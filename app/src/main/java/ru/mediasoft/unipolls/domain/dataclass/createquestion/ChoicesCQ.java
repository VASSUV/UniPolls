package ru.mediasoft.unipolls.domain.dataclass.createquestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChoicesCQ {
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("visible")
    @Expose
    public Boolean visible = true;
    @SerializedName("position")
    @Expose
    public Integer position;
    public ChoicesCQ(String text){
        this.text = text;
    }
}