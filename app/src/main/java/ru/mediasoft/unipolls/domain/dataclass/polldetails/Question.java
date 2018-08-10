package ru.mediasoft.unipolls.domain.dataclass.polldetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {
    @SerializedName("headings")
    @Expose
    public List<Heading> headings = null;
    @SerializedName("position")
    @Expose
    public Integer position;

}
