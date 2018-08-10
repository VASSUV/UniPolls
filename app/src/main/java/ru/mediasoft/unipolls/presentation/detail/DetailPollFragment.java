package ru.mediasoft.unipolls.presentation.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ru.mediasoft.unipolls.other.Constants;


import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.presentation.main.MainActivity;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

public class DetailPollFragment extends MvpAppCompatFragment implements DetailPollView {
    public static final String TAG = "DetailPollFragment";
    @InjectPresenter
    DetailPollPresenter presenter;

    private String pollTitle;
    private String pollId;

    private TextView txtTitle, txtDateCreated, txtDateModified, txtResponseCount;
    private Button btnQuestions;

    public static DetailPollFragment newInstance(Bundle args) {
        DetailPollFragment fragment = new DetailPollFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.onCreate();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_poll, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            pollTitle = getArguments().getString(Constants.BundleKeys.POLL_TITLE_KEY);
            pollId = getArguments().getString(Constants.BundleKeys.POLL_ID_KEY);
        }

        txtTitle = view.findViewById(R.id.txtTitle);
        txtTitle.setText(pollTitle);
        txtDateCreated = view.findViewById(R.id.txtDateCreated);
        txtDateModified = view.findViewById(R.id.txtDateModified);
        txtResponseCount = view.findViewById(R.id.txtResponseCount);

        btnQuestions = view.findViewById(R.id.btnQuestions);

        btnQuestions.setOnClickListener(onBtnReviewClickListener);

        presenter.getPollDetails(pollId);
        presenter.getPollPages(pollId);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(getActivity(), "Error - " + errorMessage, Toast.LENGTH_SHORT).show();
        presenter.onStop();
    }

    @Override
    public void setDateCreated(String dateCreated) {
        String strDateCreated = getActivity().getString(R.string.date_created) + dateCreated;
        txtDateCreated.setText(strDateCreated);
    }

    @Override
    public void setDateModified(String dateModified) {
        String strDateModified = getActivity().getString(R.string.date_modified) + dateModified;
        txtDateModified.setText(strDateModified);
    }

    @Override
    public void setResponseCount(String responseCount) {
        StringBuilder sbResponseCount = new StringBuilder(getActivity().getString(R.string.response_count));
        sbResponseCount.append("   ")
                .append(responseCount);
        txtResponseCount.setText(sbResponseCount);
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity)getActivity()).setActionBarTitle(getActivity().getResources().getString(R.string.details));
    }

    View.OnClickListener onBtnReviewClickListener = v -> {
        Bundle args = new Bundle();
        args.putString(Constants.BundleKeys.POLL_ID_KEY, pollId);
        presenter.goToQuestionsFragment(args);
    };
}
