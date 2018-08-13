package ru.mediasoft.unipolls.presentation.currentquestion;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.Choice;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.SearchResultQuestionDetails;
import ru.mediasoft.unipolls.domain.interactor.LoadQuestionDetailInteractor;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;

@InjectViewState
public class CurrentQuestionPresenter extends MvpPresenter<CurrentQuestionView> {

    private LoadQuestionDetailInteractor loadQuestionDetailInteractor;

    private Disposable disposable = null;

    public void onCreate(String pollId, int position){
        loadQuestionDetailInteractor = new LoadQuestionDetailInteractor();
        getQuestionDetails(pollId, position);
    }

    public void getQuestionDetails(String pollId, int position){
        String questionId = App.getDBRepository().getQuestionId(position);
        String pageId = App.getDBRepository().getPageId(position);

        loadQuestionDetailInteractor.getQuestionDetail(App.getSharPref().getToken(), pollId, pageId, questionId, new SingleObserver<SearchResultQuestionDetails>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(SearchResultQuestionDetails searchResultQuestionDetails) {
                String questionTitle = searchResultQuestionDetails.heading.get(0).heading;
                List<Choice> answers = searchResultQuestionDetails.answers.choices;
                String position = searchResultQuestionDetails.position;

                getViewState().setQuestionTitle(questionTitle);
                getViewState().setAnswersList(answers);
                getViewState().setQuestionPosition(position);
                EventBus.getDefault().post(new HideLoaderEvent());
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

    public void onSelected() {
        if(disposable != null && !disposable.isDisposed()){
            EventBus.getDefault().post(new ShowLoaderEvent());
        }
    }
}
