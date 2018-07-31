package ru.mediasoft.unipolls.presentation.detail;

import com.arellomobile.mvp.MvpView;

import ru.mediasoft.unipolls.domain.dataclass.PollInfo;

public interface DetailPollView extends MvpView {
    void showErrorMessage(String errorMessage);
    void setResult(PollInfo pollInfo);
}
