package ru.mediasoft.unipolls.domain.dataclass.polldetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Page {
    @SerializedName("questions")
    @Expose
    public List<Question> questions = null;
    @SerializedName("question_count")
    @Expose
    public Integer questionCount;
}
