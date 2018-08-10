package ru.mediasoft.unipolls.domain.interactor;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.polldetails.SearchResultDetails;

public class LoadSurveyDetailsInteractor {

    public void getSurveyDetails(String id, SingleObserver<SearchResultDetails> sub){
        App.getNetworkService().smApi
                 .getSurveyDetails(id)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(sub);
    }
}
