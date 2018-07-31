package ru.mediasoft.unipolls.domain.dataclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PollInfo {
    @SerializedName("response_count")
    @Expose
    private Integer responseCount;

    @SerializedName("page_count")
    @Expose
    private Integer pageCount;

    @SerializedName("date_created")
    @Expose
    private String dateCreated;

    @SerializedName("folder_id")
    @Expose
    private String folderId;

    @SerializedName("nickname")
    @Expose
    private String nickname;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("question_count")
    @Expose
    private Integer questionCount;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("preview")
    @Expose
    private String preview;

    @SerializedName("is_owner")
    @Expose
    private Boolean isOwner;

    @SerializedName("language")
    @Expose
    private String language;

    @SerializedName("footer")
    @Expose
    private Boolean footer;

    @SerializedName("date_modified")
    @Expose
    private String dateModified;

    @SerializedName("analyze_url")
    @Expose
    private String analyzeUrl;

    @SerializedName("summary_url")
    @Expose
    private String summaryUrl;

    @SerializedName("href")
    @Expose
    private String href;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("collect_url")
    @Expose
    private String collectUrl;

    @SerializedName("edit_url")
    @Expose
    private String editUrl;

    public Integer getResponseCount() {
        return responseCount;
    }

    public void setResponseCount(Integer responseCount) {
        this.responseCount = responseCount;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Boolean isOwner) {
        this.isOwner = isOwner;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getFooter() {
        return footer;
    }

    public void setFooter(Boolean footer) {
        this.footer = footer;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getAnalyzeUrl() {
        return analyzeUrl;
    }

    public void setAnalyzeUrl(String analyzeUrl) {
        this.analyzeUrl = analyzeUrl;
    }

    public String getSummaryUrl() {
        return summaryUrl;
    }

    public void setSummaryUrl(String summaryUrl) {
        this.summaryUrl = summaryUrl;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCollectUrl() {
        return collectUrl;
    }

    public void setCollectUrl(String collectUrl) {
        this.collectUrl = collectUrl;
    }

    public String getEditUrl() {
        return editUrl;
    }

    public void setEditUrl(String editUrl) {
        this.editUrl = editUrl;
    }
}
