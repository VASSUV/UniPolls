package ru.mediasoft.unipolls.domain.interactor;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.polldetails.SearchResultDetails;

public class LoadSurveyDetailsInteractor {

    public void loadSurveyDetails(String token, String id, SingleObserver<SearchResultDetails> sub){
        App.getNetworkService().smApi
                 .getSurveyDetails(token, id)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(sub);
    }
}
