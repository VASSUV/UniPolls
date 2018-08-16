package ru.mediasoft.unipolls.presentation.addpoll;

import com.arellomobile.mvp.MvpView;

public interface AddingPollView extends MvpView {
    void hidePollNameWindow();
    void showPollNameWindow();
    void hideQuestionsWindow();
    void showQuestionsWindow();
    void setPollName();
}
