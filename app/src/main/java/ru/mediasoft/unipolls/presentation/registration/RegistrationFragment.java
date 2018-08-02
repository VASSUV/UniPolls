package ru.mediasoft.unipolls.presentation.registration;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.R;

public class RegistrationFragment extends MvpAppCompatFragment implements RegistrationView {

	@InjectPresenter
	RegistrationPresenter mRegistrationPresenter;

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
        Context applicationContext = getActivity().getApplicationContext();
        if(applicationContext == null){
            showErrorMessage("getApplicationContext() вернула null!");
        }
        else {
            mRegistrationPresenter.onCreate((App)applicationContext, this);
        }
    }
    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
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

    @Override
    public void showProgressBar() {
        setVisibilityProgressBar(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        setVisibilityProgressBar(View.GONE);
    }

    private void setVisibilityProgressBar(int visibility) {
        try{getActivity().findViewById(R.id.progressBar).setVisibility(visibility);}
        catch(Throwable t){
            t.printStackTrace();
        }
    }

}
