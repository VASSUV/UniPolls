package ru.mediasoft.unipolls.presentation.polls.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.domain.dataclass.polldetails.SearchResultDetails;
import ru.mediasoft.unipolls.domain.dataclass.polllist.Poll;
import ru.mediasoft.unipolls.domain.interactor.LoadSurveyDetailsInteractor;
import ru.mediasoft.unipolls.other.events.ShowMessage;

public class PollsAdapter extends RecyclerView.Adapter<PollsAdapter.PollsViewHolder> {

    private OnDetailButtonClickListener onDetailButtonClickListener;

    private Context ctx;
    private List<Poll> pollList;

    private LoadSurveyDetailsInteractor loadSurveyDetailsInteractor;
    public interface OnDetailButtonClickListener{
        void onButtonClick(int position);
    }

    public void setOnDetailButtonClickListener(OnDetailButtonClickListener onDetailButtonClickListener){
        this.onDetailButtonClickListener = onDetailButtonClickListener;
    }

    public void setInteractor(){
        loadSurveyDetailsInteractor = new LoadSurveyDetailsInteractor();
    }

    public PollsAdapter(Context ctx) {
        this.ctx = ctx;
        this.pollList = new ArrayList<>();
    }

    public void setPollList(List<Poll> pollList) {
        this.pollList = pollList;
    }

    @NonNull
    @Override
    public PollsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.poll_item, parent, false);
        return new PollsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PollsViewHolder holder, int position) {
        Poll currentPoll = pollList.get(position);

        holder.txtPollName.setText(currentPoll.title);
        loadSurveyDetailsInteractor.getSurveyDetails(App.getSharPref().getToken(), currentPoll.id, new SingleObserver<SearchResultDetails>(){

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(SearchResultDetails searchResultDetails) {
                holder.poll_quest_count.setText(holder.poll_quest_count.getText().toString().concat(String.valueOf(searchResultDetails.questionCount)));
                holder.poll_compl_count.setText(holder.poll_compl_count.getText().toString().concat(String.valueOf(searchResultDetails.responseCount)));
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new ShowMessage(e.getMessage()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pollList.size();
    }

    public class PollsViewHolder extends RecyclerView.ViewHolder{

        private TextView txtPollName, poll_quest_count, poll_compl_count, btnDetails;
        private CardView poll_item;

        public PollsViewHolder(View itemView) {
            super(itemView);

            poll_quest_count = itemView.findViewById(R.id.poll_quest_count);
            poll_compl_count = itemView.findViewById(R.id.poll_compl_count);
            txtPollName = itemView.findViewById(R.id.txtPollName);
            poll_item = itemView.findViewById(R.id.poll_item);
            poll_item.setOnClickListener(v -> {
                if(onDetailButtonClickListener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        onDetailButtonClickListener.onButtonClick(position);
                    }
                }
            });
        }
    }
}
