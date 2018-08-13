package ru.mediasoft.unipolls.presentation.detail;

import com.arellomobile.mvp.MvpView;

public interface DetailPollView extends MvpView {
    void setDateCreated(String dateCreated);
    void setDateModified(String dateModified);
    void setResponseCount(String responseCount);
}
