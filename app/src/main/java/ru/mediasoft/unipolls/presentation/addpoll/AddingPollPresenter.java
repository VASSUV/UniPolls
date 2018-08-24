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
import ru.mediasoft.unipolls.other.Screen;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessageEvent;

@InjectViewState
public class AddingPollPresenter extends MvpPresenter<AddingPollView> {

    private String poll_name;

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
                EventBus.getDefault().post(new ShowMessageEvent("Опрос создан"));
                backToPollsList();
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ShowMessageEvent("Опрос не создан.\n" + e.getMessage()));
                backToPollsList();
            }
        });
    }

    private void backToPollsList() {
        App.getRouter().backTo(Screen.POLL_LIST.name());
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
}