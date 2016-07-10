package com.pu.gouthelper.bean;

import java.util.List;

/**
 * Created by Requiem on 2016/3/18.
 */
public class PurinFoodInfoEntity {

    /**
     * id : 1
     * title : 牛肉
     * alias :
     * pic : /attachment/2016/02/145526585359822.jpg
     * purine : 100
     * calorie : 100
     * fat : 100
     * desc : 基本原则
     * source : {"id":"1","title":"来源测试"}
     * tm : 1455265853
     */

    private String id;
    private String title;
    private String alias;
    private String pic;
    private String purine;
    private String calorie;
    private String fat;
    private String desc;
    /**
     * id : 1
     * title : 来源测试
     */

    private SourceEntity source;
    private String tm;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setPurine(String purine) {
        this.purine = purine;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setSource(SourceEntity source) {
        this.source = source;
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

    public String getAlias() {
        return alias;
    }

    public String getPic() {
        return pic;
    }

    public String getPurine() {
        return purine;
    }

    public String getCalorie() {
        return calorie;
    }

    public String getFat() {
        return fat;
    }

    public String getDesc() {
        return desc;
    }

    public SourceEntity getSource() {
        return source;
    }

    public String getTm() {
        return tm;
    }

    private List<PurinRecipe> purinRecipes;
    private List<PurinNews>  purinNews;

    public List<PurinRecipe> getPurinRecipes() {
        return purinRecipes;
    }

    public void setPurinRecipes(List<PurinRecipe> purinRecipes) {
        this.purinRecipes = purinRecipes;
    }

    public List<PurinNews> getPurinNews() {
        return purinNews;
    }

    public void setPurinNews(List<PurinNews> purinNews) {
        this.purinNews = purinNews;
    }

    public static class SourceEntity {
        private String id;
        private String title;

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }

}
