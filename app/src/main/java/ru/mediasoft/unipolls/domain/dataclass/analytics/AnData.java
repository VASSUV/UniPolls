package ru.mediasoft.unipolls.domain.dataclass.analytics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnData {

    @SerializedName("subtype")
    @Expose
    public String subtype;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("family")
    @Expose
    public String family;
    @SerializedName("summary")
    @Expose
    public List<Summary> summary = null;

}
