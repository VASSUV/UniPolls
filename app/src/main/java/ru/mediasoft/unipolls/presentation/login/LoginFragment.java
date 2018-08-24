package ru.mediasoft.unipolls.presentation.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.presentation.main.MainActivity;

public class LoginFragment extends MvpAppCompatFragment implements LoginView {
    @InjectPresenter
    LoginPresenter mLoginPresenter;

    WebView webView;
    EditText username, password;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginPresenter.onCreate();
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = view.findViewById(R.id.login_username);
        password = view.findViewById(R.id.login_password);
        webView = view.findViewById(R.id.auth_webview);

        username.addTextChangedListener(mLoginPresenter.getNameListener());
        password.addTextChangedListener(mLoginPresenter.getPasswordListener());
        password.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE){
                mLoginPresenter.onLoginButtonClick(webView);
                return true;
            }
            return false;
        });

        view.findViewById(R.id.login_login_button).setOnClickListener(v -> mLoginPresenter.onLoginButtonClick(webView));
        view.findViewById(R.id.login_registration_button).setOnClickListener(mLoginPresenter::onRegistrationButtonClick);
    }

    @Override
    public void clearPasswordET() {
        password.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Авторизация");
    }
}