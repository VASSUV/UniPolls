package ru.mediasoft.unipolls.domain.dataclass.pollquestions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResultQuestions {
    @SerializedName("total")
    @Expose
    public Integer total;
    @SerializedName("data")
    @Expose
    public List<Question> questionList = null;
}
