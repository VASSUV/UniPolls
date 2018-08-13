package ru.mediasoft.unipolls.domain.dataclass.analytics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Summary {
    @SerializedName("answered")
    @Expose
    public Integer answered;
    @SerializedName("skipped")
    @Expose
    public Integer skipped;
    @SerializedName("choices")
    @Expose
    public List<Choices> choices = null;
}
