package ru.mediasoft.unipolls.presentation.userInfo;

import android.view.View;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.data.UserInfoApi;
import ru.mediasoft.unipolls.domain.dataclass.UserInfoModel;
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

    public void GetUserInfo(View view) {
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
        App.getRouter().backTo("START");
    }
}
