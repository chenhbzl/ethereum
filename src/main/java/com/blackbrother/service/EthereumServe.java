package com.blackbrother.service;

import java.util.List;

import com.blackbrother.model.EthereumAtranction;

public interface EthereumServe {
	List<EthereumAtranction> getTransactions();

	int getBlockNumber();

	void updateBlockNumber(int blockNumber);
	
	void addTransactionCount(EthereumAtranction record);
	
	int getTransactionCount(String transactionArr);
	
	void addAddress(String addressid);
}
