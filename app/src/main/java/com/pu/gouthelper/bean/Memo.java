package com.pu.gouthelper.bean;

/**
 * Created by Requiem on 2016/5/28.
 */
public class Memo {
    /**
     * id : 4
     * nickname : 小伙
     * avatar :
     */
    private UserEntity tuser;
    /**
     * id : 5
     * nickname : 大浦
     * avatar : http://www.tfzs999.com/attachment/2016/05/146285984742094.JPG
     */
    private UserEntity user;

    public UserEntity getTuser() {
        return tuser;
    }

    public void setTuser(UserEntity tuser) {
        this.tuser = tuser;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
