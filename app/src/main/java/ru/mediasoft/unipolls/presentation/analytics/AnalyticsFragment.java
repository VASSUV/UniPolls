package ru.mediasoft.unipolls.presentation.analytics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.greenrobot.eventbus.EventBus;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.other.events.ShowMessage;

public class AnalyticsFragment extends MvpAppCompatFragment implements AnalyticsView {
    public static final String TAG = "AnalyticsFragment";
    @InjectPresenter
    AnalyticsPresenter mAnalyticsPresenter;

    String pollId;

    public static AnalyticsFragment newInstance(Bundle args) {
        AnalyticsFragment fragment = new AnalyticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_analytics, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAnalyticsPresenter.onCreate();

        if(getArguments() != null) {
            pollId = getArguments().getString(Constants.BundleKeys.POLL_ID_KEY);
            mAnalyticsPresenter.loadRollUps(pollId);
        }
        else EventBus.getDefault().post(new ShowMessage("getArguments() == null!"));
    }
}
