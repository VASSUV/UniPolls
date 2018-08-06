package ru.mediasoft.unipolls.presentation.main;

import com.arellomobile.mvp.MvpView;

public interface MainView extends MvpView {
    void showLoader();
    void hideLoader();;
}
