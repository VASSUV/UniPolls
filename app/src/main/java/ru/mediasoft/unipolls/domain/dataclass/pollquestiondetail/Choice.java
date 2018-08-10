package ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Choice {
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("position")
    @Expose
    public Integer position;
}
