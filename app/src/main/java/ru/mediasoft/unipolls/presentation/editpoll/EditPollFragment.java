package ru.mediasoft.unipolls.presentation.editpoll;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.presentation.main.MainActivity;

public class EditPollFragment extends MvpAppCompatFragment implements EditPollView, EditPollAdapter.onQuestButtonClickListener {
    public static final String TAG = "EditPollFragment";
    @InjectPresenter
    EditPollPresenter mEditPollPresenter;

    EditPollAdapter adapter;

    RecyclerView recView;

    TextView editPollName;
    String pollId, pollName;

    List<String> list;

    public static EditPollFragment newInstance(Bundle args) {
        EditPollFragment fragment = new EditPollFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_poll, container, false);

    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editPollName = view.findViewById(R.id.editPoll_pollName);

        if (getArguments() != null) {
            pollName = getArguments().getString(Constants.BundleKeys.POLL_TITLE_KEY);
            pollId = getArguments().getString(Constants.BundleKeys.POLL_ID_KEY);
        }

        list = new ArrayList<>();
        editPollName.setText(pollName);
        recView = view.findViewById(R.id.editPoll_recView);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new EditPollAdapter();
        adapter.setOnQuestButtonClickListener(EditPollFragment.this);

        recView.setAdapter(adapter);

        if(list.size() < 3) {
            list.add("Как дела?");
            list.add("нормально");
            list.add("нормаааальна");
        }
        adapter.setQuestList(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onQuestClick(int position) {
        Bundle args = new Bundle();
        args.putString(Constants.BundleKeys.QUESTION_TITLE_KEY, list.get(position));
        Log.i("MyLogs", list.get(position));
        Log.i("MyLogs", "argsEPF: "+args.getString(Constants.BundleKeys.QUESTION_TITLE_KEY));
        mEditPollPresenter.goToEditQuest(args);
    }

    @Override
    public void onAddClick() {
        Bundle args = new Bundle();
        mEditPollPresenter.goToAddQuest(args);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) Objects.requireNonNull(getActivity())).setActionBarTitle("Редактирование опроса");
    }
}
