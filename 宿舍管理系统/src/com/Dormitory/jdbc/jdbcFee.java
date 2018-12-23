package com.Dormitory.jdbc;

import com.Dormitory.model.FeePo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class jdbcFee extends jdbcDriver {


    //查询所有寝室用电情况
    public List<FeePo> getFeePo(){

        String sql="select * from fee; ";

        ResultSet rs;
        List<FeePo> resultList = null;
        try {
            rs = jdbcExecuteQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            this.jdbcConnectionClose();
            return resultList;
        }
        resultList = new ArrayList<FeePo>();
        FeePo po =null;
        try {
            while(rs.next()){
                po = new FeePo();
                po = this.makeFeePo(rs);
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

    //查询所有未缴电费的寝室的用电状况
    public List<FeePo> getFeePo_NotPaid(){

        String paid="否";
        String sql="select * from fee where paid=?; ";
        String[] args={paid};

        ResultSet rs;
        List<FeePo> resultList = null;
        try {
            rs = jdbcExecuteQuery(sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            this.jdbcConnectionClose();
            return resultList;
        }
        resultList = new ArrayList<FeePo>();
        FeePo po =null;
        try {
            while(rs.next()){
                po = new FeePo();
                po = this.makeFeePo(rs);
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

    //修改缴费栏
    public int updateFee(String rno,String paid){

        String[] args={paid,rno};//paid∈{"是","否"}
        String sql="update fee set paid=? where rno=?;";

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




    protected FeePo makeFeePo(ResultSet rs){
        if (null == rs)
            return null;
        FeePo po = new FeePo();
        try {
            po.setRno(rs.getString("rno"));
        } catch (SQLException e) {
            System.out.println("rno字段不存在");
        }
        try {
            po.setWater(rs.getString("water"));
        } catch (SQLException e) {
            System.out.println("water字段不存在");
        }
        try {
            po.setWbill(rs.getString("wbill"));
        } catch (SQLException e) {
            System.out.println("wbill字段不存在");
        }
        try {
            po.setElec(rs.getString("elec"));
        } catch (SQLException e) {
            System.out.println("elec字段不存在");
        }
        try {
            po.setEbill(rs.getString("ebill"));
        } catch (SQLException e) {
            System.out.println("ebill字段不存在");
        }
        try {
            po.setTotal(rs.getString("total"));
        } catch (SQLException e) {
            System.out.println("total字段不存在");
        }
        try {
            po.setPaid(rs.getString("paid"));
        } catch (SQLException e) {
            System.out.println("paid字段不存在");
        }

        return po;
    }
}
