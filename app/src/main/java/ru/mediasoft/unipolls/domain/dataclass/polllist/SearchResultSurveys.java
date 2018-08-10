package ru.mediasoft.unipolls.domain.dataclass.polllist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResultSurveys {
    @SerializedName("data")
    @Expose
    public List<Poll> pollList = null;
}
