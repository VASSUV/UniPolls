package ru.mediasoft.unipolls.presentation.addpoll;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.presentation.main.MainActivity;

public class AddingPollFragment extends MvpAppCompatFragment implements AddingPollView {
    public static final String TAG = "AddingPollFragment";
    @InjectPresenter
    AddingPollPresenter presenter;

    private EditText poll_name;

    public static AddingPollFragment newInstance() {
        AddingPollFragment fragment = new AddingPollFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adding_poll, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        poll_name = view.findViewById(R.id.add_poll_name);
        poll_name.addTextChangedListener(presenter.pollNameListener());

        view.findViewById(R.id.add_createPollButton).setOnClickListener(presenter::onCreateButtonClick);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setActionBarTitle(getActivity().getString(R.string.new_poll));
    }
}
