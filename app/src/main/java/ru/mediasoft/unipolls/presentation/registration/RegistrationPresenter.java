package ru.mediasoft.unipolls.presentation.registration;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextWatcher;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import ru.mediasoft.unipolls.domain.interactor.GetAccessTokenInteractor;
import ru.mediasoft.unipolls.other.CustomTextWatcher;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessage;

@InjectViewState
public class RegistrationPresenter extends MvpPresenter<RegistrationView> {

    private GetAccessTokenInteractor getAccessTokenInteractor;

    private String username, password, email, firstname, lastname;

    Boolean flag = false;


    public void onCreate() {
        getAccessTokenInteractor = new GetAccessTokenInteractor();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void onCreateButtonClick(WebView webView) {
        EventBus.getDefault().post(new ShowLoaderEvent());

        CookieManager cookieManager = CookieManager.getInstance();
        ValueCallback<Boolean> booleanValueCallback = value -> {
        };
        cookieManager.removeAllCookies(booleanValueCallback);

        String getTokenUrl;
        getTokenUrl = "https://ru.surveymonkey.com/user/sign-up/?ut_source=megamenu";

        webView.loadUrl(getTokenUrl);
        //webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);

        Log.i("MyLogs", "Start current url: " + getTokenUrl);

        String fusername = "InternalStruggle";
        String fpassword = "inernalstruggle73";
        String femail = "vovarazinov@gmail.com";
        String ffirstname = "Vova";
        String flastname = "Razinov";

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("MyLogs", "method: onCreateButtonClick.oPS \ncurrent url: " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                Log.i("MyLogs", "method: onCreateButtonClick.oPF \ncurrent url: " + url);
                if (!flag) {
                    view.loadUrl("javascript: (function() {"
                            + "document.getElementById('username').value='" + fusername + "';"
                            + "document.getElementById('password').value='" + fpassword + "';"
                            + "document.getElementById('email').value='" + femail + "';"
                            + "document.getElementById('first_name').value='" + ffirstname + "';"
                            + "document.getElementById('last_name').value='" + flastname + "';"
                            + "document.getElementById('submitform').click();"
                            + "}) ();");

                    Log.i("MyLogs", "Auth started");
                    flag = true;
                }

                view.evaluateJavascript("(function() {return document.getElementsByClassName('ErrorMessage')[0].getElementsByTagName('li')[0].innerHTML;})();",
                        s -> {
                            Log.i("MyLogs", "onCreateButtonClick.oJS \nError message: " + s);
                            if (!s.equals("null")) {
                                getViewState().clearPasswordET();
                                EventBus.getDefault().post(new ShowMessage(s));
                            }
                            EventBus.getDefault().post(new HideLoaderEvent());
                        });
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("MyLogs", "method: onCreateButtonClick.sOUL \ncurrent url: " + url);
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.i("MyLogs", "omethod: onCreateButtonClick.ORE \nError message: " + error.getDescription());
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                Log.i("MyLogs", "method: onCreateButtonClick.ORE \nError message: " + errorResponse.getData());
                Log.i("MyLogs", "method: onCreateButtonClick.ORE \nError message: " + errorResponse.getReasonPhrase());
                Log.i("MyLogs", "method: onCreateButtonClick.ORE \nError message: " + errorResponse.getStatusCode());
                Log.i("MyLogs", "method: onCreateButtonClick.ORE \nError message: " + errorResponse.getEncoding());
                Log.i("MyLogs", "method: onCreateButtonClick.ORE \nError message: " + errorResponse.getMimeType());
            }
        });
    }

    public TextWatcher getNameListener() {
        return new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                username = s.toString();
            }
        };
    }

    public TextWatcher getPasswordListener() {
        return new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = s.toString();
            }
        };
    }

    public TextWatcher getEmailListener() {
        return new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email = s.toString();
            }
        };
    }

    public TextWatcher getFirstNameListener() {
        return new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firstname = s.toString();
            }
        };
    }

    public TextWatcher getLastNameListener() {
        return new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lastname = s.toString();
            }
        };
    }
}
