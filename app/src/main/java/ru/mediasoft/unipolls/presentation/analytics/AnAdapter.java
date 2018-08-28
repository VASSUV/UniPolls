package ru.mediasoft.unipolls.presentation.analytics;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.other.customviews.ProgressView;
import ru.mediasoft.unipolls.presentation.editpoll.QuestionListWithIdModel;

public class AnAdapter extends BaseExpandableListAdapter {
    private List<QuestionListWithIdModel> questList;
    private List<AnswersModel> ansList;
    private Context context;
    private float chance;
    private ValueAnimator animator;
    private float animatedValue;
    private int selectedGroupPosition;

    AnAdapter(Context context) {
        this.context = context;
    }

    public void setAdapterData(List<QuestionListWithIdModel> questList, List<AnswersModel> groups) {
        this.questList = questList;
        this.ansList = groups;
    }

    public void setNewAdapterData(List<QuestionListWithIdModel> questList, List<AnswersModel> groups) {
        this.questList = questList;
        this.ansList = groups;
        for (int i = 0; i < ansList.size(); i++) {
            Collections.sort(ansList.get(i).choices, new MyComparator());
        }
    }

    @Override
    public int getGroupCount() {
        return questList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ansList.get(groupPosition).choices.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return questList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ansList.get(groupPosition).choices.get(childPosition);
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.analytics_group, null);
        }

        TextView questName = convertView.findViewById(R.id.an_question_name);
        questName.setText(questList.get(groupPosition).questionName);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.analytics_child, null);
        }

        TextView ansName = convertView.findViewById(R.id.an_answName);
        TextView percent = convertView.findViewById(R.id.an_percent);
        ProgressView progressView = convertView.findViewById(R.id.an_progressView);
        ansName.setText(ansList.get(groupPosition).choices.get(childPosition).name);

        if (ansList.get(groupPosition).choices.get(childPosition).answered != null) {
            if (ansList.get(groupPosition).choices.get(childPosition).answered == 0) {
                chance = 0;
            } else {
                chance = (float) ansList.get(groupPosition).choices.get(childPosition).answered / (float) ansList.get(groupPosition).questionAnswered;
            }
            chance *= 100;
            percent.setText(String.format(Locale.US, "%.1f", chance).concat("%"));
            progressView.setProgressPaintColor(R.color.colorSMLightGreen);
            progressView.setProgressValue(groupPosition == this.selectedGroupPosition ? (chance * animatedValue / 100) : chance);
            progressView.invalidate();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void startGroupAnimation() {
        animator =  ValueAnimator.ofFloat(0, 100);
        animator.addUpdateListener(animation -> {
            animatedValue = (float)animation.getAnimatedValue();
            notifyDataSetChanged();
        });
        animator.setInterpolator(new AccelerateInterpolator(1.5f));
        animator.setDuration(1500);
        animator.start();
    }

    public void setSelectedGroupPosition(int selectedGroupPosition) {
        this.selectedGroupPosition = selectedGroupPosition;
    }
}