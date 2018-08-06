package ru.mediasoft.unipolls.presentation.registration;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.GetAccessTokenModel;
import ru.mediasoft.unipolls.domain.interactor.GetAccessTokenInteractor;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.other.Screen;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;

@InjectViewState
public class RegistrationPresenter extends MvpPresenter<RegistrationView> {

    private GetAccessTokenInteractor getAccessTokenInteractor;

    public void onCreate(App applicationContext, RegistrationView registrationView) {
        getAccessTokenInteractor = new GetAccessTokenInteractor();
    }

    private String urlParams = "response_type=code"
            + "&redirect_uri=" + Constants.SurveyMonkeyAuthApi.REDIRECT_URI
            + "&client_id=" + Constants.SurveyMonkeyAuthApi.CLIENT_ID;

    private String getCodeUrl = Constants.SurveyMonkeyApi.BASE_URL + Constants.SurveyMonkeyAuthApi.AUTH_END_CODE + urlParams;

    @SuppressLint("SetJavaScriptEnabled")
    public void openUrl(WebView webView) {

        CookieManager cookieManager = CookieManager.getInstance();
        ValueCallback<Boolean> booleanValueCallback = value -> {
        };
        cookieManager.removeAllCookies(booleanValueCallback);

        webView.loadUrl(getCodeUrl);

        webView.getSettings().setJavaScriptEnabled(true);

        Log.i("MyLogs", "Start current url: " + getCodeUrl);

        webView.setWebViewClient(new WebViewClient() {

            Boolean flag = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                EventBus.getDefault().post(new ShowLoaderEvent());
                Log.i("MyLogs", "method: oPS current url: " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                EventBus.getDefault().post(new HideLoaderEvent());
                Log.i("MyLogs", "method: oPF current url: " + url);

                if (!flag) {
                    view.loadUrl("javascript: (function() {document.getElementById('username').value='AlexUnder'; " +
                            "document.getElementById('password').value='formediasoft312';" +
                            "document.getElementsByTagName('form')[0].submit();}) ();");
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

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                Log.i("MyLogs", "ORHE ErrorResponse message: " + errorResponse.getReasonPhrase());
            }
        });
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
                        App.getRouter().newRootScreen(Screen.USERINFO.name());
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showErrorMessage("getAccessToken " + e.getMessage());
                        App.getSharPref().removeCodeAndToken();
                        App.getRouter().backTo(Screen.START.name());
                    }
                });
    }
}
