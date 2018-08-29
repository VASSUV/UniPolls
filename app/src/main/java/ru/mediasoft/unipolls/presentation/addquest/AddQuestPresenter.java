package ru.mediasoft.unipolls.presentation.addquest;

import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.createquestion.ChoicesCQ;
import ru.mediasoft.unipolls.domain.dataclass.createquestion.CreateQuestionModelRequest;
import ru.mediasoft.unipolls.domain.dataclass.createquestion.CreateQuestionModelResponse;
import ru.mediasoft.unipolls.domain.dataclass.createquestion.HeadingCQ;
import ru.mediasoft.unipolls.domain.interactor.CreateQuestionInteractor;
import ru.mediasoft.unipolls.other.CustomTextWatcher;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessageEvent;

@InjectViewState
public class AddQuestPresenter extends MvpPresenter<AddQuestView> {

    private String questionName = "";
    private List<ChoicesCQ> ansList = new ArrayList<>();
    private AddQuestAdapter adapter;

    public void onSaveButtonClick(String poll_id, String pageId) {
        if (questionName.isEmpty()) {
            EventBus.getDefault().post(new ShowMessageEvent("Введите название вопроса!"));
        } else {
            createQuestion(poll_id, pageId, getAnswers());
        }
    }

    private void createQuestion(String poll_id, String page_id, List<ChoicesCQ> ansList) {

        CreateQuestionModelRequest createQuestionModelRequest = new CreateQuestionModelRequest();
        createQuestionModelRequest.headings.add(new HeadingCQ());
        createQuestionModelRequest.headings.get(0).heading = questionName;
        createQuestionModelRequest.answers.choices = ansList;
        CreateQuestionInteractor questionInteractor = new CreateQuestionInteractor();
        questionInteractor.createQuestion(App.getSharPref().getToken(), poll_id, page_id, createQuestionModelRequest, new SingleObserver<CreateQuestionModelResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(CreateQuestionModelResponse createQuestionModelResponse) {
                EventBus.getDefault().post(new HideLoaderEvent());
                EventBus.getDefault().post(new ShowMessageEvent("Вопрос создан"));
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new HideLoaderEvent());
                EventBus.getDefault().post(new ShowMessageEvent("Вопрос не создан.\nПричина: " + e.getMessage()));
            }
        });
    }

    public TextWatcher getTextListener() {
        return new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                questionName = s.toString();
            }
        };
    }

    public AddQuestAdapter getAdapter() {
        return adapter;
    }

    private void rePositItems() {
        for (int i = 0; i < ansList.size() - 1; i++) {
            ansList.get(i).position = i + 1;
        }
    }

    public List<ChoicesCQ> getAnswers() {
        for (int i = ansList.size() - 1; i >= 0; i--) {
            if (ansList.get(i).text.isEmpty())
                ansList.remove(i);
        }
        rePositItems();
        return ansList;
    }

    public void onCreate() {
        if (adapter == null) {
            adapter = new AddQuestAdapter();
            ansList.add(new ChoicesCQ(""));
            adapter.setAnswerList(ansList);
            adapter.setListener(new AddQuestAdapter.AddQuestListener() {
                @Override
                public void changeAnswer(int position, String answerText) {
                    if (position == ansList.size() - 1 && !answerText.isEmpty()) {
                        ChoicesCQ e = new ChoicesCQ("");
                        ansList.add(e);
                        adapter.notifyItemInserted(position + 1);
                    }
                    ansList.get(position).text = answerText;
                }

                @Override
                public boolean enterKeyBoardClick(int actionId, String s, int adapterPosition) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {                            //Нажата кнопка OK/ENTER
                        if (ansList.size() - 1 > adapterPosition) {                          //Есть слдующий элемент
                            if (!ansList.get(adapterPosition + 1).text.isEmpty()) {          //Если он не пуст
                                ansList.add(adapterPosition + 1, new ChoicesCQ(""));    //Добавить пустой
                                adapter.notifyItemInserted(adapterPosition + 1);     //
                                return true;
                            }
                        }
                    }
                    return false;
                }

                @Override
                public void onDeleteClick(int position) {
                    if (position == ansList.size() - 1){
                        ansList.get(position).text = "";
                    }else {
                        ansList.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                }

                @Override
                public void onFocusChanged(int adapterPosition, boolean isFocus) {
                    String text = ansList.get(adapterPosition).text;
                    if(!isFocus && text.isEmpty() && adapterPosition < ansList.size() - 1){
                        ansList.remove(adapterPosition);
                        adapter.notifyItemRemoved(adapterPosition);
                    }
                }
            });
        }
    }
}