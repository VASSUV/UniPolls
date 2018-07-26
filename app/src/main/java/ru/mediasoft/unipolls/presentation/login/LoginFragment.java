package ru.mediasoft.unipolls.ui.fragment.LoginFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mediasoft.unipolls.presentation.view.LoginFragment.LoginView;
import ru.mediasoft.unipolls.presentation.presenter.LoginFragment.LoginPresenter;

import              ru.mediasoft.unipolls.ui.fragment.BaseFragment                      ;

import ru.mediasoft.unipolls.R;

import com.arellomobile.mvp.presenter.InjectPresenter;

public class LoginFragment extends              BaseFragment                       implements LoginView {
    public static final String TAG = "LoginFragment";
	@InjectPresenter
	LoginPresenter mLoginPresenter;


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();

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
