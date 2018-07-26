package ru.mediasoft.unipolls.presentation.userInfo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.R;

public class UserInfoFragment extends MvpAppCompatFragment implements UserInfoView {

    UserInfoPresenter presenter = new UserInfoPresenter();

    private TextView first_name, sec_name, email;

    public static UserInfoFragment newInstance() {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate((App) getActivity().getApplicationContext(), this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmnent_userinfo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        first_name = view.findViewById(R.id.first_name);
        sec_name = view.findViewById(R.id.second_name);
        email = view.findViewById(R.id.email);

        view.findViewById(R.id.getInfo).setOnClickListener(presenter::onGetInfoButtonClick);
        view.findViewById(R.id.go_to).setOnClickListener(presenter::GotoSomeWhere);
    }

    @Override
    public void setFirstName(String fName) {
        first_name.setText(fName);
    }

    @Override
    public void setSeconName(String secName) {
        sec_name.setText(secName);
    }

    @Override
    public void setEmail(String eMail) {
        email.setText(eMail);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
