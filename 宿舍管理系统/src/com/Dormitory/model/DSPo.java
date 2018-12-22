package com.Dormitory.model;
/**
 * <p>描述 ：宿舍学生关系表Po</p>
 * @author 鲁念
 * @date  2018-12-22
 * @version 1.0
 */
public class DSPo {
    private String sno;			//寝室号
    private String rno;		//总床位
    private String time;      //空床位

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getRno() {
        return rno;
    }

    public void setRno(String rno) {
        this.rno = rno;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
