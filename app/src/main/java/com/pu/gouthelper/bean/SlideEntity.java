package com.pu.gouthelper.bean;

/**
 * Created by Requiem on 2016/4/6.
 */
public class SlideEntity {

    /**
     * id : 2
     * title : test
     * pic : http://www.tfzs999.com/attachment/2016/02/145526585359822.jpg
     * url : http://www.baidu.com
     */

    private String id;
    private String title;
    private String pic;
    private String url;

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
}
