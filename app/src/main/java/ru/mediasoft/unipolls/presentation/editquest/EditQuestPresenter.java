package ru.mediasoft.unipolls.presentation.editquest;

import android.support.v7.widget.RecyclerView;
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
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.Choice;
import ru.mediasoft.unipolls.domain.interactor.ReplaceQuestionInteractor;
import ru.mediasoft.unipolls.other.CustomTextWatcher;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessageEvent;

@InjectViewState
public class EditQuestPresenter extends MvpPresenter<EditQuestView> {

    private String questName;
    private Disposable dispose = null;
    private EditQuestAdapter adapter;
    private List<Choice> ansList = new ArrayList<>();
    private String pollId, pageId, questionId;
    private RecyclerView recView;

    public void onSaveButtonClick() {
        if (questName.isEmpty()) {
            EventBus.getDefault().post(new ShowMessageEvent("Введите название вопроса!"));
        } else {
            replaceQuestion();
        }
    }

    private void replaceQuestion() {

        EventBus.getDefault().post(new ShowLoaderEvent());
        CreateQuestionModelRequest createQuestionModelRequest = new CreateQuestionModelRequest();
        createQuestionModelRequest.headings.add(new HeadingCQ());
        createQuestionModelRequest.headings.get(0).heading = questName;
        createQuestionModelRequest.answers.choices = getAnsList();

        ReplaceQuestionInteractor replaceQuestionInteractor = new ReplaceQuestionInteractor();
        replaceQuestionInteractor.replaceQuestion(App.getSharPref().getToken(), pollId, pageId, questionId, createQuestionModelRequest, new SingleObserver<CreateQuestionModelResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                dispose = d;
            }

            @Override
            public void onSuccess(CreateQuestionModelResponse createQuestionModelResponse) {
                EventBus.getDefault().post(new HideLoaderEvent());
                EventBus.getDefault().post(new ShowMessageEvent("Вопрос изменен"));
            }

            @Override
            public void onError(Throwable e) {
                onStop();
                EventBus.getDefault().post(new HideLoaderEvent());
                EventBus.getDefault().post(new ShowMessageEvent("Вопрос не изменен.\nПричина: " + e.getMessage()));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onStop();
    }

    public void onStop() {
        if (dispose != null && !dispose.isDisposed()) {
            dispose.dispose();
        }
    }

    public TextWatcher getTextListener(String newQuestName) {
        questName = newQuestName;
        return new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                questName = s.toString();
            }
        };
    }

    public EditQuestAdapter getAdapter() {
        return adapter;
    }

    public List<ChoicesCQ> getAnsList() {
        List<ChoicesCQ> ansListCQ = new ArrayList<>();
        for (int i = 0; i < ansList.size() - 1; i++) {
            ansListCQ.add(new ChoicesCQ(ansList.get(i).text));
            ansListCQ.get(i).position = i;
        }
        for (int i = ansList.size() - 1; i >= 0; i--) {
            if (ansList.get(i).text.isEmpty())
                ansList.remove(i);
        }
        rePositItems();
        return ansListCQ;
    }

    private void rePositItems() {
        for (int i = 0; i < ansList.size() - 1; i++) {
            ansList.get(i).position = i + 1;
        }
    }

    public void onCreate(String pollId, String pageId, String questionId) {
        this.pollId = pollId;
        this.pageId = pageId;
        this.questionId = questionId;

        if (adapter == null) {
            adapter = new EditQuestAdapter();
            ansList = App.getDBRepository().getAnsList(questionId);
            Choice c = new Choice();
            c.text = "";
            ansList.add(c);
            adapter.setList(ansList);
            adapter.setIsComputingLayoutListener(() -> recView.isComputingLayout());
            adapter.setListener(new EditQuestAdapter.EditQuestAdapterListener() {
                @Override
                public void changeAnswer(int position, String answerText) {
                    if (position == ansList.size() - 1 && !answerText.isEmpty()) {
                        Choice e = new Choice();
                        e.text = "";
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
                                Choice choice = new Choice();
                                choice.text = "";
                                ansList.add(adapterPosition + 1, choice);    //Добавить пустой
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
                    if (!isFocus && text.isEmpty() && adapterPosition < ansList.size() - 1) {
                        ansList.remove(adapterPosition);
                        adapter.notifyItemRemoved(adapterPosition);
                    }
                }
            });
        }
    }

    public void setRecView(RecyclerView recView) {
        this.recView = recView;
    }
}
