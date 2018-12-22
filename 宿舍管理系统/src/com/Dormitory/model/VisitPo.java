/**
 * @ClassName: VisitPo
 * @Description: (用一句话描述该文件做什么)
 * @author Theo_hui
 * @Email theo_hui@163.com
 * @Date 2018/12/22 20:23
 */
package com.Dormitory.model;

public class VisitPo {
    //    private  int    id;     //记录序号,仅仅作为数据库主键，实际不使用
    private  String rno;    //寝室号
    private  String vname;  //来访者姓名
    private  String cred;   //证件类型
    private  String cno;    //证件号
    private  String date;   //来访日期
    private  String remark; //备注

    public String getRno() {
        return rno;
    }

    public void setRno(String rno) {
        this.rno = rno;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getCred() {
        return cred;
    }

    public void setCred(String cred) {
        this.cred = cred;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
}
