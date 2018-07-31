package ru.mediasoft.unipolls.presentation.polls.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.domain.dataclass.Poll;

public class PollsAdapter extends RecyclerView.Adapter<PollsAdapter.PollsViewHolder> {

    public interface OnDetailButtonClickListener{
        void onButtonClick(int position);
    }

    private OnDetailButtonClickListener onDetailButtonClickListener;

    public void setOnDetailButtonClickListener(OnDetailButtonClickListener onDetailButtonClickListener){
        this.onDetailButtonClickListener = onDetailButtonClickListener;
    }

    private Context ctx;
    private List<Poll> pollList;

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

        holder.txtPollName.setText(currentPoll.getTitle());
        //holder.txtCompletedCount.setText(currentPoll.get());
    }

    @Override
    public int getItemCount() {
        return pollList.size();
    }

    public class PollsViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgLogo, imgFlag;
        private TextView txtPollName, txtCompletedCount;
        private TextView btnDetails;

        public PollsViewHolder(View itemView) {
            super(itemView);

            imgFlag = itemView.findViewById(R.id.imgFlag);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            txtPollName = itemView.findViewById(R.id.txtPollName);
            //txtCompletedCount = itemView.findViewById(R.id.txtCompletedCount);
            btnDetails = itemView.findViewById(R.id.btnDetails);
            btnDetails.setOnClickListener(v -> {
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
