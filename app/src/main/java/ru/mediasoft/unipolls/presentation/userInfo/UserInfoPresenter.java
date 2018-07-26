package ru.mediasoft.unipolls.presentation.userInfo;

import android.view.View;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.data.UserInfoApi;
import ru.mediasoft.unipolls.dataclass.UserInfoModel;
import ru.mediasoft.unipolls.domain.interactors.UserInfoInteractor;
import ru.terrakok.cicerone.Router;

public class UserInfoPresenter {

    private UserInfoApi userInfoApi;
    private UserInfoView userInfoView;
    private UserInfoInteractor userInfoInteractor;
    private Router router;

    public UserInfoPresenter(){
        router = App.INSTANCE.getRouter();
    }


    public void onCreate(App applicationContext, UserInfoView userInfoView) {
        userInfoApi = applicationContext.networkService.userInfoApi;
        this.userInfoView = userInfoView;
        userInfoInteractor = new UserInfoInteractor(userInfoApi);
    }

    public void onGetInfoButtonClick(View view) {
        userInfoInteractor.getUserInfo(new SingleObserver<UserInfoModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(UserInfoModel userInfoModel) {
                userInfoView.setFirstName(userInfoModel.first_name);
                userInfoView.setSeconName(userInfoModel.last_name);
                userInfoView.setEmail(userInfoModel.email);
            }

            @Override
            public void onError(Throwable e) {
                showErrorMessage(e.getMessage());
            }
        });
    }

    private void showErrorMessage(String message) {
        //Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    public void GotoSomeWhere(View view) {
        App.getRouter().navigateTo("TEST");
    }
}
