package ru.mediasoft.unipolls.presentation.userInfo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.R;

public class UserInfoFragment extends MvpAppCompatFragment implements UserInfoView {

    @InjectPresenter
    UserInfoPresenter presenter;

    private TextView name, email;

    public static UserInfoFragment newInstance() {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context applicationContext = getActivity().getApplicationContext();
        if(applicationContext == null){
            showErrorMessage("getApplicationContext() вернула null!");
        }
        else{
            presenter.onCreate();
        }
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

        presenter.getUserInfo(view);

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
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void clearFields() {
        name.setText("");
        email.setText("");
    }
}