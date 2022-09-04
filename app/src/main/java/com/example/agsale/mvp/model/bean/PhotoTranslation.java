package com.example.agsale.mvp.model.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

public class PhotoTranslation {
    private int code;
    private String msg;
    private MyData data;

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

    @NoArgsConstructor
    @Data
    public static class MyData {
        String uid;
        String personalphoto;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPersonalphoto() {
            return personalphoto;
        }

        public void setPersonalphoto(String personalphoto) {
            this.personalphoto = personalphoto;
        }
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }




}
