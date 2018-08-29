package ru.mediasoft.unipolls.presentation.editquest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Objects;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.presentation.main.MainActivity;

public class EditQuestFragment extends MvpAppCompatFragment implements EditQuestView {
    public static final String TAG = "EditQuestFragment";
    @InjectPresenter
    EditQuestPresenter mEditQuestPresenter;
    private EditText questName;
    private RecyclerView recView;
    private String pageId, pollId, questonId;

    public static EditQuestFragment newInstance(Bundle args) {
        EditQuestFragment fragment = new EditQuestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pollId = getArguments().getString(Constants.BundleKeys.POLL_ID_KEY);
            pageId = getArguments().getString(Constants.BundleKeys.PAGE_ID_KEY);
            questonId = getArguments().getString(Constants.BundleKeys.QUESTION_ID_KEY);
        }
        mEditQuestPresenter.onCreate(pollId, pageId, questonId);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_quest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questName = view.findViewById(R.id.editQuest_questName);

        if (getArguments() != null) {
            questName.setText(getArguments().getString(Constants.BundleKeys.QUESTION_TITLE_KEY));
        }
        questName.addTextChangedListener(mEditQuestPresenter.getTextListener(questName.getText().toString()));
        view.findViewById(R.id.edit_quest_saveButton).setOnClickListener(v -> mEditQuestPresenter.onSaveButtonClick());
        recView = view.findViewById(R.id.edit_quest_recView);
        mEditQuestPresenter.setRecView(recView);
        recView.setAdapter(mEditQuestPresenter.getAdapter());
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) Objects.requireNonNull(getActivity())).setActionBarTitle("Редактирование вопроса");
    }
}
