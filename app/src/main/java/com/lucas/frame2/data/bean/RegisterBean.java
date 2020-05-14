package com.lucas.frame2.data.bean;

import com.lucas.frame.data.net.IBean;

import java.util.List;

public class RegisterBean extends IBean {

    /**
     * data : {"admin":false,"chapterTops":[],"collectIds":[],"email":"","icon":"","id":41218,"nickname":"18825204205","password":"","publicName":"18825204205","token":"","type":0,"username":"18825204205"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * admin : false
         * chapterTops : []
         * collectIds : []
         * email :
         * icon :
         * id : 41218
         * nickname : 18825204205
         * password :
         * publicName : 18825204205
         * token :
         * type : 0
         * username : 18825204205
         */

        public boolean admin;
        public String email;
        public String icon;
        public int id;
        public String nickname;
        public String password;
        public String publicName;
        public String token;
        public int type;
        public String username;
        public List<?> chapterTops;
        public List<?> collectIds;
    }
}
