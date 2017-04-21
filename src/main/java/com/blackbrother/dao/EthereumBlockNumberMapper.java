package com.blackbrother.dao;

import com.blackbrother.model.EthereumBlockNumber;

public interface EthereumBlockNumberMapper {
    int insert(EthereumBlockNumber record);

    int insertSelective(EthereumBlockNumber record);
    
    int getBlockNumber();
    
    void updateBlockNumber(int blockNumber);
}