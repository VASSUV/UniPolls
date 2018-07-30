package ru.mediasoft.unipolls.presentation.mysurveys;

import com.arellomobile.mvp.MvpView;

public interface MySurveysView extends MvpView {
    void hideProgressBar();
    void showProgressBar();
    void showMessage(String message);
}
