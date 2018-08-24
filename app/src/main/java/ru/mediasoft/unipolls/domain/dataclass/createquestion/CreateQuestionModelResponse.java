package ru.mediasoft.unipolls.domain.dataclass.createquestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateQuestionModelResponse {
    @SerializedName("family")
    @Expose
    public String family;
    @SerializedName("subtype")
    @Expose
    public String subtype;
    @SerializedName("answers")
    @Expose
    public AnswersCQ answers;
    @SerializedName("headings")
    @Expose
    public List<HeadingCQ> headings = null;
    @SerializedName("id")
    @Expose
    public String id;
}
