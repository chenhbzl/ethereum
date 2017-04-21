package com.blackbrother.dao;

import java.util.List;

import com.blackbrother.model.EthereumAtranction;

public interface EthereumAtranctionMapper {
    int insert(EthereumAtranction record);

    int insertSelective(EthereumAtranction record);
    
    List<EthereumAtranction> getTransactions();
    
    void addTransactionCount(EthereumAtranction record);
    
    int getTransactionCount(String transactionArr);
}