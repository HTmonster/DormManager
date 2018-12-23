/**
 * @ClassName: ClientApp
 * @Description: 客户端运行的主体
 * @author Theo_hui
 * @Email theo_hui@163.com
 * @Date 2018/12/22 19:09
 */


package com.Dormitory.UI;

import com.Dormitory.UI.Frames.loginFrame;
import com.Dormitory.UI.Frames.mainFrame;

import java.awt.*;

//直接进行登录界面
public class ClientApp {
    public static void main(String[] args) {
        //运行主体框架
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //登录界面
                loginFrame onload =new loginFrame();
                //mainFrame main =new mainFrame();
            }
        });
    }
}
