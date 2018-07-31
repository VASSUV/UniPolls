package ru.mediasoft.unipolls.domain.interactor;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.SearchResult;

public class GetSurveysInteractor {

    public Single<SearchResult> getSurveys(){
        return App.INSTANCE.networkService.smApi
                .getSurveys()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
