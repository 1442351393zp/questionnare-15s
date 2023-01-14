package com.infoac.cas.entity;

import java.util.List;

/**
 * @Author: PengSenjie
 * @Description: info
 * @Date: Created in 2019/7/9 10:09
 */
public class Info
{

    private long id;
    private String sourceFrom;
    private String title;
    private String content;
    private List<String> contentImg;
    private String contentImgString;

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", sourceFrom='" + sourceFrom + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", contentImg=" + contentImg +
                ", contentImgString='" + contentImgString + '\'' +
                '}';
    }

    public String getContentImgString() {
        return contentImgString;
    }

    public void setContentImgString(String contentImgString) {
        this.contentImgString = contentImgString;
    }

    public String getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
    }

    public List<String> getContentImg() {
        return contentImg;
    }

    public void setContentImg(List<String> contentImg) {
        this.contentImg = contentImg;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
