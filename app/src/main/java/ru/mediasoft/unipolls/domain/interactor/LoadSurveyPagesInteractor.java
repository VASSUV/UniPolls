package ru.mediasoft.unipolls.domain.interactor;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.pollpages.SearchResultPages;

public class LoadSurveyPagesInteractor {

    public void getPages(String token, String id, SingleObserver<SearchResultPages> sub){
        App.getNetworkService().smApi
                .getSurveyPages(token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }
}
