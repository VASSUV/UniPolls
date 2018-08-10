package ru.mediasoft.unipolls.presentation.login;


import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.other.Screen;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {
    public void onCreate() {

    }

    public void onLoginButtonClick(View view) {
        EventBus.getDefault().post(new ShowLoaderEvent());
        App.getRouter().navigateTo(Screen.USERINFO.name());
    }

    public void onRegistrationButtonClick(View view) {
        App.getRouter().navigateTo(Screen.POLL_LIST.name());
    }
}
