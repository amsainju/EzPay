package com.example.arpan.ezpay;

public class PaidBills {
    private String pbId;
    //private String orgId;
    private String orgType;
    private String orgSaveAsName;
    private String dueDate;
    private String amount;
    private String lReading;//TBD
    private String cReading; // TBD

    public PaidBills(String pbId, String orgType, String orgSaveAsName, String dueDate, String amount) {
        this.pbId = pbId;
        //this.ordId = ordId;
        this.orgType = orgType;
        this.orgSaveAsName = orgSaveAsName;
        this.dueDate = dueDate;
        this.amount = amount;
    }

    public String getPbId() {
        return pbId;
    }

    /*public String getOrdId() {
        return ordId;
    }
*/
    public String getOrgType() {
        return orgType;
    }

    public String getOrgSaveAsName() {
        return orgSaveAsName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setPbId(String pbId) {
        this.pbId = pbId;
    }

   /* public void setOrdId(String ordId) {
        this.ordId = ordId;
    }
*/
    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public void setOrgSaveAsName(String orgSaveAsName) {
        this.orgSaveAsName = orgSaveAsName;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
