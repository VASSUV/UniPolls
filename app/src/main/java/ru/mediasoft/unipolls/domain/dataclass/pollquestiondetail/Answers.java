package ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Answers {
    @SerializedName("choices")
    @Expose
    public List<Choice> choices = null;
}
