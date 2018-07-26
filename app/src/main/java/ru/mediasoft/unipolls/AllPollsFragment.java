package ru.mediasoft.unipolls;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AllPollsFragment extends Fragment {

    private RecyclerView recView;
    private PollsAdapter adapter;

    private List<Poll> pollList;

    private ProgressBar progBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_polls, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        progBar = view.findViewById(R.id.progBar);
        progBar.setVisibility(View.VISIBLE);
        doRequest();

        recView = view.findViewById(R.id.recView);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void doRequest() {
        Controller.getSurveyMonkeyApi()
                .getSurveys()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SearchResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(SearchResult searchResult) {
                        pollList = searchResult.getData();
                        adapter = new PollsAdapter(getContext(), pollList);
                        recView.setAdapter(adapter);
                        progBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
