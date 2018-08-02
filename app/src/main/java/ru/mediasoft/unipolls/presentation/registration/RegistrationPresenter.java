package ru.mediasoft.unipolls.presentation.registration;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.other.Screen;

@InjectViewState
public class RegistrationPresenter extends MvpPresenter<RegistrationView> {

    private RegistrationView registrationView;
    private Uri uri;
    private String code;
    private CookieManager cookieManager;


    public void onCreate(App applicationContext, RegistrationView registrationView) {
        this.registrationView = registrationView;
    }

    private String urlParams = "response_type=code"
            + "&redirect_uri=" + Constants.REDIRECT_URI
            + "&client_id=" + Constants.CLIENT_ID;

    private String url = Constants.BASE_URL + Constants.AUTH_END_CODE + urlParams;


    public void openUrl(WebView webView) {

        cookieManager = CookieManager.getInstance();
        ValueCallback<Boolean> booleanValueCallback = value -> { };
        cookieManager.removeAllCookies(booleanValueCallback);

        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                registrationView.showProgressBar();
                Log.i("MyLogs", "method: oPS current url: " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                registrationView.hideProgressBar();
                Log.i("MyLogs", "method: oPF current url: " + url);
                if (url.contains("code")) {
                    uri = Uri.parse(url);
                    code = uri.getQueryParameter("code");
                    Log.i("MyLogs", "method: oPF current url: " + url + "\ncode = " + code);
                    Constants.USER_CODE = code;
                    Log.i("MyLogs", "Constants.USER_CODE = " + Constants.USER_CODE);
                    if(code != null) {
                        webView.destroy();
                        App.getRouter().newRootScreen(Screen.USERINFO.name());
                    }
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.i("MyLogs", "method: sOUL current url: " + url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }
}
