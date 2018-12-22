package com.Dormitory.jdbc;

import com.sun.corba.se.pept.transport.ConnectionCache;

import java.sql.*;

/**
 * @ClassName: jdbcDriver
 * @Description: jdbc驱动 连接数据库用 为其他操作提供基础
 * @author Theo_hui
 * @Email theo_hui@163.com
 * @Date 2018/12/13 14:58
 * @modifier 修改人 鲁念
 * @modifyDate 修改时间 2018/12/18
 * @modifyContent 修改内容 重载带参数的数据库操作
 */


public class jdbcDriver {

    // JDBC 驱动名
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    //数据库连接信息
    static  String DB_URL = "jdbc:mysql://39.108.15.217:3306/dorm?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    static  String USER = "HaHaDM";
    static  String PASS = "1234";

    //数据库连接中间数据
    public static Connection connection;
    private PreparedStatement prestmt = null;
    public static ResultSet resultset;
    public static Statement statement;
    /*构造函数*/
    public  jdbcDriver(){
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*构造函数*/
    public  jdbcDriver(String db_url,String user_name,String password){

        DB_URL=db_url;
        USER=user_name;
        PASS=password;

        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 数据库驱动连接
     *
     */
    public Connection jdbcConnection(){
        try {
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("连接数据库成功！");
            return connection;
        } catch (Exception e) {
            System.out.print("MySQL连接失败！");
            return null;
        }
    }

    /**
     * 数据库驱动关闭
     *
     */
    public void jdbcConnectionClose(){
        try {
            connection.close();
            System.out.println("数据库连接成功关闭");
        } catch (SQLException e) {
            System.out.println("数据库连接关闭失败");
        }
    }

    /**
     * 更新
     * executeUpdate
     *
     */

    //执行带有参数的sql语句
    public int jdbcExecuteUpdate(String sql, String[] args) throws SQLException {
        Connection conn=jdbcConnection();
        if (sql == null || args == null)
            throw new SQLException("SQL语句为空！");
        prestmt=conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            prestmt.setString(i + 1, args[i]);
        }
        int result = prestmt.executeUpdate();
        return result;
    }
    /**
     * 查找
     * executeQuery
     *
     */

    public ResultSet jdbcExecuteQuery(String sql, String[] args) throws SQLException {
        ResultSet rs = null;
        Connection conn=jdbcConnection();
        prestmt=conn.prepareStatement(sql);
        if (sql == null || args == null)
            throw new SQLException("SQL语句为空！");
        for (int i = 0; i < args.length; i++) {
            prestmt.setString(i + 1, args[i]);
        }
        try {
            rs = prestmt.executeQuery();
        } catch (SQLException e) {
            throw e;
        }
        return rs;
    }

    /**
     * 数据库驱动运行
     * executeUpdate
     *
     */
    public void jdbcExecuteUpdate(String s) throws SQLException{
        Connection conn=jdbcConnection();
        statement=connection.createStatement();
        statement.executeUpdate(s);
    }


    /**
     * 数据库驱动运行
     * executeQuery
     *
     */
    public ResultSet jdbcExecuteQuery(String s) throws SQLException{
        Connection conn=jdbcConnection();
        statement=connection.createStatement();
        resultset=statement.executeQuery(s);
        return resultset;
    }


}