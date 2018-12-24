
package com.Dormitory.UI.Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.Dormitory.jdbc.jdbcLogin;
/**
 * @ClassName: loginFrame
 * @Description: 登录界面  普通登录进入主界面
 *                        超级管理员登录进入管理界面
 * @author Theo_hui
 * @Email theo_hui@163.com
 * @Date 2018/12/20 21:16
 */


public class loginFrame extends JFrame implements ActionListener {

    //图标库路径
    private final String ICONS_PATH=System.getProperty("user.dir")+"/icons/";

    //定义登录界面的组件
    JButton btn1,btn2=null;
    JPanel jp1,jp2,jp3=null;
    JTextField username=null;
    JLabel jlb1,jlb2=null;
    JPasswordField password=null;


    //用户表操作
    jdbcLogin login = new jdbcLogin();

    public loginFrame()
    {
        //初始化
        init();

        //设置布局
        this.setLayout(new GridLayout(3,1));

        //面板
        jp1 =new JPanel();//用户名面板
        jp2 =new JPanel();//密码面板
        jp3 =new JPanel();//按钮面板
        jp1.setLayout(new FlowLayout(FlowLayout.CENTER));
        jp2.setLayout(new FlowLayout(FlowLayout.CENTER));
        jp3.setLayout(new FlowLayout(FlowLayout.CENTER));

        //标签
        jlb1 = new JLabel("用户名:");
        jlb2 = new JLabel("密  码:");


        //输入框
        username = new JTextField(15);
        password = new JPasswordField(15);

        //按钮
        btn1 = new JButton("登录");
        btn2 = new JButton("超级用户管理");
        btn2.setIcon(new ImageIcon(ICONS_PATH+"Superman_16.png"));
        btn1.addActionListener(this);
        btn2.addActionListener(this);


        jp1.add(jlb1); jp1.add(username);
        jp2.add(jlb2); jp2.add(password);
        jp3.add(btn1); jp3.add(btn2);

        this.add(jp1); this.add(jp2); this.add(jp3);

    }
    public void init(){
        //默认工具集 并获得屏幕大小
        Toolkit kit =Toolkit.getDefaultToolkit();
        Dimension screenSize =kit.getScreenSize();

        //设置大小 和 位置
        this.setSize(300,200);
        this.setLocation((int)(screenSize.getWidth()/2)-200,(int)(screenSize.getHeight()/2)-120);

        //设置图标
        Image icon=new ImageIcon(ICONS_PATH+"Tent_32.png").getImage();
        setIconImage(icon);

        //设置标题 及退出选项
        this.setTitle("欢迎登录宿舍管理系统");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //设置风格
        try{
            String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
            //String lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            //String lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            UIManager.setLookAndFeel(lookAndFeel);
        }catch (Exception e){
            e.printStackTrace();
        }

        //设置背景颜色
        this.setBackground(Color.white);

        //设置可见 且 不可改变大小
        this.setVisible(true);
        this.setResizable(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //监听各个按钮
        if(e.getActionCommand()=="登录")
        {
            this.normal_Login();
        }else if(e.getActionCommand()=="超级用户管理")
        {
            this.super_Login();
        }
    }

    //普通用户登录方法
    public void normal_Login() {
        String user=username.getText();
        char[] pwd=password.getPassword();
        String pwdstr=String.valueOf(pwd);

        //空字符串
        if(user.isEmpty()||pwdstr.isEmpty()){
            JOptionPane.showMessageDialog(null, "错误 不能为空");
            return;
        }

        //查询信息表
        int permission;
        permission=login.queryForLogin(user,pwdstr);

        //通过验证
        if(permission==0){
            JOptionPane.showMessageDialog(null, "用户/密码不匹配");
        }else{
            System.out.println("登录成功");
            System.out.println(user);
            System.out.println(pwdstr);

            this.dispose();
            mainFrame main = new mainFrame(user);
        }
    }

    //超级用户登录方法
    public void super_Login(){
        String user=username.getText();
        char[] pwd=password.getPassword();
        String pwdstr=String.valueOf(pwd);

        //空字符串
        if(user.isEmpty()||pwdstr.isEmpty()){
            JOptionPane.showMessageDialog(null, "错误 不能为空");
            return;
        }

        //查询信息表
        int permission;
        permission=login.queryForLogin(user,pwdstr);

        //通过验证
        if(permission==0){
            JOptionPane.showMessageDialog(null, "用户/密码不匹配");
        }else if(permission==1){
            JOptionPane.showMessageDialog(null, "没有权限");
        }
        else if(permission==2){
            System.out.println("登录成功");
            System.out.println(user);
            System.out.println(pwdstr);
            this.dispose();
            adminFrame admin = new adminFrame();
        }
    }
}
