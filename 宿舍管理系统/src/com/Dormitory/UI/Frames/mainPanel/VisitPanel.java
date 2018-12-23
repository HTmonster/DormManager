/**
 * @ClassName: VisitPanel
 * @Description: 访客信息面板
 * @author Theo_hui
 * @Email theo_hui@163.com
 * @Date 2018/12/22 19:20
 */


package com.Dormitory.UI.Frames.mainPanel;

import com.Dormitory.model.VisitPo;
import com.Dormitory.jdbc.jdbcVisit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class VisitPanel extends JPanel {

    //Table头
    private final String[] TITLE=new String[]{"访问宿舍号","姓名","证件","证件号","日期","备注"};
    private Vector<Vector<String>> dataModel = new Vector<Vector<String>>();

    //访问信息类
    jdbcVisit visitjdbc = new jdbcVisit();
    List<VisitPo> visits = new ArrayList<>();

    public VisitPanel(){

        init();

//        //搜索面板
//        JPanel searchPanel = new JPanel();
//        searchPanel.setBackground(Color.blue);
//        searchPanel.setPreferredSize(new Dimension(200,800));
//        this.add(searchPanel,BorderLayout.EAST);
//
//        //
//        JLabel searchlb = new JLabel("搜索宿舍访问记录");
//        JTextField rnoText = new JTextField();
//        JButton search = new JButton("搜索");

        //中央表格
        Vector<String> title = new Vector<String>(Arrays.asList(TITLE));
        refreshData();//更新数据 首次显示
        JTable VisitTable = new JTable(dataModel, title);
//        VisitTable.getColumnModel().getColumn(0).setPreferredWidth(20);//设置列宽
//        VisitTable.getColumnModel().getColumn(2).setPreferredWidth(20);
//        VisitTable.getColumnModel().getColumn(3).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(VisitTable);
        this.add(scrollPane, BorderLayout.CENTER);

        //增加来访记录面板
        JPanel addPanel = new JPanel();
        addPanel.setBackground(Color.lightGray);
        addPanel.setPreferredSize(new Dimension(200,100));
        this.add(addPanel,BorderLayout.SOUTH);

        //增加来访记录面板————标签
        JLabel rnolb = new JLabel("访问宿舍");
        JLabel namelb = new JLabel("来访者姓名");
        JLabel credlb = new JLabel("证件");
        JLabel cnolb =new JLabel("证件号码");
        JLabel remarklb =new JLabel("备注");
        //增加来访记录面板————文本领域
        JTextField rnoText = new JTextField(8);
        JTextField nameText = new JTextField(20);
        JTextField cnoText = new JTextField(20);
        JTextField remarkText = new JTextField(20);
        //增加来访记录面板————选择框
        JComboBox creComboBox =new JComboBox();
        creComboBox.addItem("学生证");
        creComboBox.addItem("身份证");
        creComboBox.addItem("护照");
        creComboBox.addItem("其他(请备注)");
        //增加来访记录面板————按钮
        JButton addBtn = new JButton("确认");

        addPanel.add(rnolb);addPanel.add(rnoText);
        addPanel.add(namelb);addPanel.add(nameText);
        addPanel.add(credlb);addPanel.add(creComboBox);
        addPanel.add(cnolb);addPanel.add(cnoText);
        addPanel.add(remarklb);addPanel.add(remarkText);
        addPanel.add(addBtn);


        /*事件绑定区*/

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOneVisit(rnoText,nameText,creComboBox,cnoText,remarkText);
                VisitTable.validate();
                VisitTable.updateUI();
            }
        });
    }

    //基本信息初始化
    public void init(){
        this.setBackground(Color.red);
        this.setLayout(new BorderLayout());
    }

    //信息转化
    public void visit2vector(){
        dataModel.clear();

        //"访问宿舍号","姓名","证件","证件号","日期","备注"
        for(VisitPo item:visits){
            dataModel.add(new Vector<>(Arrays.asList(
                    item.getRno(),item.getVname(),
                    item.getCred(),item.getCno(),
                    item.getDate(),item.getRemark()
            )));
        }
    }

    //更新数据
    public void refreshData(){
        visits.clear();

        //查找数据库
        visits=visitjdbc.getVisitPo();

        //数据转换
        visit2vector();
    }

    public void addOneVisit(JTextField rnoText,JTextField nameText,JComboBox creComboBox,JTextField cnoText,JTextField remarkText){
        String rno =rnoText.getText();
        String name=nameText.getText();
        String cre=creComboBox.getSelectedItem().toString();
        String cno=cnoText.getText();
        String remark=remarkText.getText();

        if(name.equals("")){
            JOptionPane.showMessageDialog(null,"必须登记姓名");
            return;
        }else if(cno.equals("")){
            JOptionPane.showMessageDialog(null,"必须填写号码");
            return;
        }else{
            int flag=visitjdbc.addVist(rno,name,cre,cno,remark);
            if(flag==0){
                JOptionPane.showMessageDialog(null,"抱歉 添加失败");
            }else{
                JOptionPane.showMessageDialog(null,"添加成功！");

                //输入框清空
                rnoText.setText("");nameText.setText("");creComboBox.setSelectedIndex(0);
                cnoText.setText("");remarkText.setText("");

                //更新数据
                refreshData();
            }
        }

    }
}
