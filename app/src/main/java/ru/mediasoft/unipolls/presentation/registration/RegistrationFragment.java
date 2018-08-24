package ru.mediasoft.unipolls.presentation.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Objects;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.presentation.main.MainActivity;

public class RegistrationFragment extends MvpAppCompatFragment implements RegistrationView {

	@InjectPresenter
	RegistrationPresenter mRegistrationPresenter;

    WebView webView;

    EditText username, password, email, firstname, lastname;

    public static RegistrationFragment newInstance() {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegistrationPresenter.onCreate();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final   ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setListeners();
        setButtonListeners(view);
    }

    private void setButtonListeners(View view) {
        view.findViewById(R.id.reg_createAccButton).setOnClickListener(v -> mRegistrationPresenter.onCreateButtonClick(webView));
    }

    private void initViews(View view) {
        webView = view.findViewById(R.id.reg_webview);
        username = view.findViewById(R.id.reg_login);
        password = view.findViewById(R.id.reg_password);
        email = view.findViewById(R.id.reg_email);
        firstname = view.findViewById(R.id.reg_firstname);
        lastname = view.findViewById(R.id.reg_lastname);
    }

    private void setListeners() {
        username.addTextChangedListener(mRegistrationPresenter.getNameListener());
        password.addTextChangedListener(mRegistrationPresenter.getPasswordListener());
        email.addTextChangedListener(mRegistrationPresenter.getEmailListener());
        firstname.addTextChangedListener(mRegistrationPresenter.getFirstNameListener());
        lastname.addTextChangedListener(mRegistrationPresenter.getLastNameListener());
        lastname.setOnKeyListener(mRegistrationPresenter.OnKeyListener(webView));
    }

    @Override
    public void clearPasswordET() {
        password.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) Objects.requireNonNull(getActivity())).setActionBarTitle("Регистрация");
    }
}
