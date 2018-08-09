package ru.mediasoft.unipolls.presentation.login;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextWatcher;
import android.util.Log;
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
import ru.mediasoft.unipolls.domain.interactor.GetAccessTokenInteractor;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.other.CustomTextWatcher;
import ru.mediasoft.unipolls.other.Screen;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessage;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {

    private GetAccessTokenInteractor getAccessTokenInteractor;
    private String username, password;

    public void onCreate() {
        getAccessTokenInteractor = new GetAccessTokenInteractor();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void onLoginButtonClick(WebView webView) {

        EventBus.getDefault().post(new ShowLoaderEvent());

        CookieManager cookieManager = CookieManager.getInstance();
        ValueCallback<Boolean> booleanValueCallback = value -> {
        };
        cookieManager.removeAllCookies(booleanValueCallback);

        String getTokenUrl = "https://ru.surveymonkey.com/user/sign-in/?ut_source="
                + "papi_client_" + Constants.SurveyMonkeyAuthApi.CLIENT_ID
                + "&ep=" + "%2Foauth%2Fauthorize"
                + "%3Fresponse_type%3D" + "code"
                + "%26redirect_uri%3D" + Constants.SurveyMonkeyAuthApi.REDIRECT_URI.replace(":","%3A").replace("/","%2F")
                + "%26client_id%3D" + Constants.SurveyMonkeyAuthApi.CLIENT_ID
                + "&ut_source2=" + "papi_oauth";

        webView.loadUrl(getTokenUrl);

        webView.getSettings().setJavaScriptEnabled(true);
        Log.i("MyLogs", "Start current url: " + getTokenUrl);

        String fusername = "AlexUnder";
        String fpassword = "formediasoft312";

        webView.setWebViewClient(new WebViewClient() {

            Boolean flag = false;
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("MyLogs", "method: oPS current url: " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("MyLogs", "method: oPF current url: " + url);
                if (!flag) {
                    view.loadUrl("javascript: (function() {document.getElementById('username').value='" + fusername + "';"
                            + "document.getElementById('password').value='" + fpassword + "';"
                            + "document.getElementsByTagName('form')[0].submit();}) ();");

                    Log.i("MyLogs", "Auth started");
                    flag = true;
                }
                if (url.contains("code")) {
                    App.getSharPref().saveCode(Uri.parse(url).getQueryParameter("code"));
                    Log.i("MyLogs", "Constants.USER_CODE = " + App.getSharPref().getCode());

                    if (!(App.getSharPref().getCode().isEmpty())) {
                        webView.destroy();
                        getAccessToken();
                    }
                }

                view.evaluateJavascript("(function() {return document.getElementById('sign-in').getElementsByTagName('li')[0].innerText;})();",
                        s -> {
                            Log.i("MyLogs", "onCreateButtonClick.oJS \nError message: " + s);
                            if (!s.equals("null")) {
                                EventBus.getDefault().post(new ShowMessage(s));
                                getViewState().clearPasswordET();
                            }
                        });

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("MyLogs", "method: sOUL current url: " + url);
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.i("MyLogs", "ORE Error message: " + error.getDescription());
            }
        });
    }

    public void onRegistrationButtonClick(View view) {
        App.getRouter().navigateTo(Screen.REGISTRATION.name());
    }

    private void getAccessToken() {
        getAccessTokenInteractor.getAccessToken(Constants.SurveyMonkeyAuthApi.CLIENT_SECRET,
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
                        Log.i("MyLogs", "access_token: " + App.getSharPref().getToken());
                        EventBus.getDefault().post(new HideLoaderEvent());
                        App.getRouter().newRootScreen(Screen.USERINFO.name());
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ShowMessage(e.getMessage()));
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
}
