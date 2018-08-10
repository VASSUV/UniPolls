package ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeadingQuestion {
    @SerializedName("heading")
    @Expose
    public String heading;
}
