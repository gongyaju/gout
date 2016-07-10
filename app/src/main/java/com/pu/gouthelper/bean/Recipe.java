package com.pu.gouthelper.bean;

/**
 * Created by Requiem on 2016/3/28.
 */
public class Recipe {

    /**
     * id : 1
     * title : asdfasdf
     * pic : /attachment/2016/02/145533461683385.jpg
     * recom : 4
     * tm : 1455334616
     */

    private String id;
    private String title;
    private String pic;
    private String recom;
    private String tm;

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRecom() {
        return recom;
    }

    public void setRecom(String recom) {
        this.recom = recom;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }
}
