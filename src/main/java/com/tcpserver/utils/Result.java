package com.tcpserver.utils;

/**
 * Created by Jone on 2018-05-18.
 */
public class Result {
    /*
    * 保存状态 ok 或者 false
    * */
    private String status;
    /*
    * 接受数据使用的协议
    * */
    private Integer type;
    /*
        * 保存解析或者需要传输的数据
    * */
    private  Object data;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public static Result build(String status,Object data){
        Result result=new Result();
        result.setStatus(status);
        result.setData(data);
        return result;
    }
}
