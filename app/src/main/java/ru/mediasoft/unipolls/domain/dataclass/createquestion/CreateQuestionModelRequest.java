package ru.mediasoft.unipolls.domain.dataclass.createquestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateQuestionModel {
    @SerializedName("headings")
    @Expose
    public List<HeadsCQ> headings = null;
    @SerializedName("family")
    @Expose
    public String family = "single_choice";
    @SerializedName("subtype")
    @Expose
    public String subtype = "vertical";
    @SerializedName("answers")
    @Expose
    public AnswersCQ answers;
    @SerializedName("position")
    @Expose
    public Integer position;
}
