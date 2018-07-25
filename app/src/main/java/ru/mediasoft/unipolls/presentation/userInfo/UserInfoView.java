package ru.mediasoft.unipolls.presentation.userInfo;

public interface UserInfoView {
    void setFirstName(String fName);
    void setSeconName(String secName);
    void setEmail(String eMail);
    void showErrorMessage(String message);
}
