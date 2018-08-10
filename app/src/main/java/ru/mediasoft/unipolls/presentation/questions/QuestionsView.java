package ru.mediasoft.unipolls.presentation.questions;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.mediasoft.unipolls.domain.dataclass.pollpages.SearchResultPages;
import ru.mediasoft.unipolls.domain.dataclass.pollquestions.SearchResultQuestions;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface QuestionsView extends MvpView {
    void showErrorMessage(String message);
    void setResult();
}
