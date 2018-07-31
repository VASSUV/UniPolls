package ru.mediasoft.unipolls.domain.dataclass.polldetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Page {
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;
    @SerializedName("question_count")
    @Expose
    private Integer questionCount;

    public List<Question> getQuestions() {
        return questions;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }
}
