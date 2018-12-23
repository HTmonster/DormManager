package com.Dormitory.model;

public class FeePo {
    private String rno;     //寝室号
    private String Water;   //用水量
    private String wbill;   //水费
    private String elec;    //用电量
    private String ebill;   //电费
    private String total;   //总费用
    private String paid;    //是否缴费

    public String getRno() {
        return rno;
    }

    public void setRno(String rno) {
        this.rno = rno;
    }

    public String getWater() {
        return Water;
    }

    public void setWater(String water) {
        Water = water;
    }

    public String getWbill() {
        return wbill;
    }

    public void setWbill(String wbill) {
        this.wbill = wbill;
    }

    public String getElec() {
        return elec;
    }

    public void setElec(String elec) {
        this.elec = elec;
    }

    public String getEbill() {
        return ebill;
    }


    public void setEbill(String ebill) {
        this.ebill = ebill;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }
}
