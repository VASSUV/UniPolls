package ru.mediasoft.unipolls.presentation.userInfo;

public interface UserInfoView {
    void setName(String fName, String secName);
    void setEmail(String eMail);
    void showErrorMessage(String message);
    void clearFields();
    void hideProgressBar();
    void showProgressBar();
}
