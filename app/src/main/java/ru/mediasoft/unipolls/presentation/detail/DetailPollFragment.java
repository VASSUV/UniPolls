package ru.mediasoft.unipolls.presentation.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ru.mediasoft.unipolls.domain.dataclass.PollInfo;
import ru.mediasoft.unipolls.other.Constants;


import ru.mediasoft.unipolls.R;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailPollFragment extends MvpAppCompatFragment implements DetailPollView {
    public static final String TAG = "DetailPollFragment";
    @InjectPresenter
    DetailPollPresenter presenter;

    private String pollTitle;
    private String pollId;

    private TextView txtTitle, txtDateCreated, txtDateModified;


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

        presenter.doRequest(pollId);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(getActivity(), "Error - " + errorMessage, Toast.LENGTH_SHORT).show();
        presenter.onStop();
    }

    @Override
    public void setResult(PollInfo pollInfo) {
        String dCbuf = pollInfo.getDateCreated();
        String dMbuf = pollInfo.getDateModified();

        dCbuf = dCbuf.substring(0, dCbuf.indexOf("T"));
        dMbuf = dMbuf.substring(0, dMbuf.indexOf("T"));

        /*SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date date = format.parse(pollInfo.getDateCreated());*/

        String dateCreated = txtDateCreated.getText() + dCbuf;
        String dateModified = txtDateModified.getText() + dMbuf;
        txtDateCreated.setText(dateCreated);
        txtDateModified.setText(dateModified);
    }
}
