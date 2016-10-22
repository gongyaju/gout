package com.pu.gouthelper.bean;

/**
 * Created by Requiem on 2016/3/28.
 */
public class Recipe {


    /**
     * id : 12
     * cid : 3
     * title : 鸡刨豆腐
     * pic : http://images.tfzs999.com/attachment/2016/10/147658699044861.jpg
     * recom : 4
     * tm : 1476586990
     * cate : 炒菜
     * steps : 4
     */

    private String id;
    private String cid;
    private String title;
    private String pic;
    private String recom;
    private String tm;
    private String cate;
    private int steps;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
