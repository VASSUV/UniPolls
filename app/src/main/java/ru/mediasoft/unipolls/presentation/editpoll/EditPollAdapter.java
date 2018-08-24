package ru.mediasoft.unipolls.presentation.editpoll;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.other.Screen;

public class EditPollAdapter extends RecyclerView.Adapter {

    private static final int POLLITEMTYPE = 0;
    private static final int BUTTONTYPE = 1;
    private final String pollId;
    private final String pageId;
    private List<QuestionListWithIdModel> questList = new ArrayList<>();

    EditPollAdapter(String pollId, String pageId){
        this.pollId = pollId;
        this.pageId = pageId;
    }

    public void setQuestList(List<QuestionListWithIdModel> questList) {
        this.questList = questList;
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
        if (getItemViewType(position) == POLLITEMTYPE) {
            EditPollViewHolder editholder = (EditPollViewHolder) holder;
            editholder.editPollItem_questName.setText(questList.get(position).questionName);
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
        TextView editPollItem_questName;

        public EditPollViewHolder(View itemView) {
            super(itemView);

            editPollItem_questName = itemView.findViewById(R.id.editPollItem_questName);

            itemView.findViewById(R.id.editPollItem).setOnClickListener(v -> {
                Bundle args = new Bundle();
                args.putString(Constants.BundleKeys.POLL_ID_KEY, pollId);
                args.putString(Constants.BundleKeys.PAGE_ID_KEY, pageId);
                args.putString(Constants.BundleKeys.QUESTION_ID_KEY , questList.get(getAdapterPosition()).questionId);
                args.putString(Constants.BundleKeys.QUESTION_TITLE_KEY, questList.get(getAdapterPosition()).questionName);
                App.getRouter().navigateTo(Screen.EDITQUEST.name(), args);
            });
        }
    }

    public class EditPollButtonHolder extends RecyclerView.ViewHolder {

        public EditPollButtonHolder(View itemView) {
            super(itemView);

            itemView.findViewById(R.id.editPollButtonItem).setOnClickListener(v -> {
                Bundle args = new Bundle();
                args.putString(Constants.BundleKeys.POLL_ID_KEY, pollId);
                args.putString(Constants.BundleKeys.PAGE_ID_KEY, pageId);
                App.getRouter().navigateTo(Screen.ADDQUEST.name(), args);
            });
        }
    }
}