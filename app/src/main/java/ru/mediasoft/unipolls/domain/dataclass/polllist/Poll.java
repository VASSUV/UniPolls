package ru.mediasoft.unipolls.domain.dataclass.polllist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Poll {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
