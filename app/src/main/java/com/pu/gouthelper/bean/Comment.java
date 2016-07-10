package com.pu.gouthelper.bean;

import java.util.List;

/**
 * Created by Requiem on 2016/5/2.
 */
public class Comment {


    /**
     * id : 108
     * uid : 4
     * content : 金大将军
     * memo : []
     * ups : 0
     * downs : 0
     * tm : 1464098842
     * user : {"id":"4","nickname":"小伙","avatar":""}
     * list : [{"id":"109","uid":"3","content":"bie ren de zhanghao","memo":[],"ups":"0","downs":"0","tm":"1464099705"},{"id":"110","uid":"3","content":"try again2","memo":{"tuser":{"id":"4","nickname":"小伙","avatar":""},"user":{"id":"3","nickname":"金正恩","avatar":""}},"ups":"0","downs":"0","tm":"1464101201"},{"id":"111","uid":"3","content":"try again3","memo":{"tuser":{"id":"4","nickname":"小伙","avatar":""},"user":{"id":"3","nickname":"金正恩","avatar":""}},"ups":"0","downs":"0","tm":"1464101459"},{"id":"120","uid":"5","content":"小伙挺帅啊","memo":{"tuser":{"id":"4","nickname":"小伙","avatar":""},"user":{"id":"5","nickname":"大浦","avatar":"http://www.tfzs999.com/attachment/2016/05/146285984742094.JPG"}},"ups":"0","downs":"0","tm":"1464232600"}]
     */

    private String id;
    private String uid;
    private String content;
    private String ups;
    private String downs;
    private String tm;
    /**
     * id : 4
     * nickname : 小伙
     * avatar :
     */

    private UserEntity user;
    private Memo memo;
    /**
     * id : 109
     * uid : 3
     * content : bie ren de zhanghao
     * memo : []
     * ups : 0
     * downs : 0
     * tm : 1464099705
     */

    private List<ListEntity> list;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUps() {
        return ups;
    }

    public void setUps(String ups) {
        this.ups = ups;
    }

    public String getDowns() {
        return downs;
    }

    public void setDowns(String downs) {
        this.downs = downs;
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



    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public Memo getMemo() {
        return memo;
    }

    public void setMemo(Memo memo) {
        this.memo = memo;
    }



    public static class ListEntity {
        private String id;
        private String uid;
        private String content;
        private String ups;
        private String downs;
        private String tm;
        private Memo memo;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUps() {
            return ups;
        }

        public void setUps(String ups) {
            this.ups = ups;
        }

        public String getDowns() {
            return downs;
        }

        public void setDowns(String downs) {
            this.downs = downs;
        }

        public String getTm() {
            return tm;
        }

        public void setTm(String tm) {
            this.tm = tm;
        }


        public Memo getMemo() {
            return memo;
        }

        public void setMemo(Memo memo) {
            this.memo = memo;
        }
    }
}
