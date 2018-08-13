package ru.mediasoft.unipolls.presentation.currentquestion;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.Choice;

@StateStrategyType(SingleStateStrategy.class)
public interface CurrentQuestionView extends MvpView {
    void showErrorMessage(String message);
    /*void setQuestionTitle(String questionTitle);
    void setAnswersList(List<Choice> answersList);
    void setQuestionPosition(String position);*/
    void setResult(String questionTitle, List<Choice> answersList, String position);
}
