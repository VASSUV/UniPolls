package ru.mediasoft.unipolls.presentation.editquest;

import android.text.TextWatcher;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.createquestion.ChoicesCQ;
import ru.mediasoft.unipolls.domain.dataclass.createquestion.CreateQuestionModelRequest;
import ru.mediasoft.unipolls.domain.dataclass.createquestion.CreateQuestionModelResponse;
import ru.mediasoft.unipolls.domain.dataclass.createquestion.HeadingCQ;
import ru.mediasoft.unipolls.domain.interactor.ReplaceQuestionInteractor;
import ru.mediasoft.unipolls.other.CustomTextWatcher;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessageEvent;

@InjectViewState
public class EditQuestPresenter extends MvpPresenter<EditQuestView> {

    ReplaceQuestionInteractor replaceQuestionInteractor;
    CreateQuestionModelRequest createQuestionModelRequest;
    private String questName;
    private Disposable dispose = null;

    public void onSaveButtonClick(String pollId, String pageId, String questionId, List<ChoicesCQ> ansList) {
        EventBus.getDefault().post(new ShowLoaderEvent());
        if (questName.isEmpty()) {
            EventBus.getDefault().post(new ShowMessageEvent("Введите название вопроса!"));
            EventBus.getDefault().post(new HideLoaderEvent());
        }
        else{
            replaceQuestion(pollId, pageId, questionId, ansList);
        }
    }

    private void replaceQuestion(String pollId, String pageId, String questonId, List<ChoicesCQ> ansList){

        createQuestionModelRequest = new CreateQuestionModelRequest();
        createQuestionModelRequest.headings.add(new HeadingCQ());
        createQuestionModelRequest.headings.get(0).heading = questName;
        createQuestionModelRequest.answers.choices = ansList;

        replaceQuestionInteractor = new ReplaceQuestionInteractor();
        replaceQuestionInteractor.replaceQuestion(App.getSharPref().getToken(), pollId, pageId, questonId, createQuestionModelRequest, new SingleObserver<CreateQuestionModelResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                dispose = d;
            }

            @Override
            public void onSuccess(CreateQuestionModelResponse createQuestionModelResponse) {
                EventBus.getDefault().post(new HideLoaderEvent());
                EventBus.getDefault().post(new ShowMessageEvent("Вопрос изменен"));
            }

            @Override
            public void onError(Throwable e) {
                onStop();
                EventBus.getDefault().post(new HideLoaderEvent());
                EventBus.getDefault().post(new ShowMessageEvent("Вопрос не изменен.\nПричина: " + e.getMessage()));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onStop();
    }

    public void onStop(){
        if(dispose != null && !dispose.isDisposed()){
            dispose.dispose();
        }
    }
    public TextWatcher getTextListener(String newQuestName) {
        questName = newQuestName;
        return new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                questName = s.toString();
            }
        };
    }
}