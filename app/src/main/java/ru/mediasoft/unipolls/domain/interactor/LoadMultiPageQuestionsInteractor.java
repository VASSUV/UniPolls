package ru.mediasoft.unipolls.domain.interactor;

import android.util.Log;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.pollpages.Page;
import ru.mediasoft.unipolls.domain.dataclass.pollquestions.SearchResultQuestions;

public class LoadMultiPageQuestionsInteractor {

    public void loadPageQuestions(String token, List<Page> pageList, String surveyId, SingleObserver<Boolean> sub) {
        App.getDBRepository().deleteQuestionsFromTable(surveyId);
        Single.<Boolean>create(emitter -> {
            for (Page page : pageList) {
                Log.i("MyLogs", "Interactor pageId "+ page.id);
                SearchResultQuestions searchResultQuestions = App.getNetworkService().smApi
                        .getPageQuestions(token, surveyId, page.id)
                        .blockingGet();
                App.getDBRepository().saveQuestionList(searchResultQuestions.questionList, page.id, surveyId);
            }
            emitter.onSuccess(true);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }
}