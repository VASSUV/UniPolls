package ru.mediasoft.unipolls.presentation.questions;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.pollpages.Page;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.Choice;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.SearchResultQuestionDetails;
import ru.mediasoft.unipolls.domain.interactor.LoadMultiPageQuestionsInteractor;
import ru.mediasoft.unipolls.domain.interactor.LoadQuestionDetailInteractor;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;

@InjectViewState
public class QuestionsPresenter extends MvpPresenter<QuestionsView> {

    private LoadMultiPageQuestionsInteractor loadMultiPageQuestionsInteractor;
    private LoadQuestionDetailInteractor loadQuestionDetailInteractor;

    private Disposable disposableQuestions = null;
    private Disposable disposableDetails = null;

    public void onCreate(String pollId) {
        loadMultiPageQuestionsInteractor = new LoadMultiPageQuestionsInteractor();
        loadQuestionDetailInteractor = new LoadQuestionDetailInteractor();

        list = db.getlist();

        onRequest(pollId);

    }

    public void onRequest(String pollId) {
        list.isEmpty
            EventBus.getDefault().post(new ShowLoaderEvent());


        List<Page> pageList = App.getDBRepository().getPageList(pollId);

        loadMultiPageQuestionsInteractor.loadPageQuestions(pageList, pollId, new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposableQuestions = d;
            }

            @Override
            public void onSuccess(Boolean aBoolean) {
                loadQuestionDetailInteractor.getQuestionsDetails(pollId, new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableDetails = d;
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        list = db.getList();
                        adapter.setData(10);
                        
                        EventBus.getDefault().post(new HideLoaderEvent());
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showErrorMessage(e.getMessage());
                        EventBus.getDefault().post(new HideLoaderEvent());
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showErrorMessage(e.getMessage());
            }
        });
    }

    public void onStop() {
        if (disposableQuestions != null && !disposableQuestions.isDisposed()) {
            disposableQuestions.dispose();
        } else if (disposableDetails != null && !disposableDetails.isDisposed()) {
            disposableDetails.dispose();
        }
    }
}
