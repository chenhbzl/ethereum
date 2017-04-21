package com.blackbrother.dao;

import com.blackbrother.model.EthereumAddress;

public interface EthereumAddressMapper {
    int deleteByPrimaryKey(String addressid);

    int insert(EthereumAddress record);

    int insertSelective(EthereumAddress record);
    
    void addAddress(String addressid);
    
    int getAllAddress(String addressid);
}