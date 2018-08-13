package ru.mediasoft.unipolls.domain.interactor;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.GetAccessTokenModel;

public class GetAccessTokenInteractor {
    public void getAccessToken(String client_secret,
                               String code,
                               String redirect_uri,
                               String client_id,
                               String grant_type,
                               SingleObserver<GetAccessTokenModel> sub) {
        App.getNetworkService().smApi.getAccessToken(client_secret, code, redirect_uri, client_id, grant_type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(sub);
    }
}
