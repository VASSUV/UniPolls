package ru.mediasoft.unipolls.domain.dataclass.addpoll;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class addPollModel {

    @SerializedName("headings")
    @Expose
    public List<Heading> headings = null;
    @SerializedName("family")
    @Expose
    public String family = "single_choice";
    @SerializedName("subtype")
    @Expose
    public String subtype = "vertical";
    @SerializedName("answers")
    @Expose
    public Answers answers;
    @SerializedName("position")
    @Expose
    public Integer position;

    public class Heading {

        @SerializedName("heading")
        @Expose
        public String heading;
    }

    public class Choice {

        @SerializedName("text")
        @Expose
        public String text;
        @SerializedName("visible")
        @Expose
        public Boolean visible;
        @SerializedName("position")
        @Expose
        public Integer position;
    }

    public class Answers {

        @SerializedName("choices")
        @Expose
        public List<Choice> choices = null;
    }
}
