package ru.mediasoft.unipolls.presentation.detail;


import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.domain.dataclass.PollInfo;
import ru.mediasoft.unipolls.domain.interactor.GetPollInfoInteractor;
import ru.mediasoft.unipolls.presentation.detail.DetailPollView;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class DetailPollPresenter extends MvpPresenter<DetailPollView> {

    private GetPollInfoInteractor getPollInfoInteractor;

    private Disposable disposable;

    public void onCreate(){
        getPollInfoInteractor = new GetPollInfoInteractor();
    }

    public void doRequest(String id){
        getPollInfoInteractor.getPollInfo(id)
                .subscribe(new SingleObserver<PollInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(PollInfo pollInfo) {
                        getViewState().setResult(pollInfo);
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
