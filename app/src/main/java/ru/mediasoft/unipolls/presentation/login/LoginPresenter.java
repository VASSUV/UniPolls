package ru.mediasoft.unipolls.presentation.login;


import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.other.Screen;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {

    public void onLoginButtonClick(View view) {
        if(App.getSharPref().getToken().isEmpty()){
            //EventBus.getDefault().post(new ShowMessage("Неправильный логин или пароль!", view.getContext()));
            getViewState().showErrorMessage("Неправильный логин или пароль!");
        }
        else {
            App.getRouter().newRootScreen(Screen.USERINFO.name());
        }
    }

    public void onRegistrationButtonClick(View view) {
        App.getRouter().navigateTo(Screen.REGISTRATION.name());
    }
}
