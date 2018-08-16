package ru.mediasoft.unipolls.presentation.addquest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.other.CustomTextWatcher;

public class AddQuestAdapter extends RecyclerView.Adapter {

    private List<String> answerList = new ArrayList<>();

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.addquest_item, parent, false);
        return new AddQuestAdapter.AddQuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AddQuestViewHolder fholder = (AddQuestViewHolder) holder;
        fholder.answerName.setText(answerList.get(position));
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    public class AddQuestViewHolder extends RecyclerView.ViewHolder {
        EditText answerName;

        Button answerAdd, answerDelete;

        public AddQuestViewHolder(View itemView) {
            super(itemView);

            answerName = itemView.findViewById(R.id.addquest_answer);
            answerAdd = itemView.findViewById(R.id.addquest_AddButton);
            answerDelete = itemView.findViewById(R.id.addquest_deleteButton);

            answerName.addTextChangedListener(new CustomTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    super.onTextChanged(s, start, before, count);
                    answerList.set(getAdapterPosition(), s.toString());
                }
            });

            answerAdd.setOnClickListener(v -> {
                if (v != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        answerList.add(position + 1, "");
                        notifyDataSetChanged();
                        itemView.clearFocus();
                    }
                }
            });

            answerDelete.setOnClickListener(v -> {
                if (v != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (answerList.size() == 1) {
                            answerName.setText("");
                            answerList.set(position, "");
                            notifyDataSetChanged();
                        } else {
                            answerList.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}