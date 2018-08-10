package ru.mediasoft.unipolls.domain.dataclass.polldetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResultDetails {
    @SerializedName("response_count")
    @Expose
    public Integer responseCount;
    @SerializedName("date_created")
    @Expose
    public String dateCreated;
    @SerializedName("question_count")
    @Expose
    public Integer questionCount;
    @SerializedName("date_modified")
    @Expose
    public String dateModified;
    @SerializedName("pages")
    @Expose
    public List<Page> pages = null;

}
