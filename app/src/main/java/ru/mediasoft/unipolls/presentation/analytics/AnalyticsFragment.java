package ru.mediasoft.unipolls.presentation.analytics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.domain.dataclass.analytics.PollRollUps;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.other.events.ShowMessageEvent;

public class AnalyticsFragment extends MvpAppCompatFragment implements AnalyticsView {
    public static final String TAG = "AnalyticsFragment";
    @InjectPresenter
    AnalyticsPresenter mAnalyticsPresenter;

    private String pollId;
    private AnAdapter adapter;
    private ExpandableListView expandableListView;

    public static AnalyticsFragment newInstance(Bundle args) {
        AnalyticsFragment fragment = new AnalyticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_analytics, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAnalyticsPresenter.onCreate();

        if (getArguments() != null) {
            pollId = getArguments().getString(Constants.BundleKeys.POLL_ID_KEY);
        } else EventBus.getDefault().post(new ShowMessage("getArguments() == null!"));

       // Log.i("MyLogs", App.getDBRepository().getPollNameViaId(pollId));

        expandableListView = view.findViewById(R.id.an_exListView);
        ArrayList<String> questList = new ArrayList<>();
        questList.add("Question 1");
        questList.add("Question 2");
        questList.add("Question 3");
        questList.add("Question 4");

        ArrayList<String> ansList = new ArrayList<>();
        ansList.add("answer 1");
        ansList.add("answer 2");
        ansList.add("answer 3");
        ansList.add("answer 4");

        ArrayList<ArrayList<String>> ansGroups = new ArrayList<>();
//        mAnalyticsPresenter.loadRollUps(pollId);
        ansGroups.add(ansList);
        ansGroups.add(ansList);
        adapter = new AnAdapter(getContext(), questList, ansGroups);
        expandableListView.setAdapter(adapter);
    }

    @Override
    public void setAnQuestionsData(PollRollUps pollRollUps) {
//        adapter.setAnList(pollRollUps.data);
//        adapter.notifyDataSetChanged();
    }
}
