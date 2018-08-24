package ru.mediasoft.unipolls.domain.dataclass.createquestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AnswersCQ {
    @SerializedName("choices")
    @Expose
    public List<ChoicesCQ> choices = new ArrayList<>();
}