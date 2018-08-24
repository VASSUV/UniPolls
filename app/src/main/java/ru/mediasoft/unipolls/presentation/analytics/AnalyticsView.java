package ru.mediasoft.unipolls.presentation.analytics;

import com.arellomobile.mvp.MvpView;

import ru.mediasoft.unipolls.domain.dataclass.analytics.PollRollUps;

public interface AnalyticsView extends MvpView {
    void setAnQuestionsData(PollRollUps pollRollUps);
    void setMainData();
}
