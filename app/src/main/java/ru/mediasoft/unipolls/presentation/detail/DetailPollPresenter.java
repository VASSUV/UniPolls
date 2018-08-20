package ru.mediasoft.unipolls.presentation.detail;


import android.os.Bundle;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.polldetails.SearchResultDetails;
import ru.mediasoft.unipolls.domain.dataclass.pollpages.SearchResultPages;
import ru.mediasoft.unipolls.domain.interactor.LoadSurveyDetailsInteractor;
import ru.mediasoft.unipolls.domain.interactor.LoadSurveyPagesInteractor;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.other.Screen;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessageEvent;

@InjectViewState
public class DetailPollPresenter extends MvpPresenter<DetailPollView> {

    private LoadSurveyDetailsInteractor loadSurveyDetailsInteractor;
    private LoadSurveyPagesInteractor loadSurveyPagesInteractor;

    private Disposable disposableDetails = null;
    private Disposable disposablePages = null;

    private String dateCreated;
    private String dateModified;
    private String responseCount;

    public void onCreate(String pollId) {
        loadSurveyDetailsInteractor = new LoadSurveyDetailsInteractor();
        loadSurveyPagesInteractor = new LoadSurveyPagesInteractor();

        dateCreated = App.getDBRepository().getDateCreated(pollId);
        dateModified = App.getDBRepository().getDateModified(pollId);
        responseCount = App.getDBRepository().getResponseCount(pollId);

        if (dateCreated != null && dateModified != null && responseCount != null) {
            getViewState().setDateCreated(dateCreated);
            getViewState().setDateModified(dateModified);
            getViewState().setResponseCount(responseCount);
        }else{
            getPollPages(pollId);
            getPollDetails(pollId);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        onStop();
    }

    public void getPollDetails(String pollId) {
        if (App.getDBRepository().getDateCreated(pollId) == null || App.getDBRepository().getDateModified(pollId) == null || App.getDBRepository().getResponseCount(pollId) == null) {
            EventBus.getDefault().post(new ShowLoaderEvent());
        }
        loadSurveyDetailsInteractor.loadSurveyDetails(App.getSharPref().getToken(), pollId, new SingleObserver<SearchResultDetails>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposableDetails = d;
            }

            @Override
            public void onSuccess(SearchResultDetails searchResultDetails) {
                App.getDBRepository().saveDateCreated(pollId, getFormatedDateCreated(searchResultDetails));
                App.getDBRepository().saveDateModified(pollId, getFormatedDateModified(searchResultDetails));
                App.getDBRepository().saveResponseCount(pollId, getResponseCount(searchResultDetails));
                getViewState().setDateCreated(getFormatedDateCreated(searchResultDetails));
                getViewState().setDateModified(getFormatedDateModified(searchResultDetails));
                getViewState().setResponseCount(getResponseCount(searchResultDetails));
                EventBus.getDefault().post(new HideLoaderEvent());
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ShowMessageEvent(e.getMessage()));
                EventBus.getDefault().post(new HideLoaderEvent());
            }
        });
    }

    public void getPollPages(String id) {
        loadSurveyPagesInteractor.loadPages(App.getSharPref().getToken(), id, new SingleObserver<SearchResultPages>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposablePages = d;
            }

            @Override
            public void onSuccess(SearchResultPages searchResultPages) {
                App.getDBRepository().deletePagesFromTable(id);
                for (int i = 0; i < searchResultPages.data.size(); i++) {
                    App.getDBRepository().savePage(searchResultPages.data.get(i), id);
                }
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ShowMessageEvent(e.getMessage()));
            }
        });
    }


    private String getResponseCount(SearchResultDetails searchResultDetails) {
        return searchResultDetails.responseCount.toString();
    }

    private String getFormatedDateModified(SearchResultDetails searchResultDetails) {
        String dMbuf = searchResultDetails.dateModified;
        dMbuf = dMbuf.substring(0, dMbuf.indexOf("T"));
        String[] dModifiedArr = dMbuf.split("-");

        StringBuilder sbModified = new StringBuilder("   ");
        sbModified.append(dModifiedArr[2])
                .append("-")
                .append(dModifiedArr[1])
                .append("-")
                .append(dModifiedArr[0]);

        return sbModified.toString();
    }

    private String getFormatedDateCreated(SearchResultDetails searchResultDetails) {
        String dCbuf = searchResultDetails.dateCreated;
        dCbuf = dCbuf.substring(0, dCbuf.indexOf("T"));
        String[] dCreatedArr = dCbuf.split("-");

        StringBuilder sbCreated = new StringBuilder("   ");
        sbCreated.append(dCreatedArr[2])
                .append("-")
                .append(dCreatedArr[1])
                .append("-")
                .append(dCreatedArr[0]);

        return sbCreated.toString();
    }

    public void onStop() {
        if (disposableDetails != null && !disposableDetails.isDisposed()) {
            disposableDetails.dispose();
        } else if (disposablePages != null && !disposablePages.isDisposed()) {
            disposablePages.dispose();
        }
    }

    public void goToQuestionsFragment(Bundle args) {
        App.getRouter().navigateTo(Screen.QUESTIONS.name(), args);
    }

    public void onBtnAnalyticsClick(String pollId, String pollTitle) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKeys.POLL_ID_KEY, pollId);
        bundle.putString(Constants.BundleKeys.POLL_TITLE_KEY, pollTitle);
        App.getRouter().navigateTo(Screen.ANALYTICS.name(), bundle);
    }

    public void onEditButtonClick(String pollId, String pollTitle) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKeys.POLL_ID_KEY, pollId);
        bundle.putString(Constants.BundleKeys.POLL_TITLE_KEY, pollTitle);
        App.getRouter().navigateTo(Screen.EDITPOLL.name(), bundle);
    }
}
