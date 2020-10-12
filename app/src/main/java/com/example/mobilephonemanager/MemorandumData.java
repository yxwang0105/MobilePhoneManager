package com.example.mobilephonemanager;

import org.litepal.crud.LitePalSupport;

public class MemorandumData extends LitePalSupport {
    private long id;
    private String buildTime;
    private String content;

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MemorandumData{" +
                "id=" + id +
                ", buildTime='" + buildTime + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
