package com.pu.gouthelper.bean;

/**
 * Created by Requiem on 2016/3/27.
 */
public class GoutMessage {

    /**
     * id : 2
     * uid : 1
     * tuid : 1
     * type : 2
     * title : 回复了你的消息
     * content : null
     * tm : 1400000000
     * user : {"id":"1","nickname":"aaabbb","avatar":"/attachment/2016/02/145563314816080.JPG"}
     */

    private String id;
    private String uid;
    private String tuid;
    private String type;
    private String title;
    private MessageContent content;
    private String tm;
    /**
     * id : 1
     * nickname : aaabbb
     * avatar : /attachment/2016/02/145563314816080.JPG
     */

    private UserEntity user;

    public void setId(String id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setTuid(String tuid) {
        this.tuid = tuid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public void setTm(String tm) {
        this.tm = tm;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getTuid() {
        return tuid;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }


    public String getTm() {
        return tm;
    }

    public UserEntity getUser() {
        return user;
    }

    public MessageContent getContent() {
        return content;
    }

    public void setContent(MessageContent content) {
        this.content = content;
    }
}
