/*
package ru.mediasoft.unipolls.presentation.detail.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.domain.dataclass.polldetails.Question;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {

    private Context ctx;
    private List<Question> questionList;

    public QuestionsAdapter(Context ctx) {
        this.ctx = ctx;
        this.questionList = new ArrayList<>();
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.deatail_item, parent, false);
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position) {
        Question currentQuestion = questionList.get(position);

        String heading = currentQuestion.getHeadings().get(0).getHeading();

        holder.txtHeading.setText(heading);
        holder.txtNum.setText(String.valueOf(currentQuestion.getPosition()));
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder{

        private TextView txtHeading, txtNum;

        public QuestionsViewHolder(View itemView) {
            super(itemView);

            txtHeading = itemView.findViewById(R.id.txtHeading);
            txtNum = itemView.findViewById(R.id.txtNum);
        }
    }
}
*/
