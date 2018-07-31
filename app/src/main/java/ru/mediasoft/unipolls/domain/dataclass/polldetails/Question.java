package ru.mediasoft.unipolls.domain.dataclass.polldetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {
    @SerializedName("headings")
    @Expose
    private List<Heading> headings = null;
    @SerializedName("position")
    @Expose
    private Integer position;

    public List<Heading> getHeadings() {
        return headings;
    }

    public Integer getPosition() {
        return position;
    }

}
