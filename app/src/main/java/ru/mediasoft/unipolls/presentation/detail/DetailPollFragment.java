package ru.mediasoft.unipolls.presentation.detail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ru.mediasoft.unipolls.domain.dataclass.polldetails.SearchResultDetails;
import ru.mediasoft.unipolls.other.Constants;


import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.presentation.detail.adapter.QuestionsAdapter;
import ru.mediasoft.unipolls.presentation.main.MainActivity;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

public class DetailPollFragment extends MvpAppCompatFragment implements DetailPollView {
    public static final String TAG = "DetailPollFragment";
    @InjectPresenter
    DetailPollPresenter presenter;

    private String pollTitle;
    private String pollId;

    private TextView txtTitle, txtDateCreated, txtDateModified, txtQuestions, txtResponseCount, txtNoQuestions;
    private ProgressBar progBar;

    private RecyclerView recViewQuestions;
    private QuestionsAdapter questionsAdapter;


    public static DetailPollFragment newInstance(Bundle args) {
        DetailPollFragment fragment = new DetailPollFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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
        txtNoQuestions = view.findViewById(R.id.txtNoQuestions);

        txtQuestions = view.findViewById(R.id.txtQuestions);

        progBar = view.findViewById(R.id.progBarDetails);

        questionsAdapter = new QuestionsAdapter(getActivity());
        recViewQuestions = view.findViewById(R.id.recViewQuestions);
        recViewQuestions.setLayoutManager(new LinearLayoutManager(getActivity()));
        recViewQuestions.setAdapter(questionsAdapter);

        presenter.getPollDetails(pollId);
        showLoader(true);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(getActivity(), "Error - " + errorMessage, Toast.LENGTH_SHORT).show();
        presenter.onStop();
    }

    @Override
    public void setResult(SearchResultDetails searchResultDetails) {

        showLoader(false);

        // Выделение отформатированной даты из json
        String dCbuf = searchResultDetails.getDateCreated();
        String dMbuf = searchResultDetails.getDateModified();

        dCbuf = dCbuf.substring(0, dCbuf.indexOf("T"));
        dMbuf = dMbuf.substring(0, dMbuf.indexOf("T"));

        String[] dCreatedArr = dCbuf.split("-");
        String[] dModifiedArr = dMbuf.split("-");

        StringBuilder sbCreated = new StringBuilder("   ");
        sbCreated.append(dCreatedArr[2])
                .append("-")
                .append(dCreatedArr[1])
                .append("-")
                .append(dCreatedArr[0]);
        StringBuilder sbModified = new StringBuilder("   ");
        sbModified.append(dModifiedArr[2])
                .append("-")
                .append(dModifiedArr[1])
                .append("-")
                .append(dModifiedArr[0]);


        String dateCreated = txtDateCreated.getText() + sbCreated.toString();
        String dateModified = txtDateModified.getText() + sbModified.toString();

        txtDateCreated.setText(dateCreated);
        txtDateModified.setText(dateModified);
        //-----------------------------------------------------


        StringBuilder responseCount = new StringBuilder(getActivity().getResources().getString(R.string.response_count));
        responseCount.append("   ")
                .append(searchResultDetails.getResponseCount());
        txtResponseCount.setText(responseCount);

        if(searchResultDetails.getPages().get(0).getQuestions().size() == 0){
            txtNoQuestions.setVisibility(View.VISIBLE);
        }else {
            questionsAdapter.setQuestionList(searchResultDetails.getPages().get(0).getQuestions());
            questionsAdapter.notifyDataSetChanged();
        }
    }

    private void showLoader(boolean flag){
        if(flag){
            progBar.setVisibility(View.VISIBLE);
            txtDateModified.setVisibility(View.GONE);
            txtDateCreated.setVisibility(View.GONE);
            txtTitle.setVisibility(View.GONE);
            recViewQuestions.setVisibility(View.GONE);
            txtQuestions.setVisibility(View.GONE);
            txtResponseCount.setVisibility(View.GONE);
        }else{
            progBar.setVisibility(View.GONE);
            txtDateModified.setVisibility(View.VISIBLE);
            txtDateCreated.setVisibility(View.VISIBLE);
            txtTitle.setVisibility(View.VISIBLE);
            recViewQuestions.setVisibility(View.VISIBLE);
            txtQuestions.setVisibility(View.VISIBLE);
            txtResponseCount.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity)getActivity()).setActionBarTitle(getActivity().getResources().getString(R.string.details));
    }
}
