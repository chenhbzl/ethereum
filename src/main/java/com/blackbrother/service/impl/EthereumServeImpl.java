package com.blackbrother.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blackbrother.dao.EthereumAddressMapper;
import com.blackbrother.dao.EthereumAtranctionMapper;
import com.blackbrother.dao.EthereumBlockNumberMapper;
import com.blackbrother.model.EthereumAtranction;
import com.blackbrother.service.EthereumServe;
@Service
public class EthereumServeImpl implements EthereumServe {
	@Autowired
	private EthereumAtranctionMapper ethereumAtranctionMapper;
	@Autowired
	private EthereumBlockNumberMapper ethereumBlockNumberMapper;
	@Autowired
	private EthereumAddressMapper ethereumAddressMapper;

	@Override
	public List<EthereumAtranction> getTransactions() {
		return ethereumAtranctionMapper.getTransactions();
	}

	@Override
	public int getBlockNumber() {
		return ethereumBlockNumberMapper.getBlockNumber();
	}

	@Override
	public void updateBlockNumber(int blockNumber) {
		ethereumBlockNumberMapper.updateBlockNumber(blockNumber);
	}

	@Override
	public void addTransactionCount(EthereumAtranction record) {
		ethereumAtranctionMapper.addTransactionCount(record);
	}

	@Override
	public int getTransactionCount(String transactionArr) {
		return ethereumAtranctionMapper.getTransactionCount(transactionArr);
	}

	@Override
	public void addAddress(String addressid) {
		ethereumAddressMapper.addAddress(addressid);
	}
}
