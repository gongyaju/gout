package com.pu.gouthelper.bean;

import java.util.List;

/**
 * Created by Requiem on 2016/3/31.
 * 帖子列表
 */
public class Topic {

    /**
     * id : 1
     * title : 主题测试
     * attrs : ["/attachment/2016/02/145534175918055.jpg","/attachment/2016/02/145534184594721.jpg","/attachment/2016/02/145534191578815.jpg",""]
     * views : 12
     * comments : 0
     * tm : 0
     * user : {"id":"1","nickname":"aaabbb","avatar":"/attachment/2016/02/145563314816080.JPG"}
     */

    private String id;
    private String uid;
    private String title;
    private String views;
    private String comments;
    private String tm;
    private String content;

    private UserEntity user;
    private List<String> attrs;

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

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<String> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<String> attrs) {
        this.attrs = attrs;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
