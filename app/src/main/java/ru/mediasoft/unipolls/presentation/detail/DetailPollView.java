package ru.mediasoft.unipolls.presentation.detail;

import com.arellomobile.mvp.MvpView;

import ru.mediasoft.unipolls.domain.dataclass.polldetails.SearchResultDetails;

public interface DetailPollView extends MvpView {
    void showErrorMessage(String errorMessage);
    void setDateCreated(String dateCreated);
    void setDateModified(String dateModified);
    void setResponseCount(String responseCount);
}
