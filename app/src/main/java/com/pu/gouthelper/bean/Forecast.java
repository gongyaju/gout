package com.pu.gouthelper.bean;

/**
 * Created by Requiem on 2016/4/28.
 */
public class Forecast {
    private int state;//": 1,  //状态
    private int security;//": 0,  //安全次数
    private int limited;//": 0,  //限制次数
    private int risk;//": 0,  //高危次数
    private String msg;//": ""  //提示语句

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSecurity() {
        return security;
    }

    public void setSecurity(int security) {
        this.security = security;
    }

    public int getLimited() {
        return limited;
    }

    public void setLimited(int limited) {
        this.limited = limited;
    }

    public int getRisk() {
        return risk;
    }

    public void setRisk(int risk) {
        this.risk = risk;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
