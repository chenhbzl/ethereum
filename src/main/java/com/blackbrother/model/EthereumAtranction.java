package com.blackbrother.model;

public class EthereumAtranction {
    private String transactionarr;

    private String address;

    private Integer blockid;
    
    private String[] transactionList;

    public String getTransactionarr() {
        return transactionarr;
    }

    public void setTransactionarr(String transactionarr) {
        this.transactionarr = transactionarr == null ? null : transactionarr.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getBlockid() {
        return blockid;
    }

    public void setBlockid(Integer blockid) {
        this.blockid = blockid;
    }

	public String[] getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(String[] transactionList) {
		this.transactionList = transactionList;
	}
    
    
}