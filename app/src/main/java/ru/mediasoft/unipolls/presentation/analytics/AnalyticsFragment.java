package ru.mediasoft.unipolls.presentation.analytics;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.domain.dataclass.analytics.PollRollUps;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.other.events.ShowMessage;

public class AnalyticsFragment extends MvpAppCompatFragment implements AnalyticsView {
    public static final String TAG = "AnalyticsFragment";
    @InjectPresenter
    AnalyticsPresenter mAnalyticsPresenter;

    private String pollId;
    private RecyclerView an_recView;
    private AnAdapter adapter;

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
        an_recView = view.findViewById(R.id.an_recView);
        an_recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<String> questList = new ArrayList<>();
        questList.add("Да?");
        questList.add("No&");
        questList.add("Yes?");
        questList.add("Maybe?");
        adapter = new AnAdapter();
        an_recView.setAdapter(adapter);
        adapter.setAnList(questList);
        adapter.notifyDataSetChanged();

//        mAnalyticsPresenter.loadRollUps(pollId);
    }

    @Override
    public void setAnQuestionsData(PollRollUps pollRollUps) {
//        adapter.setAnList(pollRollUps.data);
//        adapter.notifyDataSetChanged();
    }
}
