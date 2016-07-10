package com.pu.gouthelper.bean;

/**
 * Created by Requiem on 2016/4/11.
 */
public class MyInfo {
    private String nickname;//:'昵称'
    private String sex;      //  :'1'  // 1男  2女
    private String birthday; //             :'2015-10-10'  //生日
    private String history;  //              :'1'  //痛风历史  1 有过  2 无
    private String height;    //            :'180' //身高
    private String weight;    //          :'75'  //体重
    private String tarea;     //          :'1,2'  //疼痛区域  这个数字，你们自己商定，每个区域，用数字标识，客户端保持一致就行了,多个区域，用英文逗号分隔
    private String drug;        //       :'1,2'  //用药历史，这里传的是药品的ID， 多个药品ID，用英文逗号分隔

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }
}
