package ru.mediasoft.unipolls.domain.dataclass.polldetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Choice {
    @SerializedName("quiz_options")
    @Expose
    private QuizOptions quizOptions;
    @SerializedName("visible")
    @Expose
    private Boolean visible;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("id")
    @Expose
    private String id;

    public QuizOptions getQuizOptions() {
        return quizOptions;
    }

    public void setQuizOptions(QuizOptions quizOptions) {
        this.quizOptions = quizOptions;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
}
