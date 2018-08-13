package ru.mediasoft.unipolls.domain.dataclass.pollpages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResultPages {
    @SerializedName("total")
    @Expose
    public Integer total;
    @SerializedName("data")
    @Expose
    public List<Page> data = null;
}
