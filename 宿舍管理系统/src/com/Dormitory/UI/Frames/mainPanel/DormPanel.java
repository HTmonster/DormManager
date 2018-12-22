/**
 * @ClassName: DormPanel
 * @Description: (用一句话描述该文件做什么)
 * @author Theo_hui
 * @Email theo_hui@163.com
 * @Date 2018/12/22 19:13
 */


package com.Dormitory.UI.Frames.mainPanel;

import com.Dormitory.jdbc.jdbcDormitory;
import com.Dormitory.model.RoomPo;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class DormPanel extends JPanel {

    //Table头
    private final String[] TITLE=new String[]{"宿舍号","总床位","空床位","宿舍长","价格"};
    private Vector<Vector<String>> dataModel = new Vector<Vector<String>>();

    //宿舍查询类
    jdbcDormitory dormitory=new jdbcDormitory();
    private List<RoomPo> Rooms=new ArrayList<>();

    public DormPanel(){

        init();

        //根据楼层信息搜索寝室号并树图显示
        final JTree tree=dorm_tree_show(8);
        //tree.setPreferredSize(new Dimension(200,700));
        this.add(tree, BorderLayout.WEST);


        //查询面板
        JPanel searchPanel =new JPanel();
        //searchPanel.setBackground(Color.yellow);
        this.add(searchPanel,BorderLayout.NORTH);

        //查询面板———提示标签
        JLabel slb = new JLabel("查询宿舍号");
        searchPanel.add(slb);

        //查询面板———信息输入框
        JTextField sText = new JTextField(10);
        searchPanel.add(sText);

        //查询面板——查询按钮
        JButton sbtn = new JButton("查询");
        searchPanel.add(sbtn);


        //信息面板
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.gray);
        this.add(infoPanel,BorderLayout.CENTER);

        //信息面板————表格
        Vector<String> titles = new Vector<String>(Arrays.asList(TITLE));
        JTable table = new JTable(dataModel, titles);
        JScrollPane scrollPane = new JScrollPane(table);
        infoPanel.add(scrollPane);

        //树图绑定事件
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e)
            {
                //显示该宿舍的信息
                showDormInfo(tree);
                //更新表格
                table.validate();
                table.updateUI();
            }
        });

        //查询按钮绑定事件
        sbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                querySingelRoom(table,sText);
                //更新表格
                table.validate();
                table.updateUI();
            }
        });

    }

    //基本信息初始化
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

    //类信息信息转换为表格显示信息
    public void Room2Vector(){
        dataModel.removeAllElements();//清除记录

        //转为Vector
        for(RoomPo room :Rooms){
            dataModel.add(new Vector<String>(Arrays.asList(room.getRno(),room.getBedNum(),
                    room.getEmptyBedNum(),room.getLeader(),room.getPrice())));//添加记录
        }

    }

    // 判断一个字符串是否都为数字
    public boolean isDigit(String strNum) {
        return strNum.matches("[0-9]{1,}");
    }

    //显示宿舍信息
    public void showDormInfo(JTree tree){
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node == null){
            return;
        }
        String s = (String) node.getUserObject();
        System.out.println("你选择了：" + s);

        //叶节点 单个宿舍
        Rooms.clear();
        if(node.isLeaf()){
            //Rooms.clear();
            Rooms.add(dormitory.getRoomPoByRno(s));
        }//非页节点和root
        else if(!node.isRoot()){
            System.out.println(node.children());
            Enumeration<DefaultMutableTreeNode> enume = node.children();
            while(enume.hasMoreElements()){
                DefaultMutableTreeNode t=enume.nextElement();
                Rooms.add(dormitory.getRoomPoByRno(t.toString()));
            }
        }

        Room2Vector();
    }

    //查询单个宿舍信息
    public void querySingelRoom(JTable table,JTextField text){
        String queryMsg=text.getText();

        if(queryMsg.isEmpty()){
            JOptionPane.showMessageDialog(null, "错误：无信息");
        }else if(! isDigit(queryMsg)){
            JOptionPane.showMessageDialog(null, "错误：非数字");
        }else{
            RoomPo queryRoom=null;
            queryRoom=dormitory.getRoomPoByRno(queryMsg);

            if(queryRoom==null){
                JOptionPane.showMessageDialog(null, "未查询到");
            }else{
                Rooms.clear();
                Rooms.add(queryRoom);

                Room2Vector();
            }
        }
    }
}
