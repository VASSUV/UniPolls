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
    private AddQuestListener addQuestListener;

    public void setAnswerList(List<ChoicesCQ> answerList) {
        this.choices = answerList;
    }

    public void setListener(AddQuestListener addQuestListener) {
        this.addQuestListener = addQuestListener;
    }

    public interface AddQuestListener {
        void changeAnswer(int position, String answerText);

        boolean enterKeyBoardClick(int actionId, String s, int adapterPosition);

        void onDeleteClick(int position);

        void onFocusChanged(int adapterPosition, boolean isFocus);
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
        if (choices != null)
            return choices.size();
        return 0;
    }

    public class AddQuestViewHolder extends RecyclerView.ViewHolder {
        EditText answerName;

        Button answerDelete;

        public AddQuestViewHolder(View itemView) {
            super(itemView);

            answerName = itemView.findViewById(R.id.addquest_answer);
            answerDelete = itemView.findViewById(R.id.addquest_deleteButton);

            answerDelete.setVisibility(getAdapterPosition() == choices.size() - 1 ? View.GONE : View.VISIBLE);
            answerName.addTextChangedListener(new CustomTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    super.onTextChanged(s, start, before, count);
                    addQuestListener.changeAnswer(getAdapterPosition(), s.toString());
                    answerDelete.setVisibility(getAdapterPosition() == choices.size() - 1 ? View.GONE : View.VISIBLE);
                }
            });

            answerName.setOnFocusChangeListener((v, hasFocus) -> {
                addQuestListener.onFocusChanged(getAdapterPosition(), hasFocus);
            });

            answerName.setOnEditorActionListener((v, actionId, event) -> {
                return addQuestListener.enterKeyBoardClick(actionId, answerName.getText().toString(), getAdapterPosition());
            });

            answerDelete.setOnClickListener(v -> {
                if (v != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        answerName.clearFocus();
                        addQuestListener.onDeleteClick(position);
                    }
                }
            });
        }
    }
}