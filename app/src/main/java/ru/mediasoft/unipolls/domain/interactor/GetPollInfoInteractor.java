package ru.mediasoft.unipolls.domain.interactor;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.PollInfo;

public class GetPollInfoInteractor {

    public Single<PollInfo> getPollInfo(String id){
        return App.INSTANCE.networkService.smApi
                .getSurveyThroughId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
