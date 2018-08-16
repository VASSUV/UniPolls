package ru.mediasoft.unipolls.domain.dataclass.analytics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PollRollUps {
    @SerializedName("data")
    @Expose
    public List<AnData> data = null;
}
