package ru.mediasoft.unipolls.domain.dataclass.polldetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Page {
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("question_count")
    @Expose
    private Integer questionCount;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }
}
