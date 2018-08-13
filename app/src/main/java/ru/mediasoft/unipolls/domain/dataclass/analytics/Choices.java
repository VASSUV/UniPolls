package ru.mediasoft.unipolls.domain.dataclass.analytics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Choices {
    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("id")
    @Expose
    public String id;
}
