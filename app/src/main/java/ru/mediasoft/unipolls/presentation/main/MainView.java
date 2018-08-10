package ru.mediasoft.unipolls.presentation.main;

import com.arellomobile.mvp.MvpView;

interface MainView extends MvpView {
    void showLoader();
    void hideLoader();
}
