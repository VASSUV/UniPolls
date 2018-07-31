package ru.mediasoft.unipolls.domain.dataclass.polldetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {
    @SerializedName("sorting")
    @Expose
    private Object sorting;
    @SerializedName("family")
    @Expose
    private String family;
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("required")
    @Expose
    private Object required;
    @SerializedName("answers")
    @Expose
    private Answers answers;
    @SerializedName("visible")
    @Expose
    private Boolean visible;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("headings")
    @Expose
    private List<Heading> headings = null;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("validation")
    @Expose
    private Object validation;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("forced_ranking")
    @Expose
    private Boolean forcedRanking;

    public Object getSorting() {
        return sorting;
    }

    public void setSorting(Object sorting) {
        this.sorting = sorting;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public Object getRequired() {
        return required;
    }

    public void setRequired(Object required) {
        this.required = required;
    }

    public Answers getAnswers() {
        return answers;
    }

    public void setAnswers(Answers answers) {
        this.answers = answers;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Heading> getHeadings() {
        return headings;
    }

    public void setHeadings(List<Heading> headings) {
        this.headings = headings;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Object getValidation() {
        return validation;
    }

    public void setValidation(Object validation) {
        this.validation = validation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getForcedRanking() {
        return forcedRanking;
    }

    public void setForcedRanking(Boolean forcedRanking) {
        this.forcedRanking = forcedRanking;
    }

}
