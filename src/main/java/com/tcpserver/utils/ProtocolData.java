package com.tcpserver.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
* 用于存放回传时候的数据
*       successed：
*       field:
* */
public class ProtocolData {
    public static byte[] successed=new byte[]{(byte) 0xbb, (byte) 0x88,0x02, 0x31,(byte) 0xee, (byte) 0xff};

    public static byte[] failed=new byte[]{(byte) 0xbb, (byte) 0x88, 0x03, 0x30,(byte) 0xee, (byte) 0xff};


    public static byte[] times=new byte[]{(byte) 0xbb, (byte) 0x88, 0x04,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                                            0x00,0x00,0x00,0x00,0x00,0x00,0x30,(byte) 0xee, (byte) 0xff};



    /*
    * 修改时间，验证完毕
    * */

    public static void setTime(){
        Date currentTime=new Date();

        byte [] temp;

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");

        /*此处回传的字符1*/
        String date=dateFormat.format(currentTime);

        temp=date.getBytes();

        for (int i=0;i<temp.length;i++){
            times[i+3]=temp[i];
        }
        SimpleDateFormat timeFormat=new SimpleDateFormat("HHmmss");
        String time=timeFormat.format(currentTime);
        temp=time.getBytes();
        for(int i=0;i<temp.length;i++){
            times[i+11]=temp[i];
        }
        byte vocode=getVerCode(times);
        times[17]=vocode;

    }

    private static byte getVerCode(byte[] tempTimes) {
        int length=tempTimes.length;
        length-=2;//去掉最后面两个字节
        byte first=tempTimes[0];
        for (int i=1;i<length-1;i++){
            first= (byte) (first^tempTimes[i]);
        }
        return first;
    }

    public static byte[] getTime(){
        setTime();
        return times;
    }
}
