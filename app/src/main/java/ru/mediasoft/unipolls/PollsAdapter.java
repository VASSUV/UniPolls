package ru.mediasoft.unipolls;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PollsAdapter extends RecyclerView.Adapter<PollsAdapter.PollsViewHolder> {

    private Context ctx;
    private List<Poll> pollList;

    public PollsAdapter(Context ctx, List<Poll> pollList) {
        this.ctx = ctx;
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
        holder.txtCompletedCount.setText(currentPoll.getId());
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
            txtCompletedCount = itemView.findViewById(R.id.txtCompletedCount);
            btnDetails = itemView.findViewById(R.id.btnDetails);
        }
    }
}
