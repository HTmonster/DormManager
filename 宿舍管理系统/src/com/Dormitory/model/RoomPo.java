package com.Dormitory.model;


public class RoomPo {
    private String rno;			//寝室号
    private String bedNum;		//总床位
    private String emptyBedNum;      //空床位
    private String leader;		//寝室长
    private String price;		//寝室价格

    public String getRno() {
        return rno;
    }

    public void setRno(String rno) {
        this.rno = rno;
    }

    public String getBedNum() {
        return bedNum;
    }


    public void setBedNum(String bedNum) {
        this.bedNum = bedNum;
    }

    public String getEmptyBedNum() {
        return emptyBedNum;
    }

    public void setEmptyBedNum(String emptyBedNum) {
        this.emptyBedNum = emptyBedNum;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
