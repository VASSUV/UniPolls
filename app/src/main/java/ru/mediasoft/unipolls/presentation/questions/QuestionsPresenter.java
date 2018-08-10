package ru.mediasoft.unipolls.presentation.questions;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.pollpages.Page;
import ru.mediasoft.unipolls.domain.interactor.LoadMultiPageQuestionsInteractor;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;

@InjectViewState
public class QuestionsPresenter extends MvpPresenter<QuestionsView> {

   private LoadMultiPageQuestionsInteractor loadMultiPageQuestionsInteractor;

    private Disposable disposable = null;

    public void onCreate(String pollId){
        loadMultiPageQuestionsInteractor = new LoadMultiPageQuestionsInteractor();

        onRequest(pollId);
    }

    public void onRequest(String pollId) {
        EventBus.getDefault().post(new ShowLoaderEvent());
        List<Page> pageList = App.getDBRepository().getPageList(pollId);

        loadMultiPageQuestionsInteractor.loadPageQuestions(pageList, pollId, new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(Boolean aBoolean) {
                getViewState().setResult();
                //EventBus.getDefault().post(new HideLoaderEvent());
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showErrorMessage(e.getMessage());
            }
        });
    }

    public void onStop(){
        if(disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
