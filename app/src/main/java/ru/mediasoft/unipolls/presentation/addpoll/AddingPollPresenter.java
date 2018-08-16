package ru.mediasoft.unipolls.presentation.addpoll;


import android.text.TextWatcher;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.CreateSurveyModel;
import ru.mediasoft.unipolls.domain.interactor.CreateSurveyInteractor;
import ru.mediasoft.unipolls.other.CustomTextWatcher;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessage;

@InjectViewState
public class AddingPollPresenter extends MvpPresenter<AddingPollView> {

    private String poll_name, question_name;

    public void onCreateButtonClick(View view) {
        EventBus.getDefault().post(new ShowLoaderEvent());
        CreateSurveyInteractor createSurveyInteractor = new CreateSurveyInteractor();
        createSurveyInteractor.createSurvey(App.getSharPref().getToken(), poll_name, new SingleObserver<CreateSurveyModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(CreateSurveyModel createSurveyModel) {
                EventBus.getDefault().post(new HideLoaderEvent());
                getViewState().hidePollNameWindow();
                getViewState().showQuestionsWindow();
                getViewState().setPollName();
                EventBus.getDefault().post(new ShowMessage("Опрос создан"));
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ShowMessage("Опрос не создан.\n" + e.getMessage()));
            }
        });
    }

    public void onCreateQuestionButtonClick(View view) {

    }

    public TextWatcher  pollNameListener() {
        return new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                poll_name = s.toString();
            }
        };
    }

    public TextWatcher questionNameListener() {
        return new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);

            }
        };
    }

    public TextWatcher ans1Listener() {
        return new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);

            }
        };
    }

    public TextWatcher ans2Listener() {
        return new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);

            }
        };
    }

    public TextWatcher ans3Listener() {
        return new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);

            }
        };
    }

    public TextWatcher ans4Listener() {
        return new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);

            }
        };
    }
}
