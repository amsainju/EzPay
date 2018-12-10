package com.example.arpan.ezpay;

public class Payment {
    private String id;
    private String paymentMethodName;

    public Payment(String id, String paymentMethodName){
        this.id=id;
        this.paymentMethodName=paymentMethodName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }
}
