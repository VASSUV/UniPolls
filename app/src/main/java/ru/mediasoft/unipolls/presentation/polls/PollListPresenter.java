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
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.domain.dataclass.polllist.SearchResultSurveys;
import ru.mediasoft.unipolls.domain.interactor.LoadSurveysInteractor;
import ru.mediasoft.unipolls.other.Screen;

@InjectViewState
public class PollListPresenter extends MvpPresenter<PollListView> {

    private LoadSurveysInteractor loadSurveysInteractor;

    private Disposable disposable = null;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadSurveysInteractor = new LoadSurveysInteractor();

        //TODO переделать(?)
        List<Poll> pollListFromDB = App.getDBRepository().getPollList();
        if (pollListFromDB.isEmpty()) {
            onRequest();
        }else{
            getViewState().setPollList(pollListFromDB);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void onRequest() {
        EventBus.getDefault().post(new ShowLoaderEvent());
        loadSurveysInteractor.getSurveys(new SingleObserver<SearchResultSurveys>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(SearchResultSurveys searchResultSurveys) {
                getViewState().setPollList(searchResultSurveys.pollList);
                for (int i = 0; i < searchResultSurveys.pollList.size(); i++) {
                    App.getDBRepository().savePoll(searchResultSurveys.pollList.get(i));
                }
                EventBus.getDefault().post(new HideLoaderEvent());
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showErrorMessage(e);
                EventBus.getDefault().post(new HideLoaderEvent());
            }
        });
    }

    public void onStop() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void goToDetailFragment(Bundle args) {
        App.getRouter().navigateTo(Screen.DETAIL.name(), args);
    }

    public void goToAddingPollFragment() {
        App.getRouter().navigateTo(Screen.ADDING_POLL.name());
    }

}
