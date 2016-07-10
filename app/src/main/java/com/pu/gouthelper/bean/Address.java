package com.pu.gouthelper.bean;

/**
 * Created by gongyaju on 16/6/19.
 */
public class Address {
    private String id;//": "1",
    private String uid;//": "1",
    private String username;//": "aa",  //收货人
    private String phone;//": "13800138000",  //电话
    private String address;//": "",  //地址
    private String zipcode;//": " ",  //邮编
    private String tm;//": "0"

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }
}
