package ru.mediasoft.unipolls.presentation.mysurveys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Objects;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.R;

public class MySurveysFragment extends MvpAppCompatFragment implements MySurveysView {

    @InjectPresenter
    MySurveysPresenter mMySurveysPresenter = new MySurveysPresenter();

    TextView newSurveyName;

    public static MySurveysFragment newInstance() {
        MySurveysFragment fragment = new MySurveysFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMySurveysPresenter.onCreate((App) Objects.requireNonNull(getActivity()).getApplicationContext(), this);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_surveys, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newSurveyName = view.findViewById(R.id.New_Survey_Name);
        newSurveyName.addTextChangedListener(mMySurveysPresenter.getTextListener());
        view.findViewById(R.id.add_survey).setOnClickListener(mMySurveysPresenter::onAddSurveyButtonClick);
    }

    @Override
    public void hideProgressBar() {
        getActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        getActivity().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

}
