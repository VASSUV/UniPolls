package ru.mediasoft.unipolls.presentation.mysurveys;

import com.arellomobile.mvp.MvpView;

public interface MySurveysView extends MvpView {

    void showMessage(String message);

    void showErrorMessage(String message);
}
