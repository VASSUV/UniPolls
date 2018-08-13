package ru.mediasoft.unipolls.presentation.polls;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.mediasoft.unipolls.domain.dataclass.polllist.Poll;
import ru.mediasoft.unipolls.domain.dataclass.polllist.SearchResultSurveys;

public interface PollListView extends MvpView {
    void setPollList(List<Poll> pollList);
    void showErrorMessage(Throwable e);
}
