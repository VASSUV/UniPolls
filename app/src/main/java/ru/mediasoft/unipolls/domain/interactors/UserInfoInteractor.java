package ru.mediasoft.unipolls.domain.interactors;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.data.SMApi;
import ru.mediasoft.unipolls.domain.dataclass.UserInfoModel;

public class UserInfoInteractor {
    private SMApi smApi;

    public UserInfoInteractor(SMApi smApi) {
        this.smApi = smApi;
    }

    public void getUserInfo(SingleObserver<UserInfoModel> sub){
        smApi.getUserInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(sub);
    }
}
