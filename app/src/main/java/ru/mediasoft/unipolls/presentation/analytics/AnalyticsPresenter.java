package ru.mediasoft.unipolls.presentation.analytics;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.analytics.Data;
import ru.mediasoft.unipolls.domain.dataclass.analytics.PollRollUps;
import ru.mediasoft.unipolls.domain.interactor.LoadSurveyRollUpsInteractor;
import ru.mediasoft.unipolls.other.events.ShowMessage;

@InjectViewState
public class AnalyticsPresenter extends MvpPresenter<AnalyticsView> {

    private LoadSurveyRollUpsInteractor loadSurveyRollUpsInteractor;
    private List<Data> questionsData;

    public void onCreate() {
        super.onFirstViewAttach();
        loadSurveyRollUpsInteractor = new LoadSurveyRollUpsInteractor();
    }

    public void loadRollUps(String poll_id) {
        loadSurveyRollUpsInteractor.loadSurveyRollUps(App.getSharPref().getToken(), poll_id, new SingleObserver<PollRollUps>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(PollRollUps pollRollUps) {
                questionsData = pollRollUps.data;
                Log.i("MyLogs", "poll_id " + poll_id);
                Log.i("MyLogs", "data: " + questionsData.get(0).id);
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ShowMessage(e.getMessage()));
            }
        });
    }
}
