package com.pu.gouthelper.bean;

/**
 * Created by Requiem on 2016/3/20.
 */
public class GoutDrug {
    private String id;
    private String title;
    private String pic;
    private String ups;//赞数
    private String comments;//回复数
    private String recom;
    private String tm;
    private String downs;
    private boolean isSelect = false;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setUps(String ups) {
        this.ups = ups;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setRecom(String recom) {
        this.recom = recom;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPic() {
        return pic;
    }

    public String getUps() {
        return ups;
    }

    public String getComments() {
        return comments;
    }

    public String getRecom() {
        return recom;
    }

    public String getTm() {
        return tm;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public String getDowns() {
        return downs;
    }

    public void setDowns(String downs) {
        this.downs = downs;
    }
}
