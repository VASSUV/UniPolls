package ru.mediasoft.unipolls.presentation.analytics;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.analytics.PollRollUps;
import ru.mediasoft.unipolls.domain.dataclass.polldetails.SearchResultDetails;
import ru.mediasoft.unipolls.domain.interactor.LoadSurveyDetailsInteractor;
import ru.mediasoft.unipolls.domain.interactor.LoadSurveyRollUpsInteractor;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessage;

@InjectViewState
public class AnalyticsPresenter extends MvpPresenter<AnalyticsView> {

    private LoadSurveyRollUpsInteractor loadSurveyRollUpsInteractor;
    private LoadSurveyDetailsInteractor loadSurveyDetailsInteractor;

    public void onCreate() {
        super.onFirstViewAttach();
        loadSurveyRollUpsInteractor = new LoadSurveyRollUpsInteractor();
    }

    public void loadRollUps(String poll_id) {
        EventBus.getDefault().post(new ShowLoaderEvent());
        loadSurveyRollUpsInteractor.loadSurveyRollUps(App.getSharPref().getToken(), poll_id, new SingleObserver<PollRollUps>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(PollRollUps pollRollUps) {
                Log.i("MyLogs", "Pressenter CurrId: " + pollRollUps.data.get(0).id);
                loadSurveyName(poll_id);
                //getViewState().setAnQuestionsData(pollRollUps);
                EventBus.getDefault().post(new HideLoaderEvent());
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new HideLoaderEvent());
                EventBus.getDefault().post(new ShowMessage(e.getMessage()));
            }
        });
    }

    private void loadSurveyName(String id) {
        EventBus.getDefault().post(new ShowLoaderEvent());
        loadSurveyDetailsInteractor = new LoadSurveyDetailsInteractor();
        loadSurveyDetailsInteractor.getSurveyDetails(App.getSharPref().getToken(), id, new SingleObserver<SearchResultDetails>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(SearchResultDetails searchResultDetails) {
                Log.i("MyLogs", "lSN" + searchResultDetails);

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
