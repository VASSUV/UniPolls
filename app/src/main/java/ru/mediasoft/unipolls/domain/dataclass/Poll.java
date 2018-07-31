package ru.mediasoft.unipolls.domain.dataclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Poll {
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
