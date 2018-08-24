package ru.mediasoft.unipolls.domain.dataclass.createquestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizOptions {
    @SerializedName("score")
    @Expose
    public Integer score;
}
