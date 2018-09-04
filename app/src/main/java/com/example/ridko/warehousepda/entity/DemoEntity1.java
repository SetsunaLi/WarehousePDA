package com.example.ridko.warehousepda.entity;

/**
 * Created by mumu on 2018/8/31.
 */

public class DemoEntity1 {
    private String dyelotNO;
    private int buNO;
    private int applyCount;
    private int practicalCount;
    private int readCount;
    private String state;

    public int getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(int applyCount) {
        this.applyCount = applyCount;
    }

    public int getPracticalCount() {
        return practicalCount;
    }

    public void setPracticalCount(int practicalCount) {
        this.practicalCount = practicalCount;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }



    public String getDyelotNO() {
        return dyelotNO;
    }

    public void setDyelotNO(String dyelotNO) {
        this.dyelotNO = dyelotNO;
    }

    public int getBuNO() {
        return buNO;
    }

    public void setBuNO(int buNO) {
        this.buNO = buNO;
    }



    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
