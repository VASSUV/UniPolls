package ru.mediasoft.unipolls.domain.interactor;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.polllist.SearchResultSurveys;

public class GetSurveysInteractor {

    public void getSurveys(SingleObserver<SearchResultSurveys> sub){
         App.INSTANCE.networkService.smApi
                 .getSurveys()
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(sub);
    }
}
