package ru.mediasoft.unipolls.domain.interactor;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.SearchResultQuestionDetails;

public class LoadQuestionDetailInteractor {
    /*public void getQuestionDetail(String surveyId, String pageId, String questionId, SingleObserver<SearchResultQuestionDetails> sub) {
        App.getNetworkService().smApi
                .getQuestionDetails(surveyId, pageId, questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }*/

    public void getQuestionsDetails(String pollId, SingleObserver<Boolean> sub) {
        Single.<Boolean>create(emitter -> {
            int questionCount = App.getDBRepository().getQuestionCount(pollId);
            for (int i = 1; i <= questionCount; i++) {
                String pageId = App.getDBRepository().getPageId(String.valueOf(i), pollId);
                String questionId = App.getDBRepository().getQuestionId(String.valueOf(i), pollId);
                SearchResultQuestionDetails searchResult = App.getNetworkService().smApi
                        .getQuestionDetails(pollId, pageId, questionId)
                        .blockingGet();
                App.getDBRepository().saveAnswers(questionId, searchResult.answers.choices);
            }
            emitter.onSuccess(true);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }

    /*public void getQuestionDetailTest(String surveyId, String pageId, String questionId, SingleObserver<SearchResultQuestionDetails> sub){
        App.getNetworkService().smApi
                .getQuestionDetails(surveyId, pageId, questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }

    public void loadPageQuestions(List<Page> pageList, String surveyId, SingleObserver<Boolean> sub){
        Single.<Boolean>create(emitter -> {
            for (Page page : pageList) {
                SearchResultQuestions searchResultQuestions = App.getNetworkService().smApi
                        .getPageQuestions(surveyId, page.id)
                        .blockingGet();
                for(int i = 0; i < searchResultQuestions.total; i++){
                    App.getDBRepository().saveQuestion(searchResultQuestions.questionList.get(i), page.id);
                }
            }
            emitter.onSuccess(true);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }*/
}
