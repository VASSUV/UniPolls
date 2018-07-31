package ru.mediasoft.unipolls.presentation.detail;


import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.domain.dataclass.polldetails.SearchResultDetails;
import ru.mediasoft.unipolls.domain.interactor.GetSurveyDetailsInteractor;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class DetailPollPresenter extends MvpPresenter<DetailPollView> {

    private GetSurveyDetailsInteractor getSurveyDetailsInteractor;

    private Disposable disposable;

    public void onCreate(){
        getSurveyDetailsInteractor = new GetSurveyDetailsInteractor();
    }

    public void getPollDetails(String id){
        getSurveyDetailsInteractor.getSurveyDetails(id, new SingleObserver<SearchResultDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(SearchResultDetails searchResultDetails) {
                        getViewState().setResult(searchResultDetails);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showErrorMessage(e.getMessage());
                    }
                });
    }

    public void onStop(){
        if(disposable != null && !disposable.isDisposed())
            disposable.isDisposed();
    }

}
