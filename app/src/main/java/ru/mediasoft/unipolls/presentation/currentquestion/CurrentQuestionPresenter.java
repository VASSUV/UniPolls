package ru.mediasoft.unipolls.presentation.currentquestion;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.Choice;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.SearchResultQuestionDetails;

@InjectViewState
public class CurrentQuestionPresenter extends MvpPresenter<CurrentQuestionView> {

    private Disposable disposable = null;
    private String pollId;
    private int position;

    private SearchResultQuestionDetails searchResultQuestionDetails;

    public void onCreate(String pollId, int position) {
        this.pollId = pollId;
        this.position = position;

        searchResultQuestionDetails = App.getDBRepository().getQuestionByPosition(String.valueOf(position), pollId);
        getQuestionDetails(position);
    }

    private String questionTitle = "";
    private List<Choice> answers = new ArrayList<>();

    private void getQuestionDetails(int position) {

        if (searchResultQuestionDetails.heading != null) {
            questionTitle = searchResultQuestionDetails.heading.get(0).heading;
            answers = searchResultQuestionDetails.answers.choices;
        }

        getViewState().setResult(questionTitle, answers, String.valueOf(position));
    }

    private void onStop() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onStop();
    }
}
