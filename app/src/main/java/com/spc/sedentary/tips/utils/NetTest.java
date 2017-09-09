package com.spc.sedentary.tips.utils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by spc on 2017/9/9.
 */

public class NetTest {
    static DatagramSocket ds;

    public static void sendMessageToServer(String str_send) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {

                    if (ds == null) {
                        ds = new DatagramSocket(8824);
                    }
                    byte[] backbuf = new byte[1024];

                    InetAddress address = InetAddress.getByName("192.168.1.9");
                    DatagramPacket dp_send = new DatagramPacket(str_send.getBytes(), str_send.length(), address, 7724);
                    ds.setSoTimeout(3000);              //设置接收数据时阻塞的最长时间
                    ds.send(dp_send);
                    TLog.e("向服务器 发送了" + str_send);
                } catch (Exception e) {
                    e.printStackTrace();
                    TLog.e(e.getMessage());
                }
            }
        }.start();

    }
}
