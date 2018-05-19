package com.tcpserver.utils;

import java.util.Arrays;

/*
* 作用:解析所有的报文(根据报文数据)
*    如果解析失败或者验证失败，将status置为false
*                           否则将status置为true
*
* */
public class AnalysisUtils {

    public static Result analysis(byte[] bytes,int length){

        Result result=null;
        /*
        * 接收到数据之后先进行检验，检验不通过的话返回false
        * */
        String status=checkValue(bytes,length);
        if(status.equals("false")){
            result=Result.build(status,null);
        }else{
            if (length>=3){
                if ((bytes[2]&0x01)==1){
                    result=analysisCar(bytes);
                    result.setType(1);
                    result.setStatus("true");
                }else if ((bytes[2]&0x04)==4){
                    result=analysisTime(bytes);
                    result.setType(4);
                    result.setStatus("true");
                }else if ((bytes[2]&0x05)==5){
                    result=analysisPosition(bytes);
                    result.setType(5);
                    result.setStatus("true");
                }else {
                    result=Result.build("false",null);
                }
            }else{
                result=Result.build("false",null);
            }
        }

        return result;
    }

    private static Result analysisTime(byte[] bytes) {
        Result result=new Result();
        result.setStatus("true");
        result.setType(4);
        return result;
    }

    /*
    *   对05类型报文进行数据解析
    * */
/*    private static Result analysisPosition(byte[] bytes) {
        Result result= new Result();
        result.setStatus("true");
        CarLock data = new CarLock();

        String lockId=new String(Arrays.copyOfRange(bytes,3,12));

        data.setLockId(lockId.toString());

        String IP=new String(Arrays.copyOfRange(bytes,12,24));
        data.setIP(IP);

        String positionX=new String(Arrays.copyOfRange(bytes,24,35));
        String positionY=new String(Arrays.copyOfRange(bytes,35,46));
        data.setPositionX(positionX);
        data.setPositionY(positionY);

        result.setData(data);
        return result;
    }*/
    private static Result analysisPosition(byte[] bytes) {
        Result result= new Result();
        CarLock data = new CarLock();
        result.setStatus("true");
        result.setType(5);
        String lockid = "";
        String ip = "";
        String positionx = "";
        String positiony = "";
        for(int i=0; i<12; i++) {
            if (i<9) {
                lockid = lockid + bytes[3+i];
            }
            if (i<12) {
                ip = ip+bytes[12+i];
            }
            if (i<11) {
                positionx = positionx+bytes[24+i];
                positiony = positiony+bytes[35+i];
            }
        }
        data.setLockId(lockid);
        data.setIP(ip);
        data.setPositionX(positionx);
        data.setPositionY(positiony);
        result.setData(data);
        return result;
    }
    /*
    * 对01类型报文进行数据解析
    * */
/*    private static Result analysisCar(byte[] bytes) {
        Result result= new Result();
        result.setType(1);
        CarData data = new CarData();

        String carID=new String(Arrays.copyOfRange(bytes,5,12));
        data.setCarID(carID);


        Integer whiteList=Integer.valueOf(Arrays.copyOfRange(bytes,12,13)[0]);
        data.setWhiteList(whiteList);

        Integer direction=Integer.valueOf(Arrays.copyOfRange(bytes,13,14)[0]);
        data.setDirection(direction);

        Integer carType = Integer.valueOf(Arrays.copyOfRange(bytes,14,15)[0]);
        data.setCarType(carType);


        Integer carColor=Integer.valueOf(Arrays.copyOfRange(bytes,15,16)[0]);
        data.setCarColor(carColor);

        String lockId=new String(Arrays.copyOfRange(bytes,16,24));
        data.setLockId(lockId);

        result.setData(data);
        return result;
    }*/
    private static Result analysisCar(byte[] bytes) {

        Result result= new Result();
        CarData data = new CarData();
        result.setStatus("true");
        result.setType(1);
        String carid = "";
        int whitelist = bytes[12];
        int direction = bytes[13];
        int cartype = bytes[14];
        int carcolor = bytes[15];
        String lockid = "";
        String time = "";

        for(int i =0; i<bytes.length; i++) {
            if (i<7) {
                carid = carid + bytes[5+i];
            }
            if (i<9) {
                lockid = lockid + bytes[16+i];
            }
        }
        data.setCarID(carid);
        data.setWhiteList(whitelist);
        data.setDirection(direction);
        data.setCarType(cartype);
        data.setCarColor(carcolor);
        data.setLockId(lockid);
        result.setData(data);
        return result;
    }
    /*
    * 报文正确性验证，如果验证不通过，需要再次重传处理
    * */
    private static String checkValue(byte[] bytes,int length) {
        /*
        * 获取校验码的位置,亦或后若为0表示成功
        *                  否则失败
        * */
        if (length<=2){
            return "false";
        }
        int nums= length-3;
        byte checkNum=bytes[nums];
        for (int i=0;i<nums;i++){
            checkNum= (byte) (checkNum^bytes[i]);
        }
        if (checkNum==0){
            return "true";
        }
        else{
            return "false";
        }
    }
}