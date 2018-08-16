package ru.mediasoft.unipolls.presentation.editpoll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.mediasoft.unipolls.R;

public class EditPollAdapter extends RecyclerView.Adapter {

    private static final int POLLITEMTYPE = 0;
    private static final int BUTTONTYPE = 1;
    private onQuestButtonClickListener questButtonClickListener;
    private List<String> questList;

    public void setQuestList(List<String> questList) {
        this.questList = questList;
    }

    public interface onQuestButtonClickListener {
        void onQuestClick(int position);
        void onAddClick();
    }

    public void setOnQuestButtonClickListener(onQuestButtonClickListener questButtonClickListener) {
        this.questButtonClickListener = questButtonClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        switch (viewType) {
            case POLLITEMTYPE:
                view = LayoutInflater.from(context).inflate(R.layout.editpoll_item, parent, false);
                return new EditPollViewHolder(view);
            case BUTTONTYPE:
                view = LayoutInflater.from(context).inflate(R.layout.editpoll_button_item, parent, false);
                return new EditPollButtonHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i("MyLogs", String.valueOf(position));
        if (getItemViewType(position) == POLLITEMTYPE) {
            String quest = questList.get(position);
            EditPollViewHolder editholder = (EditPollViewHolder) holder;
            editholder.editPollItem_questName.setText(quest);
        }
    }

    @Override
    public int getItemCount() {
        return questList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position < questList.size() ? POLLITEMTYPE : BUTTONTYPE;
    }

    public class EditPollViewHolder extends RecyclerView.ViewHolder {
        TextView editPollItem_questName, editPollItemQuestCount;

        public EditPollViewHolder(View itemView) {
            super(itemView);

            editPollItem_questName = itemView.findViewById(R.id.editPollItem_questName);
            editPollItemQuestCount = itemView.findViewById(R.id.editPollItem_questCount);

            itemView.findViewById(R.id.editPollItem).setOnClickListener(v -> {
                if (questButtonClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        questButtonClickListener.onQuestClick(position);
                    }
                }
            });
        }
    }

    public class EditPollButtonHolder extends RecyclerView.ViewHolder {

        public EditPollButtonHolder(View itemView) {
            super(itemView);

            itemView.findViewById(R.id.editPollButtonItem).setOnClickListener(v -> {
                if (questButtonClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        questButtonClickListener.onAddClick();
                    }
                }
            });
        }
    }
}