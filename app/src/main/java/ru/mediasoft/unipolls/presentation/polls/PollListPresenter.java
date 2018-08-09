package ru.mediasoft.unipolls.presentation.polls;


import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.polllist.SearchResultSurveys;
import ru.mediasoft.unipolls.domain.interactor.GetSurveysInteractor;
import ru.mediasoft.unipolls.other.Screen;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessage;

@InjectViewState
public class PollListPresenter extends MvpPresenter<PollListView> {

    private GetSurveysInteractor getSurveysInteractor;

    private Disposable disposable = null;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
            getSurveysInteractor = new GetSurveysInteractor();
            onRequest();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void onRequest(){
        EventBus.getDefault().post(new ShowLoaderEvent());
        getSurveysInteractor.getSurveys(App.getSharPref().getToken() ,new SingleObserver<SearchResultSurveys>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(SearchResultSurveys searchResultSurveys) {
                getViewState().setSurveysData(searchResultSurveys);
                EventBus.getDefault().post(new HideLoaderEvent());
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ShowMessage(e.getMessage()));
                EventBus.getDefault().post(new HideLoaderEvent());
            }
        });
    }

    public void onStop(){
        if(disposable!= null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    public void goToDetailFragment(Bundle args){
        App.getRouter().navigateTo(Screen.DETAIL.name(), args);
    }
    public void goToAddingPollFragment(){
        App.getRouter().navigateTo(Screen.NEWSURVEYNAME.name());
    }
}
