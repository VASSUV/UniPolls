package ru.mediasoft.unipolls.presentation.analytics;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.mediasoft.unipolls.R;

public class AnItemAdapter extends RecyclerView.Adapter {

    private List<String> answData;

    public void setList(List<String> answData){
        this.answData = answData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.analytics_item_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder Iholder = (ItemViewHolder)holder;
        Iholder.an_item_item.setText(answData.get(position));
    }

    @Override
    public int getItemCount() {
        return answData.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView an_item_item;

        public ItemViewHolder(View itemView) {
            super(itemView);
            an_item_item = itemView.findViewById(R.id.an_item_item);
        }
    }
}