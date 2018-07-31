package ru.mediasoft.unipolls.domain.dataclass.polldetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Heading {
    @SerializedName("heading")
    @Expose
    private String heading;

    public String getHeading() {
        return heading;
    }
}
