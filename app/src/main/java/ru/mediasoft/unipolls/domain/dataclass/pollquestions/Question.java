package ru.mediasoft.unipolls.domain.dataclass.pollquestions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question {
    @SerializedName("position")
    @Expose
    public Integer position;
    @SerializedName("heading")
    @Expose
    public String heading;
    @SerializedName("id")
    @Expose
    public String id;
}
