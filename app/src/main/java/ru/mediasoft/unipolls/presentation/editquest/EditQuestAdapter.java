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
import ru.mediasoft.unipolls.domain.dataclass.createquestion.ChoicesCQ;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.Choice;
import ru.mediasoft.unipolls.other.CustomTextWatcher;

public class EditQuestAdapter extends RecyclerView.Adapter {

    private List<Choice> list = new ArrayList<>();

    public void setList(List<Choice> list) {
        this.list = list;
    }

    public List<ChoicesCQ> getAnsList() {
        int i = 0;
        List<ChoicesCQ> listCQ = new ArrayList<>();
        ChoicesCQ choice;
        while (i < list.size()) {
            choice = new ChoicesCQ();
            choice.text = list.get(i).text;
            choice.position = i + 1;
            listCQ.add(choice);
            i++;
        }
        return listCQ;
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

        Button editquest_deleteButton, editquest_addButton;

        public EditQuestViewHolder(View itemView) {
            super(itemView);
            editquest_answName = itemView.findViewById(R.id.editquest_answName);
            editquest_addButton = itemView.findViewById(R.id.editquest_addButton);
            editquest_deleteButton = itemView.findViewById(R.id.editquest_deleteButton);

            editquest_answName.addTextChangedListener(new CustomTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    super.onTextChanged(s, start, before, count);
                    list.get(getAdapterPosition()).text = s.toString();
                }
            });

            editquest_addButton.setOnClickListener(v -> {
                if (v != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        list.add(position + 1, new Choice());
                        notifyDataSetChanged();
                        itemView.clearFocus();
                    }
                }
            });

            editquest_deleteButton.setOnClickListener(v -> {
                if (v != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (list.size() == 1) {
                            editquest_answName.setText("");
                            list.get(position).text = "";
                            notifyDataSetChanged();
                        } else {
                            list.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}