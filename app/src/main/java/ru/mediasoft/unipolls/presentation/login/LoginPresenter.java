package ru.mediasoft.unipolls.presentation.login;


import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.other.Screen;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {

    public void onCreate() {

    }

    public void onLoginButtonClick(View view) {
        getViewState().showProgressBar();
        App.getRouter().navigateTo(Screen.USERINFO.name());
    }

    public void onRegistrationButtonClick(View view) {
        App.getRouter().navigateTo(Screen.POLL_LIST.name());
    }
}
