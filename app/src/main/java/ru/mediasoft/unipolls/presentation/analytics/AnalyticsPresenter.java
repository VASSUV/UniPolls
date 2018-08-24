package ru.mediasoft.unipolls.presentation.analytics;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.analytics.PollRollUps;
import ru.mediasoft.unipolls.domain.dataclass.pollpages.Page;
import ru.mediasoft.unipolls.domain.interactor.LoadMultiPageQuestionsInteractor;
import ru.mediasoft.unipolls.domain.interactor.LoadQuestionDetailInteractor;
import ru.mediasoft.unipolls.domain.interactor.LoadSurveyRollUpsInteractor;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessageEvent;

@InjectViewState
public class AnalyticsPresenter extends MvpPresenter<AnalyticsView> {

    private LoadSurveyRollUpsInteractor loadSurveyRollUpsInteractor;
    private LoadMultiPageQuestionsInteractor loadMultiPageQuestionsInteractor;
    private LoadQuestionDetailInteractor loadQuestionDetailInteractor;

    private Disposable disposableQuestions = null;
    private Disposable disposableDetails = null;
    private Disposable disposableRollUps = null;
    private String pollId;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (App.getDBRepository().isHaveAnswers(pollId)) {
            getViewState().setMainData();
            loadRollUps(pollId);
        }
        else{
            EventBus.getDefault().post(new ShowMessageEvent("Загружаем данные"));
            EventBus.getDefault().post(new ShowLoaderEvent());
            onRequest(pollId);
        }
    }

    public void onCreate(String pollId) {
        this.pollId = pollId;
    }

    public void onRequest(String pollId) {
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
                        //                      getViewState().hideRefreshing();
                        getViewState().setMainData();
                        loadRollUps(pollId);
                        EventBus.getDefault().post(new HideLoaderEvent());
                    }

                    @Override
                    public void onError(Throwable e) {
//                        getViewState().hideRefreshing();
                        EventBus.getDefault().post(new ShowMessageEvent(e.getMessage()));
                        EventBus.getDefault().post(new HideLoaderEvent());
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
//                getViewState().hideRefreshing();
                EventBus.getDefault().post(new ShowMessageEvent(e.getMessage()));
                EventBus.getDefault().post(new HideLoaderEvent());
            }
        });
    }

    public void loadRollUps(String poll_id) {
        EventBus.getDefault().post(new ShowMessageEvent("Обновляем данные"));
        loadSurveyRollUpsInteractor = new LoadSurveyRollUpsInteractor();
        loadSurveyRollUpsInteractor.loadSurveyRollUps(App.getSharPref().getToken(), poll_id, new SingleObserver<PollRollUps>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposableRollUps = d;
            }

            @Override
            public void onSuccess(PollRollUps pollRollUps) {
                Log.i("MyLogs", "Pressenter CurrId: " + pollRollUps.data.get(0).id);
                getViewState().setAnQuestionsData(pollRollUps);
                EventBus.getDefault().post(new HideLoaderEvent());
                EventBus.getDefault().post(new ShowMessageEvent("Данные обновленны"));
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new HideLoaderEvent());
                EventBus.getDefault().post(new ShowMessageEvent("Данные не обновленны" + e.getMessage()));
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
        if (disposableRollUps != null && !disposableRollUps.isDisposed()) {
            disposableRollUps.dispose();
        }
    }

}