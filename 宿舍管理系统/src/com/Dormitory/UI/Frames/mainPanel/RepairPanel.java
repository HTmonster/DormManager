/**
 * @ClassName: RepairPanel
 * @Description: 维修面板
 * @author Theo_hui
 * @Email theo_hui@163.com
 * @Date 2018/12/24 22:22
 */


package com.Dormitory.UI.Frames.mainPanel;

import com.Dormitory.jdbc.jdbcRepair;
import com.Dormitory.model.RepairPo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class RepairPanel extends JPanel {

    //Table头
    private final String[] TITLE=new String[]{"宿舍号","设备","日期","备注"};
    private Vector<Vector<String>> dataModel = new Vector<Vector<String>>();

    //维修信息类
    jdbcRepair repairjdbc = new jdbcRepair();
    List<RepairPo> repairs = new ArrayList<>();

    public RepairPanel(){

        init();
        
        //上层搜索面板
        JPanel searchPanel =new JPanel();
        //searchPanel.setBackground(Color.gray);
        this.add(searchPanel,BorderLayout.NORTH);

        //上层搜索面板————组件
        JLabel searchlb = new JLabel("查找宿舍维修记录：");
        JTextField searchText = new JTextField(20);
        JButton searchBtn = new JButton("搜索");
        JButton showAllBtn = new JButton("显示所有");
        searchPanel.add(searchlb);searchPanel.add(searchText);
        searchPanel.add(searchBtn);searchPanel.add(showAllBtn);


        //中央表格
        Vector<String> title = new Vector<String>(Arrays.asList(TITLE));
        refreshData();//更新数据 首次显示
        JTable RepairTable = new JTable(dataModel, title);
//        RepairTable.getColumnModel().getColumn(0).setPreferredWidth(20);//设置列宽
//        RepairTable.getColumnModel().getColumn(2).setPreferredWidth(20);
//        RepairTable.getColumnModel().getColumn(3).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(RepairTable);
        this.add(scrollPane, BorderLayout.CENTER);

        //增加维修记录面板
        JPanel addPanel = new JPanel();
        addPanel.setBackground(Color.lightGray);
        addPanel.setPreferredSize(new Dimension(200,100));
        this.add(addPanel,BorderLayout.SOUTH);

        //增加维修记录面板————标签
        JLabel rnolb = new JLabel("宿舍");
        JLabel eqlb = new JLabel("维修物品");
        JLabel remarklb =new JLabel("备注");
        
        //增加维修记录面板————文本领域
        JTextField rnoText = new JTextField(20);
        JTextField remarkText = new JTextField(40);
        //增加维修记录面板————选择框
        JComboBox eqComboBox =new JComboBox();
        eqComboBox.addItem("床");
        eqComboBox.addItem("柜子");
        eqComboBox.addItem("桌椅");
        eqComboBox.addItem("管道/水龙头/莲蓬头");
        eqComboBox.addItem("空调");
        eqComboBox.addItem("窗门");
        eqComboBox.addItem("其他(请备注)");
        //增加维修记录面板————按钮
        JButton addBtn = new JButton("确认");

        addPanel.add(rnolb);addPanel.add(rnoText);
        addPanel.add(eqlb);addPanel.add(eqComboBox);
        addPanel.add(remarklb);addPanel.add(remarkText);
        addPanel.add(addBtn);


        /*事件绑定区*/
        
        
        //搜索按钮
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchItemByRno(searchText);
                RepairTable.validate();
                RepairTable.updateUI();
            }
        });

        //显示所有
        showAllBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
                RepairTable.validate();
                RepairTable.updateUI();
            }
        });
        
        //添加按钮
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOneRepair(rnoText,eqComboBox,remarkText);
                RepairTable.validate();
                RepairTable.updateUI();
            }
        });
    }

    //基本信息初始化
    public void init(){
        this.setBackground(Color.red);
        this.setLayout(new BorderLayout());
    }

    //信息转化
    public void repair2vector(){
        dataModel.clear();

        //"宿舍号","设备","日期","备注"
        for(RepairPo item:repairs){
            dataModel.add(new Vector<>(Arrays.asList(
                    item.getRno(),item.getEquipment(),
                    item.getDate(),item.getRemark()
            )));
        }
    }

    //更新数据
    public void refreshData(){
        repairs.clear();

        //查找数据库
        repairs=repairjdbc.getAllRepairPo();

        //数据转换
        repair2vector();
    }
    
    //搜索宿舍的维修记录
    public void searchItemByRno(JTextField searchText){
        String rno = searchText.getText();

        if(rno.equals("")){
            JOptionPane.showMessageDialog(null,"错误 不能为空");
            return;
        }else{
            repairs.clear();
            repairs=repairjdbc.getRepairPoByrno(rno);

            repair2vector();
        }
        
    }

    //添加一条记录
    public void addOneRepair(JTextField rnoText,JComboBox eqComboBox,JTextField remarkText){
        String rno =rnoText.getText();
        String eqp=eqComboBox.getSelectedItem().toString();
        String remark=remarkText.getText();

        if(rno.equals("")){
            JOptionPane.showMessageDialog(null,"必须登记寝室");
            return;
        }
        else{
            int flag=repairjdbc.addRepairment(rno,eqp,remark);
            if(flag==0){
                JOptionPane.showMessageDialog(null,"抱歉 添加失败");
            }else{
                JOptionPane.showMessageDialog(null,"添加成功！");

                //输入框清空
                rnoText.setText("");eqComboBox.setSelectedIndex(0);remarkText.setText("");

                //更新数据
                refreshData();
            }
        }

    }
}
