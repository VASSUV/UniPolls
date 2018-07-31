package ru.mediasoft.unipolls.presentation.newsurveyname;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.mediasoft.unipolls.R;

public class NewSurveyNameFragment extends MvpAppCompatFragment implements NewSurveyNameView {

    @InjectPresenter
    NewSurveyNamePresenter mNewSurveyNamePresenter = new NewSurveyNamePresenter();


    public static NewSurveyNameFragment newInstance() {
        NewSurveyNameFragment fragment = new NewSurveyNameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_survey_name, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
