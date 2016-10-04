package com.pu.gouthelper.bean;

import java.util.List;

/**
 * Created by Requiem on 2016/4/17.
 */
public class LoginResult {

    /**
     * id : 3
     * mobile : 18710278553
     * nickname :
     * avatar :
     * age : 0
     * sex : 0
     * birthday : 0
     * history : 0
     * height : 0
     * weight : 0
     * tarea :
     * drug :
     * state : 1
     * tm : 1458391324
     */

    private String id;
    private String mobile;
    private String nickname;
    private String avatar;
    private String age;
    private String sex;
    private String birthday;
    private String history;
    private String height;
    private String weight;
    private String tarea;
    private List<Drug> drug;
    private String state;
    private String tm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }



    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public List<Drug> getDrug() {
        return drug;
    }

    public void setDrug(List<Drug> drug) {
        this.drug = drug;
    }
}
