package ru.mediasoft.unipolls.presentation.polls;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.domain.dataclass.Poll;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.presentation.polls.adapter.PollsAdapter;
import ru.mediasoft.unipolls.domain.dataclass.SearchResult;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

public class PollListFragment extends MvpAppCompatFragment implements PollListView, PollsAdapter.OnDetailButtonClickListener{
    public static final String TAG = "PollListFragment";
    @InjectPresenter
    PollListPresenter presenter;

    private RecyclerView recView;
    private PollsAdapter adapter;

    private List<Poll> pollList;

    private ProgressBar progBar;

    public static PollListFragment newInstance() {
        PollListFragment fragment = new PollListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.onCreate();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poll_list, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstOperations(view);

        presenter.onRequest();
    }

    private void firstOperations(View view) {
        recView = view.findViewById(R.id.recView);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new PollsAdapter(getActivity());
        adapter.setOnDetailButtonClickListener(PollListFragment.this);

        recView.setAdapter(adapter);

        progBar = view.findViewById(R.id.progBar);
        progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onButtonClick(int position) {
        Bundle args = new Bundle();
        args.putString(Constants.BundleKeys.POLL_TITLE_KEY, pollList.get(position).getTitle());
        args.putString(Constants.BundleKeys.POLL_ID_KEY, pollList.get(position).getId());
        presenter.goToDetailActivity(args);
    }

    @Override
    public void setSurveysData(SearchResult searchResult) {
        pollList = searchResult.getData();
        adapter.setPollList(pollList);
        adapter.notifyDataSetChanged();
        progBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage(Throwable e) {
        Toast.makeText(getActivity(), "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
        Log.i("sanya", "Error: " + e.toString());
        presenter.onStop();
    }
}
