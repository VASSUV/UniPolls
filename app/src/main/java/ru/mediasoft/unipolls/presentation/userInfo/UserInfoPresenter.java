package ru.mediasoft.unipolls.presentation.userInfo;

import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.userinfo.UserInfoModel;
import ru.mediasoft.unipolls.domain.interactor.UserInfoInteractor;
import ru.mediasoft.unipolls.other.Screen;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessage;

@InjectViewState
public class UserInfoPresenter extends MvpPresenter<UserInfoView>{

    private UserInfoInteractor userInfoInteractor;

    public void onCreate() {
        userInfoInteractor = new UserInfoInteractor();
    }

    public void getUserInfo(View view) {
        EventBus.getDefault().post(new ShowLoaderEvent());
        userInfoInteractor.getUserInfo(App.getSharPref().getToken(), new SingleObserver<UserInfoModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onSuccess(UserInfoModel userInfoModel) {
                getViewState().setName(userInfoModel.first_name, userInfoModel.last_name);
                getViewState().setEmail(userInfoModel.email);
                EventBus.getDefault().post(new HideLoaderEvent());
            }
            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ShowMessage(e.getMessage()));
                EventBus.getDefault().post(new HideLoaderEvent());
            }
        });
    }

    public void onExitButtonClick(View view) {
        App.getSharPref().removeCodeAndToken();
        getViewState().clearFields();
        App.getRouter().newRootScreen(Screen.START.name());
    }

    public void onMySurveysButtonClick(View view) {
        App.getRouter().navigateTo(Screen.POLL_LIST.name());
    }
}
