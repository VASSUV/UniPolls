package ru.mediasoft.unipolls.presentation.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Objects;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.R;

public class RegistrationFragment extends MvpAppCompatFragment implements RegistrationView {

	@InjectPresenter
	RegistrationPresenter mRegistrationPresenter = new RegistrationPresenter();


    WebView webView;

    public static RegistrationFragment newInstance() {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegistrationPresenter.onCreate((App) Objects.requireNonNull(getActivity()).getApplicationContext(), this);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final   ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView = view.findViewById(R.id.reg_webview);
        mRegistrationPresenter.openUrl(webView);
    }
}
