package ru.mediasoft.unipolls.domain.interactor;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.SearchResultQuestionDetails;

public class LoadQuestionDetailInteractor {

    public void loadQuestionsDetails(String pollId, SingleObserver<Boolean> sub) {
        String token = App.getSharPref().getToken();
        Single.<Boolean>create(emitter -> {
            int questionCount = App.getDBRepository().getQuestionCount(pollId);
            for (int i = 1; i <= questionCount; i++) {
                String pageId = App.getDBRepository().getPageId(String.valueOf(i), pollId);
                String questionId = App.getDBRepository().getQuestionId(String.valueOf(i), pollId);
                SearchResultQuestionDetails searchResult = App.getNetworkService().smApi
                        .getQuestionDetails(token, pollId, pageId, questionId)
                        .blockingGet();
                App.getDBRepository().saveAnswers(questionId, searchResult.answers.choices);
            }
            emitter.onSuccess(true);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }
}
