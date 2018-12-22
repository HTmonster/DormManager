package com.Dormitory.jdbc;

import com.Dormitory.model.AdminPo;
import java.lang.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class jdbcAdmin extends jdbcDriver {
    //增加用户
    public int addAdmin(String userid,String password,String username,String tele){

        String[] args = {userid, password, username, tele};
        String sql = "insert into admin (userid,password,username,tele) values(?,MD5(?),?,?);";

        int flag=0;

        try {
            flag = jdbcExecuteUpdate(sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.jdbcConnectionClose();
        }


        return flag;
    }


    //删除用户
    public int deleteAdmin(String userid){

        String[] args={userid};
        String sql="delete from admin where userid=?;";

        int flag=0;

        try {
            flag = jdbcExecuteUpdate(sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.jdbcConnectionClose();
        }


        return flag;

    }


    //修改用户信息
    public int updateAdmin(String userid,String username,String tele){

        String[] args={username,tele,userid};
        String sql="update admin set username=?,tele=? where userid=?;";

        int flag=0;

        try {
            flag = jdbcExecuteUpdate(sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.jdbcConnectionClose();
        }


        return flag;
    }

    //修改用户密码
    public int updateAdmin(String userid,String password){

        String[] args={password,userid};
        String sql="update admin set password=MD5(?) where userid=?;";

        int flag=0;

        try {
            flag = jdbcExecuteUpdate(sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.jdbcConnectionClose();
        }


        return flag;
    }

    //查询用户信息
    public AdminPo getAdminPoByUserid(String userid){

        String[] args={userid};
        String sql="select * from admin where userid=?; ";

        ResultSet rs = null;
        AdminPo adminPo = null;
        try {
            rs = jdbcExecuteQuery(sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            this.jdbcConnectionClose();
            return null;
        }
        try {

            if(rs.next()){
                adminPo = this.makeAdminPo(rs);
                return adminPo;
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.jdbcConnectionClose();
        }
        return null;
    }

    //查询所有
    public List<AdminPo> getAdminPo(){

        String sql="select * from admin; ";

        ResultSet rs;
        List<AdminPo> resultList = null;
        try {
             rs = jdbcExecuteQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            this.jdbcConnectionClose();
            return resultList;
        }
        resultList = new ArrayList<AdminPo>();
        AdminPo po =null;
        try {
            while(rs.next()){
                po = new AdminPo();
                po = this.makeAdminPo(rs);
                resultList.add(po);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            this.jdbcConnectionClose();
        }
        return resultList;
    }



    protected AdminPo makeAdminPo(ResultSet rs){
        if (null == rs)
            return null;
        AdminPo po = new AdminPo();
        try {
            po.setUserid(rs.getString("userid"));
        } catch (SQLException e) {
            System.out.println("userid字段不存在");
        }
        try {
            po.setPassword(rs.getString("password"));
        } catch (SQLException e) {
            System.out.println("password字段不存在");
        }
        try {
            po.setUsername(rs.getString("username"));
        } catch (SQLException e) {
            System.out.println("username字段不存在");
        }
        try {
            po.setTele(rs.getString("tele"));
        } catch (SQLException e) {
            System.out.println("tele字段不存在");
        }

        return po;
    }
}
