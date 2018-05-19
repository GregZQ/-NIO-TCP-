package com.tcpserver.utils;

/**
 * Created by Jone on 2018-05-18.
 */
public class CarData {
    /*
    *   鲁J28675
    * */
    private String carID;
    private Integer whiteList;
    private Integer direction;
    private Integer carType;
    private Integer carColor;
    private String lockId;
    private String time;

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public Integer getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(Integer whiteList) {
        this.whiteList = whiteList;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }

    public Integer getCarColor() {
        return carColor;
    }

    public void setCarColor(Integer carColor) {
        this.carColor = carColor;
    }

    public String getLockId() {
        return lockId;
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
