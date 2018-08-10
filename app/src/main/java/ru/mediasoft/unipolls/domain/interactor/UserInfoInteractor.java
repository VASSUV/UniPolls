package ru.mediasoft.unipolls.domain.interactor;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.userinfo.UserInfoModel;

public class UserInfoInteractor {

    public void getUserInfo(SingleObserver<UserInfoModel> sub){
        App.getNetworkService().smApi.getUserInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(sub);
    }
}
