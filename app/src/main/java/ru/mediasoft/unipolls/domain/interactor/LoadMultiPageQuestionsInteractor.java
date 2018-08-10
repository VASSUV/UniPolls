package ru.mediasoft.unipolls.domain.interactor;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.pollpages.Page;
import ru.mediasoft.unipolls.domain.dataclass.pollquestions.SearchResultQuestions;
import ru.mediasoft.unipolls.other.Constants;

public class LoadMultiPageQuestionsInteractor {

    public void loadPageQuestions(List<Page> pageList, String surveyId, SingleObserver<Boolean> sub) {
        Single.<Boolean>create(emitter -> {
            for (Page page : pageList) {
                SearchResultQuestions searchResultQuestions = App.getNetworkService().smApi
                        .getPageQuestions(surveyId, page.id)
                        .blockingGet();
                for (int i = 0; i < searchResultQuestions.total; i++) {
                    App.getDBRepository().saveQuestion(searchResultQuestions.questionList.get(i), page.id, surveyId);
                }
            }
            emitter.onSuccess(true);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }
}
