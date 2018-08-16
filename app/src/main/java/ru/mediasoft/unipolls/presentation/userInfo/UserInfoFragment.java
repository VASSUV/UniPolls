package ru.mediasoft.unipolls.presentation.userInfo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Objects;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.presentation.main.MainActivity;

public class UserInfoFragment extends MvpAppCompatFragment implements UserInfoView {

    @InjectPresenter
    UserInfoPresenter presenter;

    private TextView name, email;
    private WebView webView;

    public static UserInfoFragment newInstance() {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.user_name);
        email = view.findViewById(R.id.user_email);
        webView = view.findViewById(R.id.home_webView);
        webView.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction()!=KeyEvent.ACTION_DOWN)
                return true;
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    ((MainActivity)getActivity()).onBackPressed();
                }
                return true;
            }
            return false;
        });
        presenter.getUserInfo(webView);

        view.findViewById(R.id.my_surveys_list).setOnClickListener(presenter::onMySurveysButtonClick);
        view.findViewById(R.id.logout).setOnClickListener(presenter::onExitButtonClick);
    }

    @Override
    public void setName(String fName, String secName) {
        name.setText(fName.concat(" ").concat(secName));
    }

    @Override
    public void setEmail(String eMail) {
        email.setText(eMail);
    }

    @Override
    public void clearFields() {
        name.setText("");
        email.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) Objects.requireNonNull(getActivity())).setActionBarTitle("Пользователь");
    }
}
