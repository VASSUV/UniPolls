package ru.mediasoft.unipolls.presentation.editquest;

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
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.Choice;
import ru.mediasoft.unipolls.other.CustomTextWatcher;

public class EditQuestAdapter extends RecyclerView.Adapter {

    private List<Choice> list = new ArrayList<>();
    private EditQuestAdapterListener editQuestAdapterListener;
    private IsComputingLayoutListener isComputingLayoutListener;

    public void setList(List<Choice> list) {
        this.list = list;
    }

    public void setListener(EditQuestAdapterListener editQuestAdapterListener) {
        this.editQuestAdapterListener = editQuestAdapterListener;
    }

    public interface EditQuestAdapterListener {

        void changeAnswer(int position, String answerText);

        boolean enterKeyBoardClick(int actionId, String s, int adapterPosition);

        void onDeleteClick(int position);

        void onFocusChanged(int adapterPosition, boolean hasFocus);
    }

    public void setIsComputingLayoutListener(IsComputingLayoutListener isComputingLayoutListener) {
        this.isComputingLayoutListener = isComputingLayoutListener;
    }

    public interface IsComputingLayoutListener {
        boolean isComputing();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.editquest_item, parent, false);
        return new EditQuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EditQuestAdapter.EditQuestViewHolder fholder = (EditQuestAdapter.EditQuestViewHolder) holder;
        fholder.editquest_answName.setText(list.get(position).text);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else return 0;
    }

    private class EditQuestViewHolder extends RecyclerView.ViewHolder {
        EditText editquest_answName;

        Button editquest_deleteButton;

        public EditQuestViewHolder(View itemView) {
            super(itemView);
            editquest_answName = itemView.findViewById(R.id.editquest_answName);
            editquest_deleteButton = itemView.findViewById(R.id.editquest_deleteButton);

            editquest_answName.addTextChangedListener(new CustomTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    super.onTextChanged(s, start, before, count);
                    if (!isComputingLayoutListener.isComputing())
                        editQuestAdapterListener.changeAnswer(getAdapterPosition(), s.toString());
                }
            });

            editquest_answName.setOnFocusChangeListener((v, hasFocus) -> editQuestAdapterListener.onFocusChanged(getAdapterPosition(), hasFocus));

            editquest_answName.setOnEditorActionListener((v, actionId, event) -> editQuestAdapterListener.enterKeyBoardClick(actionId, editquest_answName.getText().toString(), getAdapterPosition()));

            editquest_deleteButton.setOnClickListener(v -> {
                if (v != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        editquest_answName.clearFocus();
                        editQuestAdapterListener.onDeleteClick(position);
                    }
                }
            });
        }
    }
}