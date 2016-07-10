package com.pu.gouthelper.bean;


import java.io.Serializable;

/**
 * Created by Requiem on 2016/3/31.
 * 帖子列表
 */
public class AttackRecord implements Serializable {

    private String id;
    private String uid;
    private String desc;
    private String tm;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }
}
