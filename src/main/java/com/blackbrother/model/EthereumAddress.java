package com.blackbrother.model;

public class EthereumAddress {
    private String addressid;

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid == null ? null : addressid.trim();
    }
}