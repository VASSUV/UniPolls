package ru.mediasoft.unipolls.presentation.analytics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.mediasoft.unipolls.R;

public class AnAdapter extends BaseExpandableListAdapter {
    private ArrayList<String> questList;
    private ArrayList<ArrayList<String>> ansList;
    private Context context;

    AnAdapter(Context context, ArrayList<String> questList, ArrayList<ArrayList<String>> groups){
        this.context = context;
        this.questList = questList;
        this.ansList = groups;
    }

    @Override
    public int getGroupCount() {
        return ansList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ansList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return ansList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ansList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.analytics_group, null);
        }

        TextView questName = convertView.findViewById(R.id.an_question_name);
        questName.setText(questList.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.analytics_child, null);
        }
        TextView ansName = convertView.findViewById(R.id.an_child);
        ansName.setText(ansList.get(groupPosition).get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}