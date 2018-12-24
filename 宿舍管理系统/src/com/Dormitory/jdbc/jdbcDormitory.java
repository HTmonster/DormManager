package com.Dormitory.jdbc;
import java.lang.*;
import java.util.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.Dormitory.model.RoomPo;
import com.Dormitory.model.StudentPo;
import com.Dormitory.model.DSPo;

public class jdbcDormitory  extends jdbcDriver {
    //增加学生
    /**
     * <p>描述：添加学生</p>
     * @return 添加成功返回1，不成功返回0
     * @param rno
     */
    public int addStudent(String sno, String rno) {
        //获得当前的时间转化为字符串类型
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(date);
        String[] args1 = {sno,rno,time};
        String sql1 = "insert into DS(sno,rno,time) VALUES(?,?,?);";
        String[] args2 = {rno};
        String sql2 = "update dorm set emptyBedNum = emptyBedNum-1 where rno = and emptyBedNum>=0? ;";
        int flag=0;
        try {
            transBegin();
            int flag1 = jdbcExecuteUpdate(sql1,args1);
            int flag2 = jdbcExecuteUpdate(sql2,args2);
            if((flag1&flag2)>0){
                flag=1;
                transCommit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            flag= 0;
        } finally {
            this.closeResource();
            return flag;
        }

    }

    /**
     * <p>描述：删除学生</p>
     * @return 删除成功返回1，不成功返回0
     * @param rno
     */
    public int deleteStudent(String sno,String rno) {
//        String[] args1 = {String.valueOf(sno)};
        String[] args1 = {sno};
        String sql1 = "delete from DS where sno=?";
//        String[] args2 = {String.valueOf(rno)};
        String[] args2 = {rno};
        String sql2 = "update dorm set emptyBedNum = emptyBedNum+1 where rno = ? and emptyBedNum<bedNum;";
        int flag=0;
        try {
            transBegin();
            int flag1 = jdbcExecuteUpdate(sql1,args1);
            int flag2 = jdbcExecuteUpdate(sql2,args2);
            if((flag1&flag2)>0){
                flag=1;
                transCommit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            flag=0;
        }finally{
            this.closeResource();
            return flag;
        }
    }

    /**
     * <p>描述：通过寝室号查询宿舍的基本信息</p>
     * @return RoomPo
     * @param rno
     */
    public RoomPo getRoomPoByRno(String rno){
        String sql = "select * from dorm where rno = ?;";
//        String[] args = {String.valueOf(rno)};
        String[] args = {rno};

        ResultSet rs = null;
        RoomPo roomPo = null;
        try {
            rs = jdbcExecuteQuery(sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            this.jdbcConnectionClose();
            return null;
        }
        try {

            if(rs.next()){
                roomPo = this.makeRoomPo(rs);
                return roomPo;
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

    /**
     * <p>描述：通过宿舍号获得DS表</p>
     * @return DSPo
     * @param rno
     */
    public List<DSPo> getDSPoListByRno(String rno){
        String sql = "select * from DS where rno = ?;";
//        String[] args = {String.valueOf(rno)};
        String[] args = {rno};

        ResultSet rs = null;
        List<DSPo> resultList = null;
        try {
            rs = this.jdbcExecuteQuery(sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            this.jdbcConnectionClose();
        }
        resultList = new ArrayList<DSPo>();
        DSPo po =null;
        try {
            while(rs.next()){
                po = this.makeDSPo(rs);
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
     * <p>描述：通过学号获得DS表</p>
     * @return DSPo
     * @param sno
     */
    public DSPo getDSPoBySno(String sno){
        String sql = "select * from DS where sno = ?;";
//        String[] args = {String.valueOf(rno)};
        String[] args = {sno};

        ResultSet rs = null;
        DSPo po= null;
        try {
            rs = jdbcExecuteQuery(sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            this.jdbcConnectionClose();
            return null;
        }
        try {
            if(rs.next()){
                po = this.makeDSPo(rs);
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.jdbcConnectionClose();
        }
        return po;
    }


    /**
     * <p>描述：通过学号获得学生信息</p>
     * @return StudentPo
     * @param sno
     */
    public StudentPo getStudentPoBySno(String sno){
        String sql = "select * from student where sno = ?;";
        String[] args = {sno};

        ResultSet rs = null;
        StudentPo po= null;
        try {
            rs = jdbcExecuteQuery(sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            this.jdbcConnectionClose();
            return null;
        }
        try {
            if(rs.next()){
                po = this.makeStudentPo(rs);
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.jdbcConnectionClose();
        }
        return po;
    }

    /**
     * <p>描述：获取有空床位的寝室信息</p>
     * @return RoomPo的list
     * @param
     */
    public List<RoomPo> getEmptyRooms(){
        String sql = "select * from dorm where emptyBedNum >0";
        ResultSet rs = null;
        List<RoomPo> resultList = null;
        try {
            rs = this.jdbcExecuteQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            this.jdbcConnectionClose();
        }
        resultList = new ArrayList<RoomPo>();
        RoomPo po =null;
        try {
            while(rs.next()){
                po = this.makeRoomPo(rs);
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
     * <p>描述：按照楼层查找宿舍号</p>
     * @return 宿舍号的list
     * @param floor
     */
    public List<String> getRnoByFloor(String floor){
        String sql = "select rno from dorm where floor = ? order by rno desc ;";
        String[] args = {floor};
        ResultSet rs = null;
        RoomPo po = null;
        List resultList = new ArrayList<String>();
        try {
            rs = jdbcExecuteQuery(sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            this.jdbcConnectionClose();
            return null;
        }
        try {
            while(rs.next()){
                resultList.add(rs.getString("rno"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.jdbcConnectionClose();
        }
        return resultList;
    }


    /**
     * <p>描述：通过寝室号查询宿舍的住宿的学生的基本信息</p>
     * @return StudentPo的list
     * @param rno
     */
    public List<StudentPo> getStudentPoByRno(String rno){
 //       String[] args = {String.valueOf(rno)};
        String[] args = {rno};

        String sql = "select *" +
                "from student " +
                "where sno in (select sno from DS where rno = ?)";
        ResultSet rs = null;
        List<StudentPo> resultList = null;
        try {
            rs = this.jdbcExecuteQuery(sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            this.jdbcConnectionClose();
            return resultList;
        }
        resultList = new ArrayList<StudentPo>();
        StudentPo po =null;
        try {
            while(rs.next()){
               po = new StudentPo();
               po = this.makeStudentPo(rs);
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

    protected RoomPo makeRoomPo(ResultSet rs){
        if (null == rs)
            return null;
        RoomPo po = new RoomPo();
        try {
            po.setRno(rs.getString("rno"));
        } catch (SQLException e) {
            System.out.println("rno字段不存在");
        }
        try {
            po.setBedNum(rs.getString("bedNum"));
        } catch (SQLException e) {
            System.out.println("bedNum字段不存在");
        }
        try {
            po.setEmptyBedNum(rs.getString("emptyBedNum"));
        } catch (SQLException e) {
            System.out.println("emptyBedNum字段不存在");
        }
        try {
            po.setLeader(rs.getString("leader"));
        } catch (SQLException e) {
            System.out.println("leader字段不存在");
        }
        try {
            po.setPrice(rs.getString("price"));
        } catch (SQLException e) {
            System.out.println("price字段不存在");
        }
        return po;
    }

    protected StudentPo makeStudentPo(ResultSet rs){
        if (null == rs)
            return null;
        StudentPo po = new StudentPo();
        try {
            po.setSno(rs.getString("sno"));
        } catch (SQLException e) {
            System.out.println("sno字段不存在");
        }
        try {
            po.setSname(rs.getString("sname"));
        } catch (SQLException e) {
            System.out.println("sname字段不存在");
        }
        try {
            po.setGender(rs.getString("gender"));
        } catch (SQLException e) {
            System.out.println("gender字段不存在");
        }
        try {
            po.setAge(rs.getString("age"));
        } catch (SQLException e) {
            System.out.println("age字段不存在");
        }
        try {
            po.setPlace(rs.getString("place"));
        } catch (SQLException e) {
            System.out.println("place字段不存在");
        }
        try {
            po.setDept(rs.getString("dept"));
        } catch (SQLException e) {
            System.out.println("dept字段不存在");
        }
        return po;
    }

    protected DSPo makeDSPo(ResultSet rs){
        if (null == rs)
            return null;
        DSPo po = new DSPo();
        try {
            po.setSno(rs.getString("sno"));
        } catch (SQLException e) {
            System.out.println("sno字段不存在");
        }
        try {
            po.setRno(rs.getString("rno"));
        } catch (SQLException e) {
            System.out.println("rno字段不存在");
        }
        try {
            po.setTime(rs.getString("time"));
        } catch (SQLException e) {
            System.out.println("gender字段不存在");
        }
        return po;
    }
}
