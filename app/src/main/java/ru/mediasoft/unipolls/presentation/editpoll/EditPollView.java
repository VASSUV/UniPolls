package ru.mediasoft.unipolls.presentation.editpoll;

import com.arellomobile.mvp.MvpView;

public interface EditPollView extends MvpView {
    void setAdapterList();

    void hideRefreshing();

    void showRefreshing();

    void removeAdapterItem(int position);

    void refreshAdapter();
}
