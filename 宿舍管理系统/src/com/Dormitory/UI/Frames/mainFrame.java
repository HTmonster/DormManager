/**
 * @ClassName: mainFrame
 * @Description: 客户端主界面 有多个panel 宿舍管理  住宿管理 访客管理 水电管理 维修设备
 * @author Theo_hui
 * @Email theo_hui@163.com
 * @Date 2018/12/22 19:11
 */


package com.Dormitory.UI.Frames;

//用到的所有panel
import com.Dormitory.UI.Frames.mainPanel.*;

import javax.swing.*;
import java.awt.*;

public class mainFrame extends JFrame {

    public mainFrame(String adminName){
        init(adminName);

        // 创建选项窗格
        JTabbedPane tabPane = new JTabbedPane();
        tabPane.setBackground(Color.WHITE);

        // 设置面板布局为网格布局
        this.setLayout(new GridLayout(1,1));
        tabPane.setTabPlacement(JTabbedPane.TOP);// 设定选项卡放在上部
        this.add(tabPane);// 将选项窗格放置在面板中

        // 创建一个dorm面板
        DormPanel dormPanel = new DormPanel();
        tabPane.addTab("宿舍信息", dormPanel);

        // 创建一个dorminfo面板
        DormInfoPanel dormInfoPanel = new DormInfoPanel();
        tabPane.addTab("住宿信息", dormInfoPanel);

        // 创建一个visit面板
        VisitPanel visitPanel=new VisitPanel();
        tabPane.addTab("来访信息", visitPanel);

        // 创建一个fee面板
        feePanel feePanel=new feePanel();
        tabPane.addTab("水电费", feePanel);

        // 创建一个fepair面板
        RepairPanel repairPanel = new RepairPanel();
        tabPane.addTab("设备维修",repairPanel);

        tabPane.setSelectedIndex(0);
    }

    public void init(String adminName){
        //默认工具集 并获得屏幕大小
        Toolkit kit =Toolkit.getDefaultToolkit();
        Dimension screenSize =kit.getScreenSize();

        //设置大小和位置
        this.setSize(1200,760);
        this.setMinimumSize(new Dimension(1200,760));
        this.setLocation((int)(screenSize.getWidth()/2-600),(int)(screenSize.getHeight()/2-380));

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
        this.setTitle("[宿舍管理系统]    管理员："+adminName+" 欢迎您！");
        this.setVisible(true);
    }

}
