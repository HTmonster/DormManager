/**
 * @ClassName: DormInfoPanel
 * @Description: (用一句话描述该文件做什么)
 * @author Theo_hui
 * @Email theo_hui@163.com
 * @Date 2018/12/22 19:18
 */


package com.Dormitory.UI.Frames.mainPanel;

import com.Dormitory.jdbc.jdbcDormitory;
import com.Dormitory.model.DSPo;
import com.Dormitory.model.RoomPo;
import com.Dormitory.model.StudentPo;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class DormInfoPanel extends JPanel {

    //表格及表项
    private final String[] TITLE_ACCOM=new String[]{"宿舍号","学号","入住时间","空余量"};
    private final String[] TITLE_STU=new String[]{"学号","姓名","性别","年龄","籍贯","院系"};
    private Vector<Vector<String>> dataModel_accom = new Vector<Vector<String>>();
    private Vector<Vector<String>> dataModel_stu = new Vector<Vector<String>>();

    //两个表格的状态
    private int ACCOMTABLE_STATE =0 ; //公用表的状态 0:初始化 1:DS表 2：Dorm表
    private int ACCOMTABLE_STATE_INIT =0;
    private int ACCOMTABLE_STATE_DS =1;
    private int ACCOMTABLE_STATE_Dorm =2;

    private int STUTABLE_STATE =0 ; //Stu表的状态 0:初始化 1:DS表的学生信息 2：Stu表
    private int STUTABLE_STATE_INIT =0;
    private int STUTABLE_STATE_DS =1;
    private int STUTABLE_STATE_STU =2;

    //宿舍查询类
    jdbcDormitory dormitory=new jdbcDormitory();
    private List<RoomPo> Rooms=new ArrayList<>();
    private List<DSPo> DSs=new ArrayList<>();
    private List<StudentPo> Students= new ArrayList<>();

    public DormInfoPanel(){

        init();

        //根据楼层信息搜索寝室号并树图显示
        final JTree tree=dorm_tree_show(8);
        this.add(tree, BorderLayout.WEST);


        //查询面板
        JPanel searchPanel =new JPanel();
        //searchPanel.setBackground(Color.yellow);
        this.add(searchPanel,BorderLayout.NORTH);

        //查询面板——查询空余宿舍按钮
        JButton searchEmptyBtn = new JButton("查找空余宿舍");
        searchPanel.add(searchEmptyBtn);

        //查询面板———提示标签
        JLabel slb = new JLabel("学生信息查询");

        //查询面板———信息输入框
        JTextField SnoText = new JTextField(10);
        searchPanel.add(slb);
        searchPanel.add(SnoText);

        //查询面板——查询按钮
        JButton SnoSearchbtn = new JButton("查询");
        searchPanel.add(SnoSearchbtn);


        //信息面板
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.gray);
        this.add(infoPanel,BorderLayout.CENTER);

        //信息面板————表格
        /*住宿信息&宿舍空余信息  DS与Room信息共用*/
        Vector<String> title1 = new Vector<String>(Arrays.asList(TITLE_ACCOM));
        JTable AccomTable = new JTable(dataModel_accom, title1);
        AccomTable.getColumnModel().getColumn(0).setPreferredWidth(20);//设置列宽
        AccomTable.getColumnModel().getColumn(1).setPreferredWidth(80);

        /*学生信息表*/
        Vector<String> title3 = new Vector<String>(Arrays.asList(TITLE_STU));
        JTable StuTable = new JTable(dataModel_stu, title3);
        StuTable.getColumnModel().getColumn(0).setPreferredWidth(80);//设置列宽
        StuTable.getColumnModel().getColumn(1).setPreferredWidth(20);
        StuTable.getColumnModel().getColumn(2).setPreferredWidth(20);
        StuTable.getColumnModel().getColumn(3).setPreferredWidth(20);
        StuTable.getColumnModel().getColumn(4).setPreferredWidth(20);
        StuTable.getColumnModel().getColumn(5).setPreferredWidth(20);

        JScrollPane scrollPane_accom = new JScrollPane(AccomTable);
        JScrollPane scrollPane_stu = new JScrollPane(StuTable);

        infoPanel.add(scrollPane_accom);
        infoPanel.add(scrollPane_stu);


        //删除增加面板
        JPanel setPanel = new JPanel();
        setPanel.setBackground(Color.lightGray);
        setPanel.setPreferredSize(new Dimension(600,120));
        setPanel.setLayout(new FlowLayout());
        this.add(setPanel,BorderLayout.SOUTH);


        //删除增加面板——删除面板
        JPanel set_delPanel = new JPanel();set_delPanel.setBackground(Color.lightGray);
        setPanel.add(set_delPanel);
        //删除增加面板——删除面板[信息显示]
        JTextField delStuText = new JTextField(20);
        JTextField delDormText = new JTextField(10);
        delStuText.setEditable(false);delDormText.setEditable(false);

        //删除增加面板——删除面板[按钮]
        JButton delBtn = new JButton("移除该条寄宿记录");
        JButton delAllBtn = new JButton("移除该宿舍的所有寄宿记录");
        delAllBtn.setEnabled(false);
        set_delPanel.add(delStuText);set_delPanel.add(delBtn);
        set_delPanel.add(delDormText);set_delPanel.add(delAllBtn);

        //修改删除面板——增加面板
        JPanel set_addPanel = new JPanel();set_addPanel.setBackground(Color.lightGray);
        setPanel.add(set_addPanel);
        //修改删除面板——增加面板[信息显示]
        JTextField addStuText =new JTextField(20);
        JTextField addDromText =new JTextField(20);
        addStuText.setEditable(false);addDromText.setEditable(false);
        set_addPanel.add(addStuText);set_addPanel.add(addDromText);

        //修改删除面板——增加面板[按钮]
        JButton addBtn = new JButton("安排学生入住");
        set_addPanel.add(addBtn);

        /*事件绑定区*/

        //树图绑定事件
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e)
            {
                //显示该宿舍的住宿信息及住宿学生信息
                showDSInfo(tree);
                //更新表格
                AccomTable.validate();AccomTable.updateUI();
                StuTable.validate();StuTable.updateUI();
            }
        });

        //设置AccomTable的鼠标事件
        AccomTable.addMouseListener(new MouseAdapter() {    //鼠标事件
            public void mouseClicked(MouseEvent e) {
                int selectedRow = AccomTable.getSelectedRow(); //获得选中行索引

                //Accom表为DS状态下 显示在住宿信息修改区域
                if(ACCOMTABLE_STATE==ACCOMTABLE_STATE_DS){
                    //显示 宿舍 ：学号
                    delStuText.setText(
                            AccomTable.getValueAt(selectedRow,1).toString()
                    );
                    //显示 宿舍
                    delDormText.setText(
                            AccomTable.getValueAt(selectedRow,0).toString()
                    );
                }//Accom表在Dorm空余表状态下 空余宿舍信息显示在新增记录区域
                else if(ACCOMTABLE_STATE==ACCOMTABLE_STATE_Dorm){
                    addDromText.setText(AccomTable.getValueAt(selectedRow,0).toString());
                }
            }
        });

        //设置StuTable的鼠标事件
        StuTable.addMouseListener(new MouseAdapter() {    //鼠标事件
            public void mouseClicked(MouseEvent e) {
                int selectedRow = StuTable.getSelectedRow(); //获得选中行索引

                //STU表为DS状态下 显示提示
                if(STUTABLE_STATE==STUTABLE_STATE_DS){
                    addStuText.setText("请选择未入住学生");
                }//STU表在STU下 学生信息显示在新增记录区域
                else if(STUTABLE_STATE==STUTABLE_STATE_STU){
                    addStuText.setText(StuTable.getValueAt(selectedRow,0).toString());
                }
            }
        });

        //空余寝室查询按钮事件
        searchEmptyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchEmptyRooms();
                AccomTable.validate();
                AccomTable.updateUI();

                addDromText.setText("请选择空闲房间");
                delStuText.setText("");delDormText.setText("");
            }
        });

        //学生查询按钮事件
        SnoSearchbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchStuBySno(SnoText);
                StuTable.validate();
                StuTable.updateUI();

                addStuText.setText("请选择要入住的学生");
                addDromText.setText("请查找空余宿舍");
                delStuText.setText("");delDormText.setText("");
            }
        });

        //删除按钮事件
        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delOneDsitem(delStuText,delDormText);
                AccomTable.validate();AccomTable.updateUI();
                StuTable.validate();StuTable.updateUI();
            }
        });


        //安排入住按钮事件
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrangeStuAccom(addStuText,addDromText);

                AccomTable.validate();
                AccomTable.updateUI();
            }
        });

    }

    //基本格式初始化
    public void init(){
        this.setBackground(Color.red);
        this.setLayout(new BorderLayout());
    }

    //楼层搜索宿舍并树图显示
    public JTree dorm_tree_show(int maxFloor){
        //List<DefaultMutableTreeNode> groups = new ArrayList<DefaultMutableTreeNode>();
        //根节点
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("学生宿舍");

        //每个楼层遍历
        for(int i=1;i<=maxFloor;i++){
            //创建楼层对应一级节点
            DefaultMutableTreeNode FloorNode=new DefaultMutableTreeNode(i+"楼");

            //搜索该楼层的宿舍号
            List<String> rs=dormitory.getRnoByFloor(String.valueOf(i));

            //每个宿舍遍历
            for(String attribute : rs){
                //宿舍节点 添加到楼层节点
                FloorNode.add(new DefaultMutableTreeNode(attribute));
            }

            top.add(FloorNode);
        }

        //创建树图
        return  new JTree(top);
    }

    //根据侧边导航数已经选择的宿舍显示住宿信息  住宿类 学生类 转换为表格显示信息
    //"宿舍号","学号","入住时间","空余量"
    //"宿舍号","学号","入住时间",
    //"学号","姓名","性别","年龄","籍贯","院系"
    public void DS_Student2Vector(){
        //accom stu数据清空
        dataModel_accom.removeAllElements();
        dataModel_stu.removeAllElements();

        //accom表   DS转为Vector
        for(DSPo DS :DSs){
            dataModel_accom.add(new Vector<String>(
                    Arrays.asList(DS.getRno(),DS.getSno(),
                            DS.getTime(),""
                    )));//添加记录
        }

        //student表  Student转为Vector
        for(StudentPo Stu :Students){
            dataModel_stu.add(new Vector<String>(Arrays.asList(
                    Stu.getSno(), Stu.getSname(), Stu.getGender(),
                    Stu.getAge(), Stu.getPlace(), Stu.getDept()
            )));//添加记录
        }
    }

    //根据查询的空余宿舍 宿舍类信息 转为表格信息
    //"宿舍号","学号","入住时间","空余量"
    //"宿舍号",      ,         , "空余量
    public void Room2Vector(){
        //acccom 数据清空
        dataModel_accom.clear();

        //accom表   Room转为Vector
        for(RoomPo room :Rooms){
            dataModel_accom.add(new Vector<String>(
                    Arrays.asList(room.getRno(),"",
                            "",room.getEmptyBedNum()
                    )));//添加记录
        }

    }

    //根据查询的学号信息 学生类信息 转为表格信息
    //"宿舍号","学号","姓名","性别","年龄","籍贯","院系"
    public void Student2Vector(){
        //stu 数据清空
        dataModel_stu.clear();

        //student表  Student转为Vector
        for(StudentPo Stu :Students){
            dataModel_stu.add(new Vector<String>(Arrays.asList(
                    Stu.getSno(), Stu.getSname(), Stu.getGender(),
                    Stu.getAge(), Stu.getPlace(), Stu.getDept()
            )));//添加记录
        }

    }

    // 判断一个字符串是否都为数字
    public boolean isDigit(String strNum) {
        return strNum.matches("[0-9]{1,}");
    }


    //根据侧边栏选择的宿舍号显示住宿信息 并显示住宿的学生信息
    public void showDSInfo(JTree tree){
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node == null){
            return;
        }
        String s = (String) node.getUserObject();
        System.out.println("你选择了：" + s);

        //只能选叶节点 单个宿舍
        DSs.clear();
        if(node.isLeaf()){
            DSs.clear();
            Students.clear();
            //获得该宿舍的住宿信息
            DSs=dormitory.getDSPoListByRno(s);

            //获得该宿舍住宿的学生的信息
            for(DSPo ds: DSs){
                Students.add(dormitory.getStudentPoBySno(ds.getSno()));
            }

            //表状态改变
            ACCOMTABLE_STATE=ACCOMTABLE_STATE_DS;
            STUTABLE_STATE=STUTABLE_STATE_DS;

            //数据转换
            DS_Student2Vector();
        }

        DS_Student2Vector();
    }

    //获得空余的宿舍并显示
    public void searchEmptyRooms(){
        //Accom表为空余宿舍状态
        ACCOMTABLE_STATE=ACCOMTABLE_STATE_Dorm;

        //清除老数据
        Rooms.clear();

        //数据库查询
        Rooms = dormitory.getEmptyRooms();
        //转换数据
        Room2Vector();
    }

    //通过学号查询学生信息
    public void SearchStuBySno(JTextField snoText){
        String sno =snoText.getText();

        if(sno.equals("")){
            JOptionPane.showMessageDialog(null,"不能为空");
            return;
        }else if(!isDigit(sno)){
            JOptionPane.showMessageDialog(null,"错误 包含非数字");
        }else {
            StudentPo rs=null;
            rs= dormitory.getStudentPoBySno(sno);

            //查询失败
            if(rs==null){
                JOptionPane.showMessageDialog(null,"抱歉 查询失败");
            }else{
                //成功
                //添加数据
                Students.clear();
                Students.add(rs);

                //STU表状态改变
                STUTABLE_STATE=STUTABLE_STATE_STU;
                //数据转换
                Student2Vector();
            }
        }
    }

    //删除一条住宿记录
    public void delOneDsitem(JTextField delStuText,JTextField delDormText){
        String stu=delStuText.getText();
        String dorm= delDormText.getText();

        if(stu.equals("")||dorm.equals("")){
            JOptionPane.showMessageDialog(null,"错误 不能为空");
            return;
        }
        else{
            int flag =dormitory.deleteStudent(stu,dorm);
            if(flag==0){
                JOptionPane.showMessageDialog(null,"抱歉 删除失败");
            }else{
                JOptionPane.showMessageDialog(null,"删除成功");

                //数据更新
                //DS要重新加载
                DSs.clear();
                DSs=dormitory.getDSPoListByRno(dorm);

                //students只要移出原来的就行
                Iterator<StudentPo> iter = Students.iterator();
                while (iter.hasNext()) {
                    StudentPo item = iter.next();
                    if (item.getSno().equals(stu)) {
                        iter.remove();
                    }
                }
                //数据转换
                DS_Student2Vector();

                //输入栏清空
                delDormText.setText("");delStuText.setText("");
            }

        }

    }

    //安排学生入住
    public void ArrangeStuAccom(JTextField stuText,JTextField dormText){
        String stu = stuText.getText();
        String dorm = dormText.getText();

        if(stu.equals("")||dorm.equals("")){
            JOptionPane.showMessageDialog(null,"错误 不能为空");
            return;
        }
        else if(!isDigit(stu)||!isDigit(dorm)){
            JOptionPane.showMessageDialog(null,"错误 包含非数字");
            return;
        }else{
            DSPo s_rs=null;
            s_rs =dormitory.getDSPoBySno(stu);

            if(s_rs!=null){
                JOptionPane.showMessageDialog(null,"错误 该生已经入住");
                stuText.setText("");
                return;
            }else{
                int flag =dormitory.addStudent(stu,dorm);
                if(flag==0){
                    JOptionPane.showMessageDialog(null,"抱歉 添加失败");
                }else{
                    JOptionPane.showMessageDialog(null,"添加成功");

                    //输入栏清空
                    stuText.setText("");dormText.setText("");
                    //数据更新
                    searchEmptyRooms();
                }
            }
        }
    }
}
