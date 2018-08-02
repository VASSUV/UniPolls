package ru.mediasoft.unipolls.presentation.registration;

import com.arellomobile.mvp.MvpView;

public interface RegistrationView extends MvpView {

    void showErrorMessage(String message);
    void showMessage(String message);

    void hideProgressBar();
    void showProgressBar();
}
