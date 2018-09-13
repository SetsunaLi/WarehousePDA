package com.example.ridko.warehousepda.entity;

/**
 * Created by mumu on 2018/9/4.
 */

public class DemoEntity2 {
    private int clothingNO;
    private int buNO;
    private float weight;
    private String dyelotNO;
    private String epcNO;
    /*0代表默认状态原色
    * 1代表找到状态绿色
    * 2代表串读状态红色
    * 点击状态蓝色*/
    private int flag=0;

    public int isFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getClothingNO() {
        return clothingNO;
    }

    public void setClothingNO(int clothingNO) {
        this.clothingNO = clothingNO;
    }

    public int getBuNO() {
        return buNO;
    }

    public void setBuNO(int buNO) {
        this.buNO = buNO;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDyelotNO() {
        return dyelotNO;
    }

    public void setDyelotNO(String dyelotNO) {
        this.dyelotNO = dyelotNO;
    }

    public String getEpcNO() {
        return epcNO;
    }

    public void setEpcNO(String epcNO) {
        this.epcNO = epcNO;
    }
}
