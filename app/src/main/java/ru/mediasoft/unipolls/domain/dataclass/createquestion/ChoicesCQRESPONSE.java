package ru.mediasoft.unipolls.domain.dataclass.createquestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChoicesCQRESPONSE {
    @SerializedName("quiz_options")
    @Expose
    public QuizOptions quizOptions;
    @SerializedName("visible")
    @Expose
    public Boolean visible;
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("position")
    @Expose
    public Integer position;
    @SerializedName("id")
    @Expose
    public String id;
}
