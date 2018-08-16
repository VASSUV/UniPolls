package ru.mediasoft.unipolls.presentation.addpoll;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.presentation.main.MainActivity;

public class AddingPollFragment extends MvpAppCompatFragment implements AddingPollView {
    public static final String TAG = "AddingPollFragment";
    @InjectPresenter
    AddingPollPresenter presenter;

    private TextView poll_nameFin;
    private EditText poll_name, questionName, ans1, ans2,ans3, ans4;
    private LinearLayout pollNameWindow, createQuestionWindow;

    public static AddingPollFragment newInstance() {
        AddingPollFragment fragment = new AddingPollFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adding_poll, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        addTextListeners();

        view.findViewById(R.id.add_createPollButton).setOnClickListener(presenter::onCreateButtonClick);
        view.findViewById(R.id.add_createQuestionButton).setOnClickListener(presenter::onCreateQuestionButtonClick);
    }

    private void addTextListeners() {
        poll_name.addTextChangedListener(presenter.pollNameListener());
        questionName.addTextChangedListener(presenter.questionNameListener());
        ans1.addTextChangedListener(presenter.ans1Listener());
        ans2.addTextChangedListener(presenter.ans2Listener());
        ans3.addTextChangedListener(presenter.ans3Listener());
        ans4.addTextChangedListener(presenter.ans4Listener());
    }

    private void initViews(View view) {
        poll_nameFin = view.findViewById(R.id.add_poll_nameFin);

        pollNameWindow = view.findViewById(R.id.add_pollNameWindow);
        createQuestionWindow = view.findViewById(R.id.add_questionCreate);

        poll_name = view.findViewById(R.id.add_poll_name);
        questionName = view.findViewById(R.id.add_questionName);
        ans1 = view.findViewById(R.id.add_ans1);
        ans2 = view.findViewById(R.id.add_ans2);
        ans3 = view.findViewById(R.id.add_ans3);
        ans4 = view.findViewById(R.id.add_ans4);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setActionBarTitle(getActivity().getString(R.string.new_poll));
    }

    @Override
    public void hidePollNameWindow() {
        pollNameWindow.setVisibility(View.GONE);
    }

    @Override
    public void showPollNameWindow() {
        pollNameWindow.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideQuestionsWindow() {
        createQuestionWindow.setVisibility(View.GONE);
    }

    @Override
    public void showQuestionsWindow() {
        createQuestionWindow.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPollName() {
        poll_nameFin.setText(poll_name.getText().toString());
    }
}
