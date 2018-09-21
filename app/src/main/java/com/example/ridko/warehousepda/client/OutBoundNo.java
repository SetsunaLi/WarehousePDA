package com.example.ridko.warehousepda.client;

/**
 * Created by mumu on 2018/9/20.
 */

public class OutBoundNo {
    private boolean isOut=true;
    private String applyNo;
    private boolean isHead=false;

    public OutBoundNo(boolean isOut, String applyNo, boolean isHead) {
        this.isOut = isOut;
        this.applyNo = applyNo;
        this.isHead = isHead;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public boolean isHead() {
        return isHead;
    }

    public void setHead(boolean head) {
        isHead = head;
    }
}
