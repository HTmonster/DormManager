/**
 * @ClassName: feePanel
 * @Description: (用一句话描述该文件做什么)
 * @author Theo_hui
 * @Email theo_hui@163.com
 * @Date 2018/12/23 10:54
 */


package com.Dormitory.UI.Frames.mainPanel;

import com.Dormitory.jdbc.jdbcFee;
import com.Dormitory.model.FeePo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class feePanel extends JPanel {

    //Table头
    private final String[] TITLE=new String[]{"宿舍号","水量","水费","电量","电费","总费用","支付状态"};
    private Vector<Vector<String>> dataModel = new Vector<Vector<String>>();

    //水电费信息类
    jdbcFee feejdbc = new jdbcFee();
    List<FeePo> fees = new ArrayList<>();

    public feePanel(){

        init();

        //搜索面板
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.lightGray);
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setPreferredSize(new Dimension(200,800));
        this.add(searchPanel,BorderLayout.WEST);

        //搜索面板
        JButton showallBtn = new JButton("所有记录");
        JButton  showNotPaidBtn = new JButton("未缴费记录");

        searchPanel.add(showallBtn);searchPanel.add(showNotPaidBtn);

        //中央面板
        Vector<String> title = new Vector<String>(Arrays.asList(TITLE));
        refreshData();//更新数据 首次显示
        JTable feeTable = new JTable(dataModel, title);
        feeTable.getColumnModel().getColumn(0).setPreferredWidth(20);//设置列宽
        feeTable.getColumnModel().getColumn(2).setPreferredWidth(20);
        feeTable.getColumnModel().getColumn(3).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(feeTable);
        this.add(scrollPane, BorderLayout.CENTER);

        //操作面板
        JPanel opPanel = new JPanel();
        opPanel.setBackground(Color.gray);
        this.add(opPanel,BorderLayout.SOUTH);

        //操作面板————标签
        JLabel rnolb = new JLabel("宿舍号：");
        JLabel feelb = new JLabel("费用：");
        JLabel paidlb = new JLabel("缴费：");

        //操作面板————输入框
        JTextField rnoText = new JTextField(8);
        JTextField feeText = new JTextField(8);
        JTextField isPaidText = new JTextField(2);
        rnoText.setEditable(false);feeText.setEditable(false);isPaidText.setEditable(false);

        //按钮
        JButton setPaidBtn = new JButton("缴费");

        opPanel.add(rnolb);opPanel.add(rnoText);
        opPanel.add(feelb);opPanel.add(feeText);
        opPanel.add(paidlb);opPanel.add(isPaidText);
        opPanel.add(setPaidBtn);

        /*事件响应区*/

        //显示所有按钮
        showallBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
                feeTable.validate();
                feeTable.updateUI();
            }
        });

        //显示未缴费按钮
        showNotPaidBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNotPaid();
                feeTable.validate();
                feeTable.updateUI();
            }
        });

        //表格鼠标事件
        feeTable.addMouseListener(new MouseAdapter() {    //鼠标事件
            public void mouseClicked(MouseEvent e) {
                int selectedRow = feeTable.getSelectedRow(); //获得选中行索引

                //显示具体的选择信息
                rnoText.setText(feeTable.getValueAt(selectedRow,0).toString());
                feeText.setText(feeTable.getValueAt(selectedRow,5).toString()+" 元");
                isPaidText.setText(feeTable.getValueAt(selectedRow,6).toString());
            }
        });

        //缴费按钮
        setPaidBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPaid(rnoText,feeText,isPaidText);
                feeTable.validate();
                feeTable.updateUI();
            }
        });
    }

    //基本信息初始化
    public void init(){
        this.setBackground(Color.red);
        this.setLayout(new BorderLayout());
    }

    //信息转化
    public void fee2vector(){
        dataModel.clear();

        //"宿舍号","水量","水费","电量","电费","总费用","支付状态"
        for(FeePo item:fees){
            dataModel.add(new Vector<>(Arrays.asList(
                    item.getRno(),item.getWater(),
                    item.getWbill(),item.getElec(),
                    item.getEbill(),item.getTotal(),
                    item.getPaid()
            )));
        }
    }

    //更新数据
    public void refreshData(){
        fees.clear();

        //查找数据库
        fees=feejdbc.getFeePo();

        //数据转换
        fee2vector();
    }

    //查找未支付
    public void showNotPaid(){
        fees.clear();

        fees=feejdbc.getFeePo_NotPaid();

        fee2vector();
    }

    //设置缴费
    public void setPaid(JTextField rnoText,JTextField feeText,JTextField isPaidText){
        String rno = rnoText.getText();
        String isPaid =isPaidText.getText();

        if(rno.equals("")){
            JOptionPane.showMessageDialog(null,"不能为空");
            return;
        }
        else if(isPaid.equals("是")){
            JOptionPane.showMessageDialog(null,"该寝室已经缴费");
            return;
        }else{
            int  flag = feejdbc.updateFee(rno,"是");
            if(flag==0){
                /*操作失败*/
                JOptionPane.showMessageDialog(null,"抱歉 操作失败");
            }else{
                /*操作成功*/
                JOptionPane.showMessageDialog(null,"操作成功");

                //输入框清空
                rnoText.setText("");feeText.setText("");isPaidText.setText("");

                //更新数据
                refreshData();
            }
        }
    }


}
