package ru.mediasoft.unipolls.presentation.editpoll;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;
import java.util.Objects;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.other.AdapterItemTouchHelper;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.presentation.main.MainActivity;

public class EditPollFragment extends MvpAppCompatFragment implements EditPollView, AdapterItemTouchHelper.AdapterItemTouchListener {
    public static final String TAG = "EditPollFragment";
    @InjectPresenter
    EditPollPresenter mEditPollPresenter;

    EditPollAdapter adapter;

    RecyclerView recView;

    EditText editPollName;
    SwipeRefreshLayout swipeRefreshLayout;
    String pollId, pollName;
    Button editPoll_patchName;
    private String pageId;

    public static EditPollFragment newInstance(Bundle args) {
        EditPollFragment fragment = new EditPollFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pollName = getArguments().getString(Constants.BundleKeys.POLL_TITLE_KEY);
            pollId = getArguments().getString(Constants.BundleKeys.POLL_ID_KEY);
            pageId = App.getDBRepository().getPageId(pollId);
        }
        mEditPollPresenter.onCreate(pollId);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_poll, container, false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editPollName = view.findViewById(R.id.editPoll_pollName);
        editPollName.setText(pollName);
        editPollName.addTextChangedListener(mEditPollPresenter.getTextListener());
        editPoll_patchName = view.findViewById(R.id.editPoll_patchName);
        editPoll_patchName.setOnClickListener(v -> mEditPollPresenter.patchPollName(pollId));
        swipeRefreshLayout = view.findViewById(R.id.swipeToRefreshEditPoll);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorSMGreen, R.color.colorSMOrange, R.color.legacy_primary);
        swipeRefreshLayout.setOnRefreshListener(() -> mEditPollPresenter.onRequest(pollId));

        recView = view.findViewById(R.id.editPoll_recView);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new EditPollAdapter(pollId, pageId);
        recView.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback ItemTouchHelper = new AdapterItemTouchHelper(0, android.support.v7.widget.helper.ItemTouchHelper.LEFT | android.support.v7.widget.helper.ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(ItemTouchHelper).attachToRecyclerView(recView);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) Objects.requireNonNull(getActivity())).setActionBarTitle("Редактирование опроса");
        mEditPollPresenter.onRequest(pollId);
    }

    @Override
    public void hideRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showRefreshing() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void setAdapterList() {
        List<QuestionListWithIdModel> list = App.getDBRepository().getQuestionsListWIthIds(pollId);
        adapter.setQuestList(list);
        refreshAdapter();
    }

    @Override
    public void removeAdapterItem(int position) {
        adapter.removeItem(position);
        mEditPollPresenter.onRequest(pollId);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        mEditPollPresenter.deleteQuestion(pollId, pageId, adapter.getQuestList().get(position).questionId, position);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void refreshAdapter() {
        adapter.notifyDataSetChanged();
    }
}