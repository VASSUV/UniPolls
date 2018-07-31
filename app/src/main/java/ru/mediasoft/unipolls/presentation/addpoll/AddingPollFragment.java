package ru.mediasoft.unipolls.presentation.addpoll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.presentation.main.MainActivity;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

public class AddingPollFragment extends MvpAppCompatFragment implements AddingPollView {
    public static final String TAG = "AddingPollFragment";
    @InjectPresenter
    AddingPollPresenter presenter;


    public static AddingPollFragment newInstance() {
        AddingPollFragment fragment = new AddingPollFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adding_poll, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity)getActivity()).setActionBarTitle(getActivity().getResources().getString(R.string.new_poll));
    }
}
