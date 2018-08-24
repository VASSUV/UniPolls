package ru.mediasoft.unipolls.domain.interactor;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.createquestion.CreateQuestionModelRequest;
import ru.mediasoft.unipolls.domain.dataclass.createquestion.CreateQuestionModelResponse;

public class ReplaceQuestionInteractor {
    public void replaceQuestion(String token, String poll_id, String page_id, String question_id, CreateQuestionModelRequest createQuestionModelRequest, SingleObserver<CreateQuestionModelResponse> sub){
        App.getNetworkService().smApi
                .replaceQuestion(token, poll_id, page_id, question_id, createQuestionModelRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(sub);
    }
}
