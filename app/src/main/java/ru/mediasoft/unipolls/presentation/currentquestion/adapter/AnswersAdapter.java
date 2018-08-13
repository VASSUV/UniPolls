package ru.mediasoft.unipolls.presentation.currentquestion.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.Choice;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.AnswersViewHolder> {

    private List<Choice> answers;

    public AnswersAdapter() {
        this.answers = new ArrayList<>();
    }

    public void setAnswers(List<Choice> answers) {
        this.answers = answers;
    }

    @NonNull
    @Override
    public AnswersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item, parent, false);
        return new AnswersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersViewHolder holder, int position) {
        Choice currentAnswer = answers.get(position);

        holder.txtAnswerPos.setText(String.valueOf(currentAnswer.position));
        holder.txtAnswerTitle.setText(String.valueOf(currentAnswer.text));
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public class AnswersViewHolder extends RecyclerView.ViewHolder{

        private TextView txtAnswerPos, txtAnswerTitle;

        public AnswersViewHolder(View itemView) {
            super(itemView);

            txtAnswerPos = itemView.findViewById(R.id.txtAnswerPos);
            txtAnswerTitle = itemView.findViewById(R.id.txtAnswerTitle);
        }
    }
}
