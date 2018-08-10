package ru.mediasoft.unipolls.presentation.questions;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.domain.dataclass.pollpages.SearchResultPages;
import ru.mediasoft.unipolls.domain.dataclass.pollquestions.SearchResultQuestions;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.presentation.main.MainActivity;
import ru.mediasoft.unipolls.presentation.questions.adapter.QuestionsPagerAdapter;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

public class QuestionsFragment extends MvpAppCompatFragment implements QuestionsView {
    public static final String TAG = "QuestionsFragment";
    @InjectPresenter
    QuestionsPresenter presenter;

    private ViewPager viewPager;
    private QuestionsPagerAdapter pagerAdapter;

    private String pollId;

    public static QuestionsFragment newInstance(Bundle args) {
        QuestionsFragment fragment = new QuestionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        Log.i(Constants.LOG_TAG_DB, arguments.getString(Constants.BundleKeys.POLL_ID_KEY));

        pollId = arguments.getString(Constants.BundleKeys.POLL_ID_KEY);

        presenter.onCreate(pollId);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_questions, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.questionsViewPager);
        pagerAdapter = new QuestionsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagerAdapter.onSelectedQuestion(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).setActionBarTitle(getActivity().getString(R.string.to_review_poll));
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
        presenter.onStop();
    }

    @Override
    public void setResult() {
        Bundle args = new Bundle();
        args.putString(Constants.BundleKeys.POLL_ID_KEY, pollId);
        args.putInt(Constants.BundleKeys.PAGE_QUESTIONS_COUNT, App.getDBRepository().getQuestionCount(pollId));
        pagerAdapter.setArgs(args);
        pagerAdapter.notifyDataSetChanged();
    }
}
