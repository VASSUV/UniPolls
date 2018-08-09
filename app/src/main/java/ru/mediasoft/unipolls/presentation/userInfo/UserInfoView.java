package ru.mediasoft.unipolls.presentation.userInfo;

import com.arellomobile.mvp.MvpView;

public interface UserInfoView extends MvpView {
    void setName(String fName, String secName);
    void setEmail(String eMail);
    void clearFields();
}
