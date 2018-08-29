package ru.mediasoft.unipolls.presentation.analytics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.idunnololz.widgets.AnimatedExpandableListView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.domain.dataclass.analytics.PollRollUps;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.Choice;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.other.events.ShowMessageEvent;
import ru.mediasoft.unipolls.presentation.editpoll.QuestionListWithIdModel;

public class AnalyticsFragment extends MvpAppCompatFragment implements AnalyticsView {
    public static final String TAG = "AnalyticsFragment";
    @InjectPresenter
    AnalyticsPresenter mAnalyticsPresenter;

    private String pollId;
    private AnAdapter adapter;
    private AnimatedExpandableListView expandableListView;
    List<QuestionListWithIdModel> questList;
    List<Choice> ansList;
    List<AnswersModel> answersModels;
    List<AnsChoices> ansEndForm;
    private int currentPosition = -1;

    public static AnalyticsFragment newInstance(Bundle args) {
        AnalyticsFragment fragment = new AnalyticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pollId = getArguments().getString(Constants.BundleKeys.POLL_ID_KEY);
        }
        mAnalyticsPresenter.onCreate(pollId);
        mAnalyticsPresenter.onFirstViewAttach();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_analytics, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            pollId = getArguments().getString(Constants.BundleKeys.POLL_ID_KEY);
        } else EventBus.getDefault().post(new ShowMessageEvent("getArguments() == null!"));

        expandableListView = view.findViewById(R.id.an_exListView);

        adapter = new AnAdapter(getContext());
    }

    @Override
    public void setMainData() {
        answersModels = new ArrayList<>();
        AnswersModel answersModel;
        ansEndForm = new ArrayList<>();
        AnsChoices ansChoices;
        questList = App.getDBRepository().getQuestionsListWIthIds(pollId);
        if (questList != null) {

            for (int i = 0; i < questList.size(); i++) {
                ansList = App.getDBRepository().getAnsList(questList.get(i).questionId);
                answersModel = new AnswersModel();
                ansEndForm = new ArrayList<>();

                if (ansList != null) {
                    for (int k = 0; k < ansList.size(); k++) {
                        ansChoices = new AnsChoices();
                        ansChoices.name = ansList.get(k).text;
                        ansChoices.id = ansList.get(k).id;
                        ansEndForm.add(ansChoices);
                    }
                    answersModel.choices = ansEndForm;
                    answersModels.add(answersModel);
                }
            }
            adapter.setAdapterData(questList, answersModels);
            adapter.notifyDataSetChanged();
            expandableListView.setAdapter(adapter);
            expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    if (currentPosition != -1 && currentPosition != groupPosition) {
                        expandableListView.collapseGroupWithAnimation(groupPosition);
                    }
                    expandableListView.expandGroupWithAnimation(groupPosition);
                    expandableListView.collapseGroupWithAnimation(currentPosition);
                    currentPosition = groupPosition;
                    adapter.setSelectedGroupPosition(groupPosition);
                    adapter.startGroupAnimation();

                    return true;
                }
            });
        }
    }

    @Override
    public void setAnQuestionsData(PollRollUps pollRollUps) {
        if (questList != null) {
            for (int i = 0; i < questList.size(); i++) {
                if (pollRollUps != null)
                    for (int l = 0; l < pollRollUps.data.size(); l++) {
                        if (answersModels != null)
                            if (questList.get(i).questionId.equals(pollRollUps.data.get(l).id)) {//Нашли нужный вопрос

                                answersModels.get(i).questionAnswered = pollRollUps.data.get(l).summary.get(0).answered; //Сколько раз ответили на вопрос

                                for (int k = 0; k < answersModels.get(i).choices.size(); k++) {//Пробегаем по локальным ответам

                                    for (int j = 0; j < pollRollUps.data.get(l).summary.get(0).choices.size(); j++) {//Пробегаем по полученным ответам

                                        if (answersModels.get(i).choices.get(k).id.equals(pollRollUps.data.get(l).summary.get(0).choices.get(j).id)) {//Ищем нужный ответ
                                            answersModels.get(i).choices.get(k).answered = pollRollUps.data.get(l).summary.get(0).choices.get(j).count;//Записываем кол-во выборов ответа
                                        }
                                    }
                                }
                            }
                    }
            }
        }
        adapter.setNewAdapterData(questList, answersModels);
        adapter.notifyDataSetChanged();
    }
}