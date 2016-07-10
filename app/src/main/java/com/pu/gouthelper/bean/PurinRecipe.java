package com.pu.gouthelper.bean;

/**
 * Created by Requiem on 2016/3/18.
 */
public class PurinRecipe {

    /**
     * id : 1
     * title : asdfasdf
     * pic : /attachment/2016/02/145533461683385.jpg
     * tm : 1455334616
     */

    private String id;
    private String title;
    private String pic;
    private String tm;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public String getTm() {
        return tm;
    }
}
