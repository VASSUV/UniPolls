package ru.mediasoft.unipolls.presentation.addquest;

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
import ru.mediasoft.unipolls.domain.dataclass.createquestion.ChoicesCQ;
import ru.mediasoft.unipolls.other.CustomTextWatcher;

public class AddQuestAdapter extends RecyclerView.Adapter {

    private List<ChoicesCQ> choices = new ArrayList<>();

    public void setAnswerList(List<ChoicesCQ> answerList) {
        this.choices = answerList;
    }

    public List<ChoicesCQ> getAnswerList() {
        for(int i = (choices.size() - 1); i >= 0; i--){
            if(choices.get(i).text.isEmpty() || choices.get(i).text == null){
                choices.remove(i);
            }
        }
        return choices;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addquest_item, parent, false);
        return new AddQuestAdapter.AddQuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AddQuestViewHolder fholder = (AddQuestViewHolder) holder;
        fholder.answerName.setText(choices.get(position).text);
    }

    @Override
    public int getItemCount() {
        return choices.size();
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
                    choices.get(getAdapterPosition()).text = s.toString();
                }
            });

            answerAdd.setOnClickListener(v -> {
                if (v != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ChoicesCQ choice = new ChoicesCQ();
                        choice.text = answerName.getText().toString();
                        choice.position = position + 1;
                        choices.add(position + 1, choice);
                        choices.get(position + 1).text = "";
                        notifyDataSetChanged();
                        itemView.clearFocus();
                    }
                }
            });

            answerDelete.setOnClickListener(v -> {
                if (v != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (choices.size() == 1) {
                            answerName.setText("");
                            choices.get(position).text = "";
                            notifyDataSetChanged();
                        } else {
                            choices.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}