package com.example.agsale.mvp.model.bean;

import java.util.List;

public class ShopTranslation {
    private Integer code;
    private String msg;
    private List<Package> data;

    public ShopTranslation(Integer code, String msg, List<Package> data, Long timestamp) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.timestamp = timestamp;
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

    public List<Package> getData() {
        return data;
    }

    public void setData(List<Package> data) {
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
