package ru.mediasoft.unipolls.domain.dataclass.polldetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResultDetails {
    @SerializedName("response_count")
    @Expose
    private Integer responseCount;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("question_count")
    @Expose
    private Integer questionCount;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("pages")
    @Expose
    private List<Page> pages = null;

    public Integer getResponseCount() {
        return responseCount;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public String getDateModified() {
        return dateModified;
    }

    public List<Page> getPages() {
        return pages;
    }

}
