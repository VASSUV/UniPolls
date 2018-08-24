package ru.mediasoft.unipolls.presentation.registration;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.GetAccessTokenModel;
import ru.mediasoft.unipolls.domain.interactor.LoadAccessTokenInteractor;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.other.CustomTextWatcher;
import ru.mediasoft.unipolls.other.Screen;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessageEvent;

@InjectViewState
public class RegistrationPresenter extends MvpPresenter<RegistrationView> {

    private LoadAccessTokenInteractor loadAccessTokenInteractor;

    private String username, password, email, firstname, lastname;

    Boolean flag = false;


    public void onCreate() {
        loadAccessTokenInteractor = new LoadAccessTokenInteractor();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void onCreateButtonClick(WebView webView) {
        EventBus.getDefault().post(new ShowLoaderEvent());

        CookieManager cookieManager = CookieManager.getInstance();
        ValueCallback<Boolean> booleanValueCallback = value -> {
        };
        cookieManager.removeAllCookies(booleanValueCallback);

        String Url = "https://ru.surveymonkey.com/user/sign-up/?ut_source="
                + "papi_client_" + Constants.SurveyMonkeyAuthApi.CLIENT_ID
                + "&ep=" + "%2Foauth%2Fauthorize"
                + "%3Fresponse_type%3D" + "code"
                + "%26redirect_uri%3D" + Constants.SurveyMonkeyAuthApi.REDIRECT_URI.replace(":", "%3A").replace("/", "%2F")
                + "%26client_id%3D" + Constants.SurveyMonkeyAuthApi.CLIENT_ID
                + "&ut_source2=" + "papi_oauth";

        webView.loadUrl(Url);
        //webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);

//        Log.i("MyLogs", "Start current url: " + Url);

        String fusername = "excel1228";
        String fpassword = "inernalstruggle73";
        String femail = "vovarazinov@mail.ru";
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

//                Log.i("MyLogs", "method: onCreateButtonClick.oPF \ncurrent url: " + url);
                if (!flag) {
                    view.loadUrl("javascript: (function() {"
                            + "document.getElementById('username').value='" + username + "';"
                            + "document.getElementById('password').value='" + password + "';"
                            + "document.getElementById('email').value='" + email + "';"
                            + "document.getElementById('first_name').value='" + firstname + "';"
                            + "document.getElementById('last_name').value='" + lastname + "';"
                            + "document.getElementById('submitform').click();"
                            + "}) ();"
                    );

//                    Log.i("MyLogs", "Auth started");
                    flag = true;
                }
                if (url.contains("code")) {
                    App.getSharPref().saveCode(Uri.parse(url).getQueryParameter("code"));
//                    Log.i("MyLogs", "Constants.USER_CODE = " + App.getSharPref().getCode());

                    if (!(App.getSharPref().getCode().isEmpty())) {
                        webView.destroy();
                        getAccessToken();
                    }
                }
                view.evaluateJavascript("(function() {return document.getElementsByClassName('ErrorMessage')[0].getElementsByTagName('li')[0].innerHTML;})();",
                        s -> {
//                            Log.i("MyLogs", "onCreateButtonClick.oJS \nError message: " + s);
                            if (!s.equals("null")) {
                                getViewState().clearPasswordET();
                                EventBus.getDefault().post(new ShowMessageEvent(s));
                            }
                            EventBus.getDefault().post(new HideLoaderEvent());
                        });
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.i("MyLogs", "method: onCreateButtonClick.sOUL \ncurrent url: " + url);
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
//                Log.i("MyLogs", "omethod: onCreateButtonClick.ORE \nError message: " + error.getDescription());
                EventBus.getDefault().post(new ShowMessageEvent("Ошибка сети: " + error.getDescription().toString()));
            }
        });
    }

    private void getAccessToken() {
        loadAccessTokenInteractor.loadAccessToken(Constants.SurveyMonkeyAuthApi.CLIENT_SECRET,
                App.getSharPref().getCode(),
                Constants.SurveyMonkeyAuthApi.REDIRECT_URI,
                Constants.SurveyMonkeyAuthApi.CLIENT_ID,
                Constants.SurveyMonkeyAuthApi.GRANT_TYPE, new SingleObserver<GetAccessTokenModel>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetAccessTokenModel getAccessTokenModel) {
                        App.getSharPref().saveToken(getAccessTokenModel.token_type.concat(" ").concat(getAccessTokenModel.access_token));
//                        Log.i("MyLogs", "access_token: " + App.getSharPref().getToken());
                        EventBus.getDefault().post(new HideLoaderEvent());
                        App.getRouter().newRootScreen(Screen.USERINFO.name());
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ShowMessageEvent(e.getMessage()));
                        EventBus.getDefault().post(new HideLoaderEvent());
                        App.getSharPref().removeCodeAndToken();
                        App.getRouter().backTo(Screen.START.name());
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

    public View.OnKeyListener OnKeyListener(WebView webView) {
        return (v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                onCreateButtonClick(webView);
                return true;
            }
            return false;
        };
    }
}