package ru.mediasoft.unipolls.presentation.editquest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.other.CustomTextWatcher;

public class EditQuestAdapter extends RecyclerView.Adapter {

    private List<String> list;


    public void setList(List<String> list) {
        this.list = list;
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
        fholder.editquest_answName.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
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
                    list.set(getAdapterPosition(), s.toString());
                }
            });

            editquest_addButton.setOnClickListener(v -> {
                if (v != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        list.add(position + 1, "");
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
                            list.set(position, "");
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