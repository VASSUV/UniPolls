package ru.mediasoft.unipolls.presentation.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;

import java.util.Objects;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.R;

public class LoginFragment extends MvpAppCompatFragment implements LoginView {
    LoginPresenter mLoginPresenter = new LoginPresenter();

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginPresenter.onCreate((App) Objects.requireNonNull(getActivity()).getApplicationContext(), this);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.login_button).setOnClickListener(mLoginPresenter::onLoginButtonClick);
        view.findViewById(R.id.registration_button).setOnClickListener(mLoginPresenter::onRegistrationButtonClick);
    }

    @Override
    public void showProgressBar() {
        getActivity().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        getActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
    }
}
