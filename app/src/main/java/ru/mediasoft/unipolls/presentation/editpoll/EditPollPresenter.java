package ru.mediasoft.unipolls.presentation.editpoll;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.pollpages.Page;
import ru.mediasoft.unipolls.domain.interactor.DeleteQuestionInteractor;
import ru.mediasoft.unipolls.domain.interactor.LoadMultiPageQuestionsInteractor;
import ru.mediasoft.unipolls.domain.interactor.LoadQuestionDetailInteractor;
import ru.mediasoft.unipolls.domain.interactor.PatchSurveyNameInteractor;
import ru.mediasoft.unipolls.other.CustomTextWatcher;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessageEvent;

@InjectViewState
public class EditPollPresenter extends MvpPresenter<EditPollView> {

    private LoadMultiPageQuestionsInteractor loadMultiPageQuestionsInteractor;
    private LoadQuestionDetailInteractor loadQuestionDetailInteractor;
    private Disposable disposableQuestions = null;
    private Disposable disposableDetails = null;
    private Disposable disposableDelete = null;
    private String pollId;
    private String pollName;
    private Disposable disposablePatch;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadMultiPageQuestionsInteractor = new LoadMultiPageQuestionsInteractor();
        loadQuestionDetailInteractor = new LoadQuestionDetailInteractor();

        if (App.getDBRepository().isHaveAnswers(pollId)) {
            getViewState().setAdapterList();
        } else {
            EventBus.getDefault().post(new ShowLoaderEvent());
            onRequest(pollId);
        }
    }

    public void onCreate(String pollId) {
        this.pollId = pollId;
    }

    public void onRequest(String pollId) {
        EventBus.getDefault().post(new ShowMessageEvent("Обновляем данные"));

        List<Page> pageList = App.getDBRepository().getPageList(pollId);

        loadMultiPageQuestionsInteractor.loadPageQuestions(App.getSharPref().getToken(), pageList, pollId, new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposableQuestions = d;
            }

            @Override
            public void onSuccess(Boolean aBoolean) {

                loadQuestionDetailInteractor.loadQuestionsDetails(pollId, new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableDetails = d;
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        EventBus.getDefault().post(new ShowMessageEvent("Данные обновленны"));
                        getViewState().setAdapterList();
                        getViewState().hideRefreshing();
                        EventBus.getDefault().post(new HideLoaderEvent());
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ShowMessageEvent("Данные не обновленны\n" + e.getMessage()));
                        getViewState().hideRefreshing();
                        EventBus.getDefault().post(new ShowMessageEvent(e.getMessage()));
                        EventBus.getDefault().post(new HideLoaderEvent());
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ShowMessageEvent("Данные не обновленны\n" + e.getMessage()));
                getViewState().hideRefreshing();
                EventBus.getDefault().post(new ShowMessageEvent(e.getMessage()));
                EventBus.getDefault().post(new HideLoaderEvent());
            }
        });
    }

    public void deleteQuestion(String pollId, String pageId, String questionId, int position) {
        DeleteQuestionInteractor deleteQuestionInteractor = new DeleteQuestionInteractor();
        deleteQuestionInteractor.deleteQuestion(App.getSharPref().getToken(), pollId, pageId, questionId, new SingleObserver<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposableDelete = d;
            }

            @Override
            public void onSuccess(Object voidResponse) {
                getViewState().removeAdapterItem(position);
                EventBus.getDefault().post(new ShowMessageEvent("Вопрос удален"));
            }

            @Override
            public void onError(Throwable e) {
                getViewState().refreshAdapter();
                EventBus.getDefault().post(new ShowMessageEvent("Вопрос не удален\nПричниа: " + e.getMessage()));
            }
        });
    }

    public void patchPollName(String pollId){
        EventBus.getDefault().post(new ShowLoaderEvent());
        PatchSurveyNameInteractor patchSurveyNameInteractor = new PatchSurveyNameInteractor();
        patchSurveyNameInteractor.patchSurvey(App.getSharPref().getToken(), pollId, pollName, new SingleObserver<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposablePatch = d;
            }

            @Override
            public void onSuccess(Object o) {
                EventBus.getDefault().post(new ShowMessageEvent("Обновлено"));
                EventBus.getDefault().post(new HideLoaderEvent());
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ShowMessageEvent("Не удалось\nПричина: " + e.getMessage()));
                EventBus.getDefault().post(new HideLoaderEvent());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onStop();
    }

    private void onStop() {
        if (disposableDetails != null && !disposableDetails.isDisposed()) {
            disposableDetails.dispose();
        }
        if (disposableQuestions != null && !disposableQuestions.isDisposed()) {
            disposableQuestions.dispose();
        }
        if (disposableDelete != null && !disposableDelete.isDisposed()){
            disposableDelete.dispose();
        }
    }

    public CustomTextWatcher getTextListener() {
        return new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                pollName = s.toString();
            }
        };
    }
}