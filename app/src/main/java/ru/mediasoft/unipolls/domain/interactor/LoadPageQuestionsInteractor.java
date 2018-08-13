package ru.mediasoft.unipolls.domain.interactor;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.pollquestions.SearchResultQuestions;

public class LoadPageQuestionsInteractor {
    public void loadPageQuestions(String token, String surveyId, String pageId, SingleObserver<SearchResultQuestions> sub) {
        App.getNetworkService().smApi
                .getPageQuestions(token, surveyId, pageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }
}
