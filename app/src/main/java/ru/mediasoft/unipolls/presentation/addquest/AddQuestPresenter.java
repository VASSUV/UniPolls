package ru.mediasoft.unipolls.presentation.addquest;

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
import ru.mediasoft.unipolls.domain.interactor.CreateQuestionInteractor;
import ru.mediasoft.unipolls.other.CustomTextWatcher;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessageEvent;

@InjectViewState
public class AddQuestPresenter extends MvpPresenter<AddQuestView> {

    private CreateQuestionInteractor questionInteractor;
    private CreateQuestionModelRequest createQuestionModelRequest;
    private String questionName = "";

    public void onSaveButtonClick(String poll_id, String pageId, List<ChoicesCQ> ansList) {
        EventBus.getDefault().post(new ShowLoaderEvent());
        if (questionName.isEmpty()) {
            EventBus.getDefault().post(new ShowMessageEvent("Введите название вопроса!"));
            EventBus.getDefault().post(new HideLoaderEvent());
        }
        else{
            createQuestion(poll_id, pageId, ansList);
        }
    }

    private void createQuestion(String poll_id, String page_id, List<ChoicesCQ> ansList) {

        createQuestionModelRequest = new CreateQuestionModelRequest();
        createQuestionModelRequest.headings.add(new HeadingCQ());
        createQuestionModelRequest.headings.get(0).heading = questionName;
        createQuestionModelRequest.answers.choices = ansList;
        questionInteractor = new CreateQuestionInteractor();
        questionInteractor.createQuestion(App.getSharPref().getToken(), poll_id, page_id, createQuestionModelRequest, new SingleObserver<CreateQuestionModelResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(CreateQuestionModelResponse createQuestionModelResponse) {
                EventBus.getDefault().post(new HideLoaderEvent());
                EventBus.getDefault().post(new ShowMessageEvent("Вопрос создан"));
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new HideLoaderEvent());
                EventBus.getDefault().post(new ShowMessageEvent("Вопрос не создан.\nПричина: " + e.getMessage()));
            }
        });
    }

    public TextWatcher getTextListener() {
        return new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                questionName = s.toString();
            }
        };
    }
}