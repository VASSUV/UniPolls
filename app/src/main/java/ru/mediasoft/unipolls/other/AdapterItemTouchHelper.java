package ru.mediasoft.unipolls.other;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class AdapterItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private AdapterItemTouchListener listener;

    public AdapterItemTouchHelper(int dragDirs, int swipeDirs, AdapterItemTouchListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    public interface AdapterItemTouchListener{
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
