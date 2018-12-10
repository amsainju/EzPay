package com.example.arpan.ezpay;

public class Organization {
    private String id;
    private String OrganizationName;

    public Organization(String id, String OrganizationName){
        this.id=id;
        this.OrganizationName=OrganizationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return OrganizationName;
    }

    public void setOrganizationName(String OrganizationName) {
        this.OrganizationName = OrganizationName;
    }
}
