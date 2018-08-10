package ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResultQuestionDetails {
    @SerializedName("answers")
    @Expose
    public Answers answers;

    @SerializedName("headings")
    @Expose
    public List<HeadingQuestion> heading;

    @SerializedName("position")
    @Expose
    public String position;
}
