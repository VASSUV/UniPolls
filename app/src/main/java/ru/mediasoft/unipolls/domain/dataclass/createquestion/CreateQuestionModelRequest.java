package ru.mediasoft.unipolls.domain.dataclass.createquestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CreateQuestionModelRequest {
    @SerializedName("headings")
    @Expose
    public List<HeadingCQ> headings = new ArrayList<>();
    @SerializedName("family")
    @Expose
    public String family = "single_choice";
    @SerializedName("subtype")
    @Expose
    public String subtype = "vertical";
    @SerializedName("answers")
    @Expose
    public AnswersCQ answers = new AnswersCQ();
    @SerializedName("position")
    @Expose
    public Integer position;
}
