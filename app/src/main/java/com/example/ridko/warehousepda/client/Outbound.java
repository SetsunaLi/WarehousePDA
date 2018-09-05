package com.example.ridko.warehousepda.client;


import java.io.Serializable;
import java.util.List;

public class Outbound implements Serializable {
    /**
     * 出库申请单号
     */
    private String applyNo;
    /**
     * 备注
     */
    private String remarks;

    /**
     * 出库申请单详情
     */
    private List<OutboundDetail> outboundDetails;

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<OutboundDetail> getOutboundDetails() {
        return outboundDetails;
    }

    public void setOutboundDetails(List<OutboundDetail> outboundDetails) {
        this.outboundDetails = outboundDetails;
    }
}
