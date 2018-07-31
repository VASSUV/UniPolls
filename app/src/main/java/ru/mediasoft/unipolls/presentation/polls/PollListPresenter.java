package ru.mediasoft.unipolls.presentation.polls;


import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.polllist.SearchResultSurveys;
import ru.mediasoft.unipolls.domain.interactor.GetSurveysInteractor;
import ru.mediasoft.unipolls.other.Screen;

@InjectViewState
public class PollListPresenter extends MvpPresenter<PollListView> {

    private GetSurveysInteractor getSurveysInteractor;

    private Disposable disposable = null;

    public void onCreate(){
        getSurveysInteractor = new GetSurveysInteractor();
    }

    public void onRequest(){
        getSurveysInteractor.getSurveys(new SingleObserver<SearchResultSurveys>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(SearchResultSurveys searchResultSurveys) {
                getViewState().setSurveysData(searchResultSurveys);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showErrorMessage(e);
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
        App.getRouter().navigateTo(Screen.ADDING_POLL.name());
    }

}
