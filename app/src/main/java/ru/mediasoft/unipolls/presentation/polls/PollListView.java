package ru.mediasoft.unipolls.presentation.polls;

import com.arellomobile.mvp.MvpView;

import ru.mediasoft.unipolls.domain.dataclass.polllist.SearchResultSurveys;

public interface PollListView extends MvpView {
    void setSurveysData(SearchResultSurveys searchResultSurveys);
}
