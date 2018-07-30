package ru.mediasoft.unipolls.presentation.registration;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.mediasoft.unipolls.App;

@InjectViewState
public class RegistrationPresenter extends MvpPresenter<RegistrationView>  {

    public void onCreate(App applicationContext, RegistrationView registrationView) {

    }

    public void openUrl(WebView webView){
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://api.surveymonkey.com/oauth/authorize?response_type=code&redirect_uri=https://www.surveymonkey.com&client_id=5Rwe_g_nQMOZdnC80Riq0Q");
    }
}
