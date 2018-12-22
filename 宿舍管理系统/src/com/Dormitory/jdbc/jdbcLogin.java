package com.Dormitory.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;


public class jdbcLogin extends jdbcDriver {
    public int queryForLogin(String userid,String password) {

        int permission = 0;  //权限，初始为0，无法登陆

        String[] args = {userid, password};
        String sql = "select * from admin where userid = ? and password = MD5(?) ;";

        ResultSet rs = null;
        try {
            rs = jdbcExecuteQuery(sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            this.jdbcConnectionClose();
            return 0;
        }
        try {

            if (rs.next()) {
                permission = 1;     //普通用户权限为1，可以对Dorm和DS表进行操作
                if (userid.equals("root")) {
                    permission = 2; //超级管理员权限为2，增加对admin表的管理操作
                }
            } else {
                System.out.println("用户名或密码错误！");
                permission = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            this.jdbcConnectionClose();
        }

        return permission;
    }

}
