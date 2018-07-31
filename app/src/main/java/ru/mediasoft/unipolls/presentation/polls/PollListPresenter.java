package ru.mediasoft.unipolls.presentation.polls;


import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.interactor.GetSurveysInteractor;
import ru.mediasoft.unipolls.domain.dataclass.SearchResult;
import ru.mediasoft.unipolls.other.router.CustomRouter;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class PollListPresenter extends MvpPresenter<PollListView> {

    private GetSurveysInteractor getSurveysInteractor;

    private Disposable disposable = null;

    public void onCreate(){
        getSurveysInteractor = new GetSurveysInteractor();
    }

    public void onRequest(){
        getSurveysInteractor.getSurveys()
                .subscribe(new SingleObserver<SearchResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(SearchResult searchResult) {
                        getViewState().setSurveysData(searchResult);
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

    public void goToDetailActivity(Bundle args){
        App.getRouter().navigateTo("DETAIL", args);
    }

}
