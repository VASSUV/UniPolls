package ru.mediasoft.unipolls.presentation.analytics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.mediasoft.unipolls.R;

public class AnAdapter extends RecyclerView.Adapter<AnAdapter.AnViewHolder> {
    private List<String> questionsData;
    private List<String> ansData = new ArrayList<>();

    public void setAnList(List<String> questionsData) {
        this.questionsData = questionsData;
    }

    @NonNull
    @Override
    public AnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        View view = LayoutInflater.from(ctx).inflate(R.layout.analytics_item, parent, false);
        return new AnViewHolder(view, ctx);
    }

    @Override
    public void onBindViewHolder(@NonNull AnViewHolder holder, int position) {
        holder.an_question_name.setText(questionsData.get(position));
    }

    @Override
    public int getItemCount() {
        return questionsData.size();
    }

    public class AnViewHolder extends RecyclerView.ViewHolder {

        TextView an_question_name;
        RecyclerView itemRecView;
        AnItemAdapter itemAdapter;

        public AnViewHolder(View itemView, Context ctx) {
            super(itemView);
            an_question_name = itemView.findViewById(R.id.an_question_name);

            itemView.findViewById(R.id.analytics_item).setOnClickListener(v -> {
                itemRecView = itemView.findViewById(R.id.an_item_recView);
                itemRecView.setLayoutManager(new LinearLayoutManager(ctx));

                itemAdapter = new AnItemAdapter();
                itemRecView.setAdapter(itemAdapter);

                if (ansData.size() < 1) {
                    ansData.add("answer 1");
                    ansData.add("answer 2");
                    ansData.add("answer 3");
                    ansData.add("answer 4");
                }
                itemAdapter.setList(ansData);
            });
        }
    }
}