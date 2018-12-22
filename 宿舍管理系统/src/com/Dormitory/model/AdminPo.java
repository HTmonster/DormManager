package com.Dormitory.model;

public class AdminPo {
    private  String userid;     //用户名
    private  String password;   //密码
    private  String username;   //管理员姓名
    private  String tele;       //联系方式

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public  String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getTele() {
        return tele;
    }
}


