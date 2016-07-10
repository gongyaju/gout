package com.pu.gouthelper.bean;

import java.util.List;

/**
 * Created by Requiem on 2016/4/3.
 */
public class TopicDetail {

    /**
     * id : 1
     * uid : 1
     * title : 主题测试
     * content : asdfadfasdfasdfasdf
     * pic : /attachment/2016/02/145534191578815.jpg
     * attrs : ["/attachment/2016/02/145534175918055.jpg","/attachment/2016/02/145534184594721.jpg","/attachment/2016/02/145534191578815.jpg",""]
     * dtx :
     * dty :
     * views : 12
     * comments : 0
     * tm : 0
     * user : {"id":"1","nickname":"aaabbb","avatar":"/attachment/2016/02/145563314816080.JPG"}
     */

    private String id;
    private String uid;
    private String title;
    private String content;
    private String pic;
    private String dtx;
    private String dty;
    private String views;
    private String comments;
    private String tm;
    /**
     * id : 1
     * nickname : aaabbb
     * avatar : /attachment/2016/02/145563314816080.JPG
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDtx() {
        return dtx;
    }

    public void setDtx(String dtx) {
        this.dtx = dtx;
    }

    public String getDty() {
        return dty;
    }

    public void setDty(String dty) {
        this.dty = dty;
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
