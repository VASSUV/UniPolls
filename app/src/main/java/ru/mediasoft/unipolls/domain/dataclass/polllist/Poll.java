package ru.mediasoft.unipolls.domain.dataclass.polllist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Poll {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("title")
    @Expose
    public String title;
}
