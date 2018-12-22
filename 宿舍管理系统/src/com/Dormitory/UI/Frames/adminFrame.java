
package com.Dormitory.UI.Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;


import com.Dormitory.model.AdminPo;
import com.Dormitory.jdbc.jdbcLogin;
import com.Dormitory.jdbc.jdbcAdmin;

/**
 * @ClassName: adminFrame
 * @Description: 超级管理员管理界面
 * @author Theo_hui
 * @Email theo_hui@163.com
 * @Date 2018/12/20 15:54
 */


public class adminFrame extends JFrame {

    private final String[] TITLES =new String[]{"id","名字","电话"};
    private Vector<Vector<String>> dataModel = new Vector<Vector<String>>();

    //数据库查询及结果存储数组
    public jdbcLogin login = new jdbcLogin();
    public jdbcAdmin admin = new jdbcAdmin();
    List<AdminPo> users =  new ArrayList<AdminPo>();

    //标签库路径
    private final String ICONS_PATH=System.getProperty("user.dir")+"/icons/";

    public adminFrame(){
        init();
        this.setLayout(new BorderLayout());

        //选择操作面板
        JPanel slcPanel = new JPanel();
        slcPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //slcPanel.setBackground(Color.yellow);
        slcPanel.setPreferredSize(new Dimension(150,200));
        this.add(slcPanel,BorderLayout.WEST);

        //选择操作面板————按钮
        JButton backBtn = new JButton("返回登录");
        JButton flushBtn = new JButton("刷新表格");
        JButton addBtn = new JButton("增新管理员");
        JButton quitBtn = new JButton("退出系统");
        backBtn.setIcon(new ImageIcon(ICONS_PATH+"Name_Tag_32.png"));
        flushBtn.setIcon(new ImageIcon(ICONS_PATH+"Rotate_32.png"));
        addBtn.setIcon(new ImageIcon(ICONS_PATH+"Add_User_32.png"));
        quitBtn.setIcon(new ImageIcon(ICONS_PATH+"Exit_32.png"));
        slcPanel.add(backBtn);slcPanel.add(flushBtn);slcPanel.add(addBtn);slcPanel.add(quitBtn);


        //信息表面板
        JPanel tblPanel = new JPanel();
        tblPanel.setBackground(Color.gray);
        this.add(tblPanel,BorderLayout.CENTER);

        //信息表面板————表格
        Vector<String> titles = new Vector<>(Arrays.asList(TITLES));
        JTable table = new JTable(dataModel, titles){public boolean isCellEditable(int row, int column) { return false; }};//创建table并设置不可写
        JScrollPane scrollPane = new JScrollPane(table);//把table装进可滚动容器内
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tblPanel.add(scrollPane);
        flush_admin2Vector(table);//首次刷新

        //操作面板
        JPanel opPanel = new JPanel();
        opPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        opPanel.setPreferredSize(new Dimension(130,100));
        //opPanel.setBackground(Color.darkGray);
        this.add(opPanel,BorderLayout.SOUTH);

        //操作面板————标签提示栏
        JLabel lb1 =new JLabel("ID");
        JLabel lb2 =new JLabel("用户名");
        JLabel lb3 =new JLabel("电话");
        JLabel lb4 =new JLabel("密码");
        JLabel lb5 =new JLabel("确认密码");

        //操作面板————拆分信息栏
        JTextField txt1 = new JTextField(25);
        JTextField txt2 = new JTextField(25);
        JTextField txt3 = new JTextField(25);
        txt1.setEditable(false);

        opPanel.add(lb1);opPanel.add(txt1);
        opPanel.add(lb2);opPanel.add(txt2);
        opPanel.add(lb3);opPanel.add(txt3);

        JPasswordField pwd=new JPasswordField(35);
        JPasswordField pwdConfirm=new JPasswordField(35);

        opPanel.add(lb4); opPanel.add(pwd);
        opPanel.add(lb5); opPanel.add(pwdConfirm);

        //操作面板————操作按钮
        JButton delBtn = new JButton("删除记录");
        JButton fixBtn = new JButton("修改信息");
        JButton fixPwdBtn = new JButton("修改密码");


        opPanel.add(delBtn);opPanel.add(fixBtn);opPanel.add(fixPwdBtn);

        //返回登录按钮
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Back2Login();
            }
        });

        //刷新按钮
        flushBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flush_admin2Vector(table);
            }
        });

        //增加成员按钮事件
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAdmin();
                flush_admin2Vector(table);
            }
        });

        //退出按钮
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitSystem();
            }
        });

        //设置table的鼠标事件
        table.addMouseListener(new MouseAdapter() {    //鼠标事件
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow(); //获得选中行索引
                txt1.setText(table.getValueAt(selectedRow,0).toString());
                txt2.setText(table.getValueAt(selectedRow,1).toString());
                txt3.setText(table.getValueAt(selectedRow,2).toString());
            }
        });

        //删除按钮事件
        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delAdminPo(txt1.getText(),table);
            }
        });

        //修改信息按钮事件
        fixBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fixAdminPo(txt1.getText(),txt2.getText(),txt3.getText());
                flush_admin2Vector(table);
            }
        });

        //修改密码按钮事件
        fixPwdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fixPwdAdminPo(txt1.getText(),pwd,pwdConfirm);
                flush_admin2Vector(table);
            }
        });

    }

    public void init(){
        //默认工具集 并获得屏幕大小
        Toolkit kit =Toolkit.getDefaultToolkit();
        Dimension screenSize =kit.getScreenSize();

        //设置大小和位置
        this.setSize(880,700);
        this.setLocation((int)(screenSize.getWidth()/2-440),(int)(screenSize.getHeight()/2-350));

        //设置图标
        Image icon=new ImageIcon(System.getProperty("user.dir")+"/icons/"+"Tent_32.png").getImage();
        this.setIconImage(icon);

        //设置风格
        try{
            String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
            //String lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            //String lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            UIManager.setLookAndFeel(lookAndFeel);
        }catch (Exception e){
            e.printStackTrace();
        }

        //设置关闭后主线继续运行
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //设置标题和布局
        this.setTitle("宿舍管理系统——超级管理员");
        this.setVisible(true);
    }

    //数据结构转换并更新数据
    public void flush_admin2Vector(JTable table) {
        //更新设置了的信息
        users = null;

        //数据库查询方法
        users=admin.getAdminPo();
        System.out.println(users);


        //转换并更新table显示的数据
        dataModel.removeAllElements();

        //转换值
        Iterator<AdminPo> iter = users.iterator();
        while(iter.hasNext()){
            AdminPo ad = iter.next();
            if (!ad.getUserid().equals("root")){
                System.out.println(ad.getUserid()+ad.getUsername()+ad.getTele());
                dataModel.add(new Vector<String>(Arrays.asList(ad.getUserid(),ad.getUsername(),ad.getTele())));
            }
        }

        //表格刷新
        table.validate();
        table.updateUI();
    }


    //返回登录界面
    public void Back2Login(){
        this.dispose();
        loginFrame login = new loginFrame();

    }

    //增加新成员
    public void addAdmin(){
        String name, tele="",pwd,pwdCon;

        //交互输入框获得姓名 不能为空
        name=JOptionPane.showInputDialog(null,"请输入名字");
        while (name.isEmpty()){
            JOptionPane.showMessageDialog(null,"姓名不能为空");
            name=JOptionPane.showInputDialog(null,"请输入名字");
        }

        //交互输入框获得电话 可以为空
        tele=JOptionPane.showInputDialog(null,"请输入电话");

        //交互输入框获得密码 前后一致 不能为空
        pwd=JOptionPane.showInputDialog(null,"请输入密码");
        pwdCon=JOptionPane.showInputDialog(null,"请确认密码");
        while (!pwd.equals(pwdCon)||pwd.equals("")){
            JOptionPane.showMessageDialog(null,"前后密码不一致或密码为空，请重新输入");
            pwd=JOptionPane.showInputDialog(null,"请输入密码");
            pwdCon=JOptionPane.showInputDialog(null,"请确认密码");
        }

        System.out.println("增加成员"+name+tele+pwd);

        //名字的hash值作为id
        int flag=admin.addAdmin(String.valueOf(name.hashCode()),pwd,name,tele);
        if(flag==0){
            JOptionPane.showMessageDialog(null,"抱歉，添加失败");
        }else{
            JOptionPane.showMessageDialog(null,"添加管理员 "+name+" 成功");
        }
    }

    //退出系统
    public void quitSystem(){
        System.exit(0);

    }

    //删除一条记录
    public void delAdminPo(String id,JTable table){
        if(id==null){
            JOptionPane.showMessageDialog(null,"错误 不能为空");
        }
        else{
            int value=JOptionPane.showConfirmDialog(null,"是否确认删除？");
            if(value==JOptionPane.YES_OPTION){
                int flag=admin.deleteAdmin(id);
                if(flag==0){
                    JOptionPane.showMessageDialog(null,"抱歉 删除失败");
                }else{
                    JOptionPane.showMessageDialog(null,"成功删除"+id);
                    flush_admin2Vector(table);
                }
            }
        }
    }

    //修改信息
    public void fixAdminPo(String id,String name,String tel){

        //确认是否删除
        int value = JOptionPane.showConfirmDialog(null,"确认修改此条记录信息为"+id+" "+name+" "+tel+"?");

        //确认
        if(value==JOptionPane.YES_OPTION){
            int flag=admin.updateAdmin(id,name,tel);
            if(flag==0){
                JOptionPane.showMessageDialog(null,"抱歉，修改失败");
            }else{
                JOptionPane.showMessageDialog(null,"修改成功");
            }
        }
    }

    //修改密码
    public void fixPwdAdminPo(String id,JPasswordField pwd, JPasswordField pwdConfirm){
        String password=pwd.getText();
        String passwordConfirm=pwdConfirm.getText();

        System.out.println(password+" "+passwordConfirm);

        if(id.isEmpty()){
            JOptionPane.showMessageDialog(null,"错误 未选中对象");
            pwd.setText("");
            pwdConfirm.setText("");
            return;
        }
        if(password.equals("")||passwordConfirm.equals("")){
            JOptionPane.showMessageDialog(null,"错误 不能为空");
            return;
        }

        if (!password.equals(passwordConfirm)){
            JOptionPane.showMessageDialog(null,"错误，前后密码不一致");
            pwdConfirm.setText("");//清除确认密码栏文本
            return;
        }else{

            int value = JOptionPane.showConfirmDialog(null,"确认修改密码？");
            if(value==JOptionPane.YES_OPTION){
                //更新密码
                int flag=admin.updateAdmin(id,password);
                if(flag==0){
                    JOptionPane.showMessageDialog(null,"抱歉 修改失败");
                }else{
                    JOptionPane.showMessageDialog(null,"修改成功！");
                    pwd.setText("");
                    pwdConfirm.setText("");
                }
            }
        }

    }

//    public static void main(String[] args) {
//        //运行主体框架
//        EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                adminFrame t = new adminFrame();
//            }
//        });
//
//    }
}