package com.example.ridko.warehousepda.client;


import java.io.Serializable;

public class OutboundDetail implements Serializable {
    private String epc;
//    匹配一
    private String clothNo;
    private String ticketNo;
    private Double weight;
//    匹配二
    private String vateDye;
    private int flag=0;

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public String getClothNo() {
        return clothNo;
    }

    public void setClothNo(String clothNo) {
        this.clothNo = clothNo;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getVateDye() {
        return vateDye;
    }

    public void setVateDye(String vateDye) {
        this.vateDye = vateDye;
    }

    public OutboundDetail() {
    }

    public OutboundDetail(String epc) {
        this.epc = epc;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
