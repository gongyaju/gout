package com.pu.gouthelper.bean;

/**
 * Created by Requiem on 2016/4/6.
 */
public class SlideEntity {

    /**
     * "id": "10",
     * "title": "写给痛风新手",
     * "pic": "http://images.tfzs999.com/attachment/2016/09/147411303948576.jpg",
     * "url": "#",
     * "sid": "29"
     */

    private String id;
    private String title;
    private String pic;
    private String url;
    private String sid;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
