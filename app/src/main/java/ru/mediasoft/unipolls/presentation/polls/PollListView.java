package ru.mediasoft.unipolls.presentation.polls;

import com.arellomobile.mvp.MvpView;

import ru.mediasoft.unipolls.domain.dataclass.SearchResult;

public interface PollListView extends MvpView {
    void setSurveysData(SearchResult searchResult);
    void showErrorMessage(Throwable e);
}
