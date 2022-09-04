package com.example.agsale.mvp.model.bean;

public class AreaTranslation {
    private Integer code;
    private String msg;
    private myArea data;

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

    public myArea getData() {
        return data;
    }

    public void setData(myArea data) {
        this.data = data;
    }

    public class myArea {
        String uid;
        String name;
        String phone;
        String provinces;
        String city;
        String county;
        String detail;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProvinces() {
            return provinces;
        }

        public void setProvinces(String province) {
            this.provinces = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }
}
