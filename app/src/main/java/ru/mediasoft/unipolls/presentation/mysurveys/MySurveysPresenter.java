package ru.mediasoft.unipolls.presentation.mysurveys;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.data.SMApi;
import ru.mediasoft.unipolls.domain.interactors.CreateSurveyInteractor;
import ru.mediasoft.unipolls.other.Screen;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class MySurveysPresenter extends MvpPresenter<MySurveysView> {

    private SMApi smApi;
    private MySurveysView mySurveysView;
    private CreateSurveyInteractor createSurveyInteractor;
    private Router router;
    private String name;

    public MySurveysPresenter() {
        router = App.getRouter();
    }

    public void onCreate() {
        smApi = App.INSTANCE.networkService.smApi;
        createSurveyInteractor = new CreateSurveyInteractor(smApi);
    }

//    public void onAddSurveyButtonClick(View view) {
//        mySurveysView.showProgressBar();
//        createSurveyInteractor.createSurvey(name, new SingleObserver<CreateSurveyModel>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onSuccess(CreateSurveyModel createSurveyModel) {
//                mySurveysView.hideProgressBar();
//                mySurveysView.showMessage("Succes");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                mySurveysView.hideProgressBar();
//                mySurveysView.showErrorMessage("Error");
//            }
//
//        });
//    }

    public void onAddSurveyButtonClick(View view) {
        App.getRouter().navigateTo(Screen.NEWSURVEYNAME.name());
    }

    public TextWatcher getTextListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }
}
