package com.pu.gouthelper.bean;

/**
 * Created by Requiem on 2016/8/31.
 */
public class RewardEntity {

    /**
     * id : 3
     * uid : 1
     * money : 1.00
     * tm : 1400000000
     * user : {"id":"1","nickname":"aaabbb","avatar":"http://images.tfzs999.com/attachment/2016/02/145563314816080.JPG"}
     */

    private String id;
    private String uid;
    private String money;
    private String tm;
    private UserEntity user;

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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
