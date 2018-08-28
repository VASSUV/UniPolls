package ru.mediasoft.unipolls.domain.interactor;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;

public class DeleteQuestionInteractor {
    public void deleteQuestion(String token, String pollId, String pageId, String questionId, SingleObserver<Object> sub){
        App.getNetworkService().smApi.deleteQuestion(token, pollId, pageId, questionId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(sub);
    }
}
