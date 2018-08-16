package ru.mediasoft.unipolls.presentation.addquest;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.other.CustomTextWatcher;
import ru.mediasoft.unipolls.presentation.main.MainActivity;

public class AddQuestFragment extends MvpAppCompatFragment implements AddQuestView {
    public static final String TAG = "AddQuest";
    @InjectPresenter
    AddQuestPresenter mAddQuestPresenter;

    List<String> anslist = new ArrayList<>();
    AddQuestAdapter adapter;

    RecyclerView recView;

    EditText questName;

    public static AddQuestFragment newInstance() {
        AddQuestFragment fragment = new AddQuestFragment();
        Bundle args = new Bundle();
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

        questName = view.findViewById(R.id.add_quest_questname);
        questName.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                questName.setText(s.toString());
            }
        });
        recView = view.findViewById(R.id.add_quest_recView);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        view.findViewById(R.id.add_quest_saveButton).setOnClickListener(mAddQuestPresenter.onSaveButtonClick());

        adapter = new AddQuestAdapter();

        recView.setAdapter(adapter);
        anslist.add("");
        adapter.setAnswerList(anslist);
        adapter.notifyDataSetChanged();

//        anslist = adapter.getAnswerList();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) Objects.requireNonNull(getActivity())).setActionBarTitle("Добавление вопроса");
    }
}
