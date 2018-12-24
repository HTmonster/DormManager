package com.Dormitory.jdbc;

import com.Dormitory.model.RepairPo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class jdbcRepair extends jdbcDriver{
    //增加维修记录
    public int addRepairment(String rno,String repairment,String remark){

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(date);

        String[] args = {rno,repairment,time,remark};

        String sql = "insert into repair (rno,equipment,date,remark) values(?,?,?,?);";

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

    //查询宿舍的维修记录
    public List<RepairPo> getRepairPoByrno(String rno){

        String[] args={rno};
        String sql="select rno,equipment,date,remark from repair where rno=?; ";

        ResultSet rs = null;
        List<RepairPo> resultList = null;
        try {
            rs = this.jdbcExecuteQuery(sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            this.jdbcConnectionClose();
            return resultList;
        }
        resultList = new ArrayList<RepairPo>();
        RepairPo po =null;
        try {
            while(rs.next()){
                po = new RepairPo();
                po = this.makeRepairPo(rs);
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

    //查询所有的维修记录
    public List<RepairPo> getAllRepairPo(){

        String sql="select rno,equipment,date,remark from repair; ";

        ResultSet rs = null;
        List<RepairPo> resultList = null;
        try {
            rs = this.jdbcExecuteQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            this.jdbcConnectionClose();
            return resultList;
        }
        resultList = new ArrayList<RepairPo>();
        RepairPo po =null;
        try {
            while(rs.next()){
                po = new RepairPo();
                po = this.makeRepairPo(rs);
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

    protected RepairPo makeRepairPo(ResultSet rs){
        if (null == rs)
            return null;
        RepairPo po = new RepairPo();
        try {
            po.setRno(rs.getString("rno"));
        } catch (SQLException e) {
            System.out.println("rno字段不存在");
        }
        try {
            po.setEquipment(rs.getString("equipment"));
        } catch (SQLException e) {
            System.out.println("equipment字段不存在");
        }
        try {
            po.setDate(rs.getString("date"));
        } catch (SQLException e) {
            System.out.println("date字段不存在");
        }
        try {
            po.setRemark(rs.getString("remark"));
        } catch (SQLException e) {
            System.out.println("date字段不存在");
        }

        return po;
    }
}
