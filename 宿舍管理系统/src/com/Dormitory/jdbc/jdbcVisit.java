/**
 * @ClassName: jdbcVisit
 * @Description: (用一句话描述该文件做什么)
 * @author Theo_hui
 * @Email theo_hui@163.com
 * @Date 2018/12/22 20:21
 */


package com.Dormitory.jdbc;

import com.Dormitory.model.VisitPo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class jdbcVisit extends jdbcDriver{
    //增加来访记录
    public int addVist(String rno,String vname,String cred,String cno,String remark){

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(date);

        String[] args = {rno,vname,cred,cno,time,remark};

        String sql = "insert into visit (rno,vname,cred,cno,date,remark) values(?,?,?,?,?,?);";

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

    //查询来访记录
    public List<VisitPo> getVisitPoByrno(String rno){

        String[] args={rno};
        String sql="select rno,vname,cred,cno,date,remark from visit where rno=?; ";

        ResultSet rs = null;
        List<VisitPo> resultList = null;
        try {
            rs = this.jdbcExecuteQuery(sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            this.jdbcConnectionClose();
            return resultList;
        }
        resultList = new ArrayList<VisitPo>();
        VisitPo po =null;
        try {
            while(rs.next()){
                po = new VisitPo();
                po = this.makeVisitPo(rs);
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

    /**
     * 查找所有的访问记录
     *
     * @param
     *
     * @return 访问记录列表  List<VisitPo>
     */
    public List<VisitPo> getVisitPo(){

        String sql="select rno,vname,cred,cno,date,remark from visit; ";

        ResultSet rs = null;
        List<VisitPo> resultList = null;
        try {
            rs = this.jdbcExecuteQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            this.jdbcConnectionClose();
            return resultList;
        }
        resultList = new ArrayList<VisitPo>();
        VisitPo po =null;
        try {
            while(rs.next()){
                po = new VisitPo();
                po = this.makeVisitPo(rs);
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

    protected VisitPo makeVisitPo(ResultSet rs){
        if (null == rs)
            return null;
        VisitPo po = new VisitPo();
        try {
            po.setRno(rs.getString("rno"));
        } catch (SQLException e) {
            System.out.println("rno字段不存在");
        }
        try {
            po.setVname(rs.getString("vname"));
        } catch (SQLException e) {
            System.out.println("vname字段不存在");
        }
        try {
            po.setCred(rs.getString("cred"));
        } catch (SQLException e) {
            System.out.println("cred字段不存在");
        }
        try {
            po.setCno(rs.getString("cno"));
        } catch (SQLException e) {
            System.out.println("cno字段不存在");
        }
        try {
            po.setDate(rs.getString("date"));
        } catch (SQLException e) {
            System.out.println("date字段不存在");
        }
        try {
            po.setRemark(rs.getString("remark"));
        } catch (SQLException e) {
            System.out.println("remark字段不存在");
        }

        return po;
    }
}
