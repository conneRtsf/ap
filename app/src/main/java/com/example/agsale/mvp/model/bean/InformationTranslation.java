package com.example.agsale.mvp.model.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

public class InformationTranslation {
    private Integer code;
    private String msg;
    private MyData data;
    @NoArgsConstructor
    @Data
    public static class MyData {
        String uid;
        String username;
        String daddress;
        String identity;
        String viptime;

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

        public String getDaddress() {
            return daddress;
        }

        public void setDaddress(String daddress) {
            this.daddress = daddress;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public String getViptime() {
            return viptime;
        }

        public void setViptime(String viptime) {
            this.viptime = viptime;
        }


    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MyData getData() {
        return data;
    }

    public void setData(MyData data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    private Long timestamp;


}
