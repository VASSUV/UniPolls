package ru.mediasoft.unipolls.presentation.currentquestion;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.Choice;
import ru.mediasoft.unipolls.other.Constants;

import com.arellomobile.mvp.MvpAppCompatFragment;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.presentation.currentquestion.adapter.AnswersAdapter;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.arellomobile.mvp.presenter.ProvidePresenterTag;

import java.util.List;

public class CurrentQuestionFragment extends MvpAppCompatFragment implements CurrentQuestionView {
    public static final String TAG = "CurrentQuestionFragment";

    @InjectPresenter(type = PresenterType.GLOBAL)
    CurrentQuestionPresenter presenter;

    @ProvidePresenterTag(presenterClass = CurrentQuestionPresenter.class, type = PresenterType.GLOBAL)
    String provideCurrentQuestionPresenterTag() {
//        TODO использовать другой TAG
        return String.valueOf(position);
    }

    @ProvidePresenter(type = PresenterType.GLOBAL)
    CurrentQuestionPresenter provideCurrentQuestionPresenter() {
        return new CurrentQuestionPresenter();
    }

    private TextView txtQuestion, txtQuestionPosition;
    private RecyclerView recView;
    private AnswersAdapter adapter;

    private int position;
    private String pollId;

    public static CurrentQuestionFragment newInstance(Bundle args) {
        CurrentQuestionFragment fragment = new CurrentQuestionFragment();
        fragment.setArguments(args);
        fragment.position = args.getInt(Constants.BundleKeys.QUESTION_POSITION);
        fragment.pollId = args.getString(Constants.BundleKeys.POLL_ID_KEY);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.onCreate(pollId, position);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_question, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtQuestion = view.findViewById(R.id.txtQuestionName);
        txtQuestionPosition = view.findViewById(R.id.txtQuestionPos);

        adapter = new AnswersAdapter();

        recView = view.findViewById(R.id.recViewAnswers);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView.setAdapter(adapter);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
        presenter.onStop();
    }

    @Override
    public void setResult(String questionTitle, List<Choice> answersList, String position) {
        txtQuestion.setText(questionTitle);
        adapter.setAnswers(answersList);
        adapter.notifyDataSetChanged();
        txtQuestionPosition.setText(position);
    }

    /*@Override
    public void setQuestionTitle(String questionTitle) {
        txtQuestion.setText(questionTitle);
    }

    @Override
    public void setAnswersList(List<Choice> answersList) {
        adapter.setAnswers(answersList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setQuestionPosition(String position) {
        txtQuestionPosition.setText(position);
    }*/

    /*public void onSelectedQuestion() {
        if(presenter != null) {
            presenter.onSelected();
        }
    }*/
}
