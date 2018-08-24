package ru.mediasoft.unipolls.domain.dataclass.createquestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeadingCQ {
    @SerializedName("heading")
    @Expose
    public String heading;
}
