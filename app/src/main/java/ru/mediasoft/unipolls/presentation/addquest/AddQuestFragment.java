package ru.mediasoft.unipolls.presentation.addquest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.domain.dataclass.createquestion.ChoicesCQ;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.presentation.main.MainActivity;

public class AddQuestFragment extends MvpAppCompatFragment implements AddQuestView {
    public static final String TAG = "AddQuest";
    @InjectPresenter
    AddQuestPresenter mAddQuestPresenter;

    AddQuestAdapter adapter;
    String pollId;
    RecyclerView recView;
    List<ChoicesCQ> ansList;
    EditText questName;
    String pageId;

    public static AddQuestFragment newInstance(Bundle args) {
        AddQuestFragment fragment = new AddQuestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_quest, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            pollId = getArguments().getString(Constants.BundleKeys.POLL_ID_KEY);
            pageId = getArguments().getString(Constants.BundleKeys.PAGE_ID_KEY);
            Log.i("MyLogs", "pollId: " + pollId);
            Log.i("MyLogs", "pageId: " + pollId);
        }

        view.findViewById(R.id.add_quest_saveButton).setOnClickListener(v -> mAddQuestPresenter.onSaveButtonClick(pollId, pageId, adapter.getAnswerList()));
        Log.i("MyLogs", "pollId: " + pollId);

        questName = view.findViewById(R.id.add_quest_questname);
        questName.addTextChangedListener(mAddQuestPresenter.getTextListener());

        recView = view.findViewById(R.id.add_quest_recView);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AddQuestAdapter();
        recView.setAdapter(adapter);
        ansList = new ArrayList<>();

        ChoicesCQ choice = new ChoicesCQ();
        choice.text = "";
        choice.position = 1;
        ansList.add(choice);
        adapter.setAnswerList(ansList);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) Objects.requireNonNull(getActivity())).setActionBarTitle("Добавление вопроса");
    }
}