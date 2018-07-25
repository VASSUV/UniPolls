package ru.mediasoft.unipolls.domain.interactors;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.data.UserInfoApi;
import ru.mediasoft.unipolls.dataclass.UserInfoModel;

public class UserInfoInteractor {
    private UserInfoApi userInfoApi;

    public UserInfoInteractor(UserInfoApi userInfoApi) {
        this.userInfoApi = userInfoApi;
    }

    public void getUserInfo(SingleObserver<UserInfoModel> sub){
        userInfoApi.getUserInfo("https://api.surveymonkey.com/v3/users/me")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(sub);
    }
}
