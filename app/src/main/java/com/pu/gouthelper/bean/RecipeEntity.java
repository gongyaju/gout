package com.pu.gouthelper.bean;

import java.util.List;

/**
 * Created by Requiem on 2016/4/4.
 */
public class RecipeEntity {

    /**
     * id : 2
     * cid : 3
     * title : 西红柿炒鸡蛋
     * pic : /attachment/2016/03/145854457327954.jpg
     * source : 0
     * ingredient : [{"title":"鸡蛋","num":"4个"},{"title":"西红柿","num":"2个"}]
     * material : [{"title":"葱","num":"若干"},{"title":"蒜","num":"若干"}]
     * step : [{"content":"<p>\r\n\t第一步，先打鸡蛋&nbsp; &nbsp; 搅匀\r\n<\/p>\r\n<p>\r\n\t<img src=\"/attachment/2016/03/145854454595421.jpg\" width=\"450\" height=\"301\" align=\"left\" alt=\"\" />\r\n<\/p>"},{"content":"切西红柿，一个西红柿切八刀"}]
     * recom : 0
     * tm : 1458544573
     */

    private String id;
    private String cid;
    private String title;
    private String pic;
    private String source;
    private String recom;
    private String tm;
    /**
     * title : 鸡蛋
     * num : 4个
     */

    private List<IngredientEntity> ingredient;
    /**
     * title : 葱
     * num : 若干
     */

    private List<MaterialEntity> material;
    /**
     * content : <p>
     第一步，先打鸡蛋&nbsp; &nbsp; 搅匀
     </p>
     <p>
     <img src="/attachment/2016/03/145854454595421.jpg" width="450" height="301" align="left" alt="" />
     </p>
     */

    private List<StepEntity> step;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public List<IngredientEntity> getIngredient() {
        return ingredient;
    }

    public void setIngredient(List<IngredientEntity> ingredient) {
        this.ingredient = ingredient;
    }

    public List<MaterialEntity> getMaterial() {
        return material;
    }

    public void setMaterial(List<MaterialEntity> material) {
        this.material = material;
    }

    public List<StepEntity> getStep() {
        return step;
    }

    public void setStep(List<StepEntity> step) {
        this.step = step;
    }

    public static class IngredientEntity {
        private String title;
        private String num;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }

    public static class MaterialEntity {
        private String title;
        private String num;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }

    public static class StepEntity {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
