package ru.mediasoft.unipolls.presentation.login;

import com.arellomobile.mvp.MvpView;

interface LoginView extends MvpView {
    void hideProgressBar();
    void showProgressBar();
}
