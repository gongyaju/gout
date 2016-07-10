package com.pu.gouthelper.bean;

/**
 * Created by Requiem on 2016/4/3.
 */
public class UserEntity {
    private String id;
    private String nickname;
    private String avatar;
    private String cid;//评论id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
