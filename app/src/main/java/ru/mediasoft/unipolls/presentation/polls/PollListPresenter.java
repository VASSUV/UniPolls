package ru.mediasoft.unipolls.presentation.polls;


import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.polllist.Poll;
import ru.mediasoft.unipolls.domain.dataclass.polllist.SearchResultSurveys;
import ru.mediasoft.unipolls.domain.interactor.LoadSurveysInteractor;
import ru.mediasoft.unipolls.other.Screen;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessageEvent;

@InjectViewState
public class PollListPresenter extends MvpPresenter<PollListView> {

    private LoadSurveysInteractor loadSurveysInteractor;

    private Disposable disposable = null;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadSurveysInteractor = new LoadSurveysInteractor();

        List<Poll> pollListFromDB = App.getDBRepository().getPollList();
        if (!pollListFromDB.isEmpty()) {
            getViewState().setPollList(pollListFromDB);
        } else {
            onRequest();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void onRequest() {
        if (App.getDBRepository().getPollList().isEmpty()) {
            EventBus.getDefault().post(new ShowLoaderEvent());
        }
        loadSurveysInteractor.loadSurveys(App.getSharPref().getToken(), new SingleObserver<SearchResultSurveys>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(SearchResultSurveys searchResultSurveys) {
                App.getDBRepository().savePolls(searchResultSurveys.pollList);
                getViewState().setPollList(searchResultSurveys.pollList);
                EventBus.getDefault().post(new HideLoaderEvent());
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ShowMessageEvent(e.getMessage()));
                EventBus.getDefault().post(new HideLoaderEvent());
            }
        });
    }

    public void goToDetailFragment(Bundle args) {
        App.getRouter().navigateTo(Screen.DETAIL.name(), args);
    }

    public void goToAddingPollFragment() {
        App.getRouter().navigateTo(Screen.ADDING_POLL.name());
    }
}
