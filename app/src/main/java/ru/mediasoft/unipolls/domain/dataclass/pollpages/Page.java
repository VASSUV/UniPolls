package ru.mediasoft.unipolls.domain.dataclass.pollpages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Page {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("position")
    @Expose
    public Integer position;

    public void setId(String id) {
        this.id = id;
    }
}
