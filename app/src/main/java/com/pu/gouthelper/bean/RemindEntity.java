package com.pu.gouthelper.bean;

import java.util.List;

/**
 * Created by Requiem on 2016/4/24.
 */
public class RemindEntity {
    /**
     * clockList = {ArrayList@4753}  size = 2
     * 0 = {RemindEntity@4763}
     * 1 = {RemindEntity@4764}
     * color = "-16777216"
     * days = "21"
     * dosage = "1.00"
     * dtm = "["16:00","17:00","2:00","无"]"
     * id = "68"
     * title = "安慕希"
     * tm = "1472543301"
     * uid = "3"
     * shadow$_klass_ = {Class@4646} "class com.pu.gouthelper.bean.RemindEntity"
     * shadow$_monitor_ = -2108383282
     */
    private String id;//": "1",
    private String uid;//": "1",
    private String title;//": "\u5403\u836f",  //标题
    private String dosage;//": "0.00",  //剂量
    private String dtm;//
    private String days;//": "0",  //天数
    private String tm;//": "0"
    private String color;

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

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }


    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDtm() {
        return dtm;
    }

    public void setDtm(String dtm) {
        this.dtm = dtm;
    }
}
