package com.pu.gouthelper.bean;

import java.util.List;

/**
 * Created by Requiem on 2016/4/3.
 */
public class MyTopic {

    /**
     * id : 5
     * uid : 3
     * title : 丰爹
     * attrs : []
     * views : 0
     * comments : 0
     * tm : 1459689639
     * user : {"id":"3","nickname":"","avatar":""}
     */

    private String id;
    private String uid;
    private String title;
    private String views;
    private String comments;
    private String tm;
    /**
     * id : 3
     * nickname :
     * avatar :
     */

    private UserEntity user;
    private List<String> attrs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

}
