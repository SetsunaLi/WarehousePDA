package com.example.ridko.warehousepda.client;


import java.io.Serializable;

public class OutboundApplyDetail implements Serializable {
    /**
     * 申请单号
     */
    private String applyNo;
    /**
     * 申请布号
     */
    private String clothNo;
    /**
     * 申请布种名称
     */
    private String clothName;
    /**
     * 申请布匹颜色
     */
    private String colorName;
    /**
     * 申请布匹色号
     */
    private String colorNo;
    /**
     * 申请布匹缸号
     */
    private String vatDyeNo;
    /**
     * 申请布匹条数
     */
    private Integer num;

    public OutboundApplyDetail() {
    }

    public OutboundApplyDetail(String applyNo, String clothNo, String clothName, String colorName, String colorNo, String vatDyeNo, Integer num) {
        this.applyNo = applyNo;
        this.clothNo = clothNo;
        this.clothName = clothName;
        this.colorName = colorName;
        this.colorNo = colorNo;
        this.vatDyeNo = vatDyeNo;
        this.num = num;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getClothNo() {
        return clothNo;
    }

    public void setClothNo(String clothNo) {
        this.clothNo = clothNo;
    }

    public String getClothName() {
        return clothName;
    }

    public void setClothName(String clothName) {
        this.clothName = clothName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorNo() {
        return colorNo;
    }

    public void setColorNo(String colorNo) {
        this.colorNo = colorNo;
    }

    public String getVatDyeNo() {
        return vatDyeNo;
    }

    public void setVatDyeNo(String vatDyeNo) {
        this.vatDyeNo = vatDyeNo;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
