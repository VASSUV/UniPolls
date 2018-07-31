package ru.mediasoft.unipolls.presentation.userInfo;

import android.support.annotation.NonNull;
import android.view.View;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.data.SMApi;
import ru.mediasoft.unipolls.domain.dataclass.UserInfoModel;
import ru.mediasoft.unipolls.other.Screen;
import ru.mediasoft.unipolls.domain.interactor.UserInfoInteractor;
import ru.terrakok.cicerone.Router;

public class UserInfoPresenter {

    private SMApi smApi;
    private UserInfoView userInfoView;
    private UserInfoInteractor userInfoInteractor;
    private Router router;

    public UserInfoPresenter(){
        router = App.getRouter();
    }


    public void onCreate(@NonNull App applicationContext, UserInfoView userInfoView) {
        smApi = App.INSTANCE.networkService.smApi;
        this.userInfoView = userInfoView;
        userInfoInteractor = new UserInfoInteractor();
    }

    public void getUserInfo(View view) {
        userInfoInteractor.getUserInfo(new SingleObserver<UserInfoModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onSuccess(UserInfoModel userInfoModel) {
                userInfoView.setName(userInfoModel.first_name, userInfoModel.last_name);
                userInfoView.setEmail(userInfoModel.email);
                userInfoView.hideProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                userInfoView.showErrorMessage(e.getMessage());
            }
        });
    }

    public void onExitButtonClick(View view) {
        userInfoView.clearFields();
        App.getRouter().backTo(Screen.START.name());
    }

    public void onMySurveysButtonClick(View view) {
        userInfoView.showProgressBar();
        App.getRouter().navigateTo(Screen.MYSURVEYS.name());
    }
}
