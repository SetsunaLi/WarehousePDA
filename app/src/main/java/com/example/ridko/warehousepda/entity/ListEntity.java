package com.example.ridko.warehousepda.entity;

/**
 * Created by mumu on 2018/6/13.
 */

public class ListEntity {
    private String buNo;
    private String buName;
    private String ranchangNo;
    private int count;
    private float weight;

    public String getBuNo() {
        return buNo;
    }

    public void setBuNo(String buNo) {
        this.buNo = buNo;
    }

    public String getBuName() {
        return buName;
    }

    public void setBuName(String buName) {
        this.buName = buName;
    }

    public String getRanchangNo() {
        return ranchangNo;
    }

    public void setRanchangNo(String ranchangNo) {
        this.ranchangNo = ranchangNo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
