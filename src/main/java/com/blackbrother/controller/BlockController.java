package com.blackbrother.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByNumber;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import com.blackbrother.util.HexUtils;
import com.blackbrother.util.Web3JClient;

@RestController
public class BlockController {
    private static Web3j web3j = Web3JClient.getClient();
    
    /**
     * 查询块信息
     * @param request
     * @return
     */
    @RequestMapping(value = "block/getBlock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getBlock(HttpServletRequest request) {
		String blockId = request.getParameter("blockId");
		Map<String, Object> map = new HashMap<>();
		try {
			EthBlock ethBlock = null;
			if(blockId.length()==66){
				ethBlock = web3j.ethGetBlockByHash(blockId, true).send();
			}else{
				ethBlock = web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(new BigInteger(blockId)), true).send();
			}
			
			if(ethBlock!=null){
				Block block = ethBlock.getResult();
				map.put("nonce", Numeric.encodeQuantity(block.getNonce()));
				map.put("extraData", block.getExtraData());
				map.put("size", block.getSize());        
				map.put("hash", block.getHash());
				map.put("miner", block.getMiner());
				map.put("gasLimit", block.getGasLimit());
				map.put("gasUsed", block.getGasUsed());
				map.put("difficulty", block.getDifficulty());
				map.put("number", block.getNumber());
				map.put("parentHash", block.getParentHash());
				map.put("timestamp", block.getTimestamp());
			}else{
				map.put("result", null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
    
    /**
     * 查询块下的交易数量
     * @param request
     * @return
     */
    @RequestMapping(value = "block/getBlockTransactionCount", method = RequestMethod.POST)
	@ResponseBody
	public int getBlockTransactionCount(HttpServletRequest request) {
		String blockId = request.getParameter("blockId");
		int transactionCount = 0;
		try {
			EthGetBlockTransactionCountByNumber ethGetBlockTransactionCountByNumber = web3j.ethGetBlockTransactionCountByNumber(new DefaultBlockParameterNumber(new BigInteger(blockId))).send();
			if(ethGetBlockTransactionCountByNumber!=null){
				transactionCount = ethGetBlockTransactionCountByNumber.getTransactionCount().intValue();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return transactionCount;
	}
    
    /**
     * 根据块数和交易循序号查询交易信息
     * @param request
     * @return
     */
    @RequestMapping(value = "block/getTransactionFromBlock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTransactionFromBlock(HttpServletRequest request) {
		String blockId = request.getParameter("blockId");
		String blockIdx = request.getParameter("blockIdx");
		Map<String, Object> map = new HashMap<>();
		try {
			EthTransaction ethTransaction = web3j.ethGetTransactionByBlockNumberAndIndex(new DefaultBlockParameterNumber(new BigInteger(blockId)), new BigInteger(blockIdx)).send();
			if(ethTransaction!=null){
				Transaction transaction = ethTransaction.getResult();
				map.put("hash", transaction.getHash());
				map.put("input", transaction.getInput());
				map.put("value", transaction.getValue());
			}else{
				map.put("result", null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
    
    /**
     * 根据hash获取某个交易的详细信息
     * @param request
     * @return
     */
    @RequestMapping(value = "block/getTransactionReceipt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTransactionReceipt(HttpServletRequest request) {
		String hash = request.getParameter("hash");
		Map<String, Object> map = new HashMap<>();
		try {
			EthGetTransactionReceipt ethGetTransactionReceipt = web3j.ethGetTransactionReceipt(hash).send();
			if(ethGetTransactionReceipt!=null){
				TransactionReceipt transactionReceipt = ethGetTransactionReceipt.getResult();
				map.put("transactionHash", transactionReceipt.getTransactionHash());
				map.put("from", transactionReceipt.getFrom());
				map.put("to", transactionReceipt.getTo());
				map.put("gasUsed", transactionReceipt.getGasUsed());
				map.put("contractAddress", transactionReceipt.getContractAddress());
			}else{
				map.put("result", null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
    
    /**
     * 获取余额
     * @param request
     * @return
     */
    @RequestMapping(value = "block/getBalance", method = RequestMethod.POST)
	@ResponseBody
	public BigInteger getBalance(HttpServletRequest request) {
		String addressId = request.getParameter("addressId");
		//String blockId = request.getParameter("blockId");//也可以查询某个区块某个地址的余额
		BigInteger balance = null;
		try {
			EthGetBalance ethGetBalance = web3j.ethGetBalance(addressId, DefaultBlockParameterName.LATEST).send();
			if(ethGetBalance!=null){
				System.out.println(ethGetBalance.getBalance());
				balance = ethGetBalance.getBalance();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return balance;
	}
    
    //获取当前块数
    @RequestMapping(value = "block/blockNumber", method = RequestMethod.POST)
	@ResponseBody
	public BigInteger blockNumber() {
		BigInteger blockNumber = null;
		try {
			EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().send();
			if(ethBlockNumber!=null){
				blockNumber = ethBlockNumber.getBlockNumber();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return blockNumber;
	}
    
    /**
     * 查询交易详情
     * @param request
     * @return
     */
    @RequestMapping(value = "block/ethGetTransactionByHash", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ethGetTransactionByHash(HttpServletRequest request) {
		String transactionHash = request.getParameter("txId");
		Map<String, Object> map = new HashMap<>();
		try {
			EthTransaction ethTransaction = web3j.ethGetTransactionByHash(transactionHash).send();
			if(ethTransaction!=null){
				Transaction transaction = ethTransaction.getResult();
				map.put("blockHash", transaction.getBlockHash());
				map.put("blockNumber", transaction.getBlockNumber());
				map.put("from", transaction.getFrom());
				map.put("gas", transaction.getGas());
				map.put("gasPrice", transaction.getGasPrice());
				map.put("hash", transaction.getHash());
				map.put("input", HexUtils.decode(transaction.getInput()));
				map.put("nonce", transaction.getNonce());
				map.put("to", transaction.getTo());
				map.put("transactionIndex", transaction.getTransactionIndex());
				map.put("value", transaction.getValue());
				map.put("", transaction);
			}else{
				map.put("result", null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
