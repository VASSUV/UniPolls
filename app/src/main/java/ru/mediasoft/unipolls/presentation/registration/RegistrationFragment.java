package ru.mediasoft.unipolls.presentation.ui.fragment.;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mediasoft.unipolls.presentation.presentation.view..RegistrationView;
import ru.mediasoft.unipolls.presentation.presentation.presenter..RegistrationPresenter;

import              com.arellomobile.mvp.MvpAppCompatFragment                      ;

import ru.mediasoft.unipolls.presentation.R;

import com.arellomobile.mvp.presenter.InjectPresenter;

public class RegistrationFragment extends              MvpAppCompatFragment                       implements RegistrationView {
    public static final String TAG = "RegistrationFragment";
	@InjectPresenter
	RegistrationPresenter mRegistrationPresenter;


    public static RegistrationFragment newInstance() {
        RegistrationFragment fragment = new RegistrationFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final   ViewGroup container,
            final Bundle savedInstanceState) {
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
