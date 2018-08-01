package ru.mediasoft.unipolls.presentation.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.R;

public class LoginFragment extends MvpAppCompatFragment implements LoginView {
    @InjectPresenter
    LoginPresenter mLoginPresenter;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context applicationContext = getActivity().getApplicationContext();
        if(applicationContext == null){
            showErrorMessage("getApplicationContext() вернула null!");
        }
        else{
            mLoginPresenter.onCreate();
        }
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
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }
}
