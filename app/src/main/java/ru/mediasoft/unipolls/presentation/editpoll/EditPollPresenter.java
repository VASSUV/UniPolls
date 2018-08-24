package ru.mediasoft.unipolls.presentation.editpoll;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.pollpages.Page;
import ru.mediasoft.unipolls.domain.interactor.LoadMultiPageQuestionsInteractor;
import ru.mediasoft.unipolls.domain.interactor.LoadQuestionDetailInteractor;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessageEvent;

@InjectViewState
public class EditPollPresenter extends MvpPresenter<EditPollView> {

    LoadMultiPageQuestionsInteractor loadMultiPageQuestionsInteractor;
    LoadQuestionDetailInteractor loadQuestionDetailInteractor;
    private Disposable disposableQuestions = null;
    private Disposable disposableDetails = null;
    private String pollId;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (App.getDBRepository().isHaveAnswers(pollId)) {
            getViewState().setAdapterList();
        } else {
            EventBus.getDefault().post(new ShowLoaderEvent());
            onRequest(pollId);
        }
    }

    public void onCreate(String pollId) {
        this.pollId = pollId;
    }

    public void onRequest(String pollId) {
        getViewState().showRefreshing();
        EventBus.getDefault().post(new ShowMessageEvent("Обновляем данные"));
        loadMultiPageQuestionsInteractor = new LoadMultiPageQuestionsInteractor();
        List<Page> pageList = App.getDBRepository().getPageList(pollId);

        loadMultiPageQuestionsInteractor.loadPageQuestions(App.getSharPref().getToken(), pageList, pollId, new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposableQuestions = d;
            }

            @Override
            public void onSuccess(Boolean aBoolean) {

                loadQuestionDetailInteractor = new LoadQuestionDetailInteractor();
                loadQuestionDetailInteractor.loadQuestionsDetails(pollId, new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableDetails = d;
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        EventBus.getDefault().post(new ShowMessageEvent("Данные обновленны"));
                        getViewState().setAdapterList();
                        getViewState().hideRefreshing();
                        EventBus.getDefault().post(new HideLoaderEvent());
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ShowMessageEvent("Данные не обновленны\n" + e.getMessage()));
                        getViewState().hideRefreshing();
                        onStop();
                        EventBus.getDefault().post(new ShowMessageEvent(e.getMessage()));
                        EventBus.getDefault().post(new HideLoaderEvent());
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ShowMessageEvent("Данные не обновленны\n" + e.getMessage()));
                getViewState().hideRefreshing();
                onStop();
                EventBus.getDefault().post(new ShowMessageEvent(e.getMessage()));
                EventBus.getDefault().post(new HideLoaderEvent());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onStop();
    }

    public void onStop() {
        if (disposableDetails != null && !disposableDetails.isDisposed()) {
            disposableDetails.dispose();
        }
        if (disposableQuestions != null && !disposableQuestions.isDisposed()) {
            disposableQuestions.dispose();
        }
    }
}