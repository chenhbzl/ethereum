package com.blackbrother.controller;

import java.io.File;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.parity.Parity;

import com.blackbrother.model.P2ptarget;
import com.blackbrother.util.ParityClient;
import com.blackbrother.util.Web3JClient;

@RestController
public class P2ptargetController {
	private static Web3j web3j = Web3JClient.getClient();
	private static Parity parity = ParityClient.getParity();
	
	private static Credentials getCredentials(int account){
		Credentials credentials = null;
		String password = "111111";
		String pathToWalletFile = "";
		if(account == 1){
		    pathToWalletFile = "E:/blockchain_file/user/UTC--2017-03-24T01-53-19.639609974Z--882f967839a9984ca20569d703900aa180b9b548";
		}else{
			pathToWalletFile = "E:/blockchain_file/user/UTC--2017-03-29T05-33-32.102700340Z--eb97bed0631515013f58b066b8e8561694e06a3b";
		}
		// String 
		File file = new File(pathToWalletFile);
		try {
			credentials = WalletUtils.loadCredentials(password, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return credentials;
	}
	
	@RequestMapping(value = "p2ptarget/deploy", method = RequestMethod.POST)
	@ResponseBody
	public static Map<String, Object> deploy(HttpServletRequest request) {
		String address = "";
		try {
			BigInteger totalAmount = new BigInteger(request.getParameter("totalAmount"));
			P2ptarget contract =  P2ptarget.deploy(web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"), BigInteger.ZERO, new Uint256(totalAmount)).get();
			address = contract.getContractAddress();
			System.out.println(contract.getContractAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("result", address);
		return map;
	}
	
	@RequestMapping(value = "p2ptarget/getTotalAmount", method = RequestMethod.POST)
	@ResponseBody
	public static Map<String, Object>  getTotalAmount(HttpServletRequest request) throws Exception{
		String address = request.getParameter("contractAddress");//合约地址
		P2ptarget contract = P2ptarget.load(address, web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"));
		Map<String, Object> map = new HashMap<>();
		System.out.println(contract.totalAmount().get().getValue().toString());
		map.put("result", contract.totalAmount().get().getValue().toString());
		return map;
	}
	
	@RequestMapping(value = "p2ptarget/buyTicket", method = RequestMethod.POST)
	@ResponseBody
	public static Map<String, Object> buyTicket(HttpServletRequest request) throws InterruptedException, ExecutionException {
		String to = request.getParameter("contractAddress");//合约地址
		P2ptarget contract = P2ptarget.load(to, web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"));
		System.out.println(contract.totalAmount().get().getValue().toString());
		BigInteger totalAmount = contract.totalAmount().get().getValue();//获取标的的剩余金额
		BigInteger value = new BigInteger(request.getParameter("value"));
		BigInteger unit = new BigInteger("1000000000000000000");
		value = value.multiply(unit); //投资人投资的金额
		Map<String, Object> map = new HashMap<>();
		if(totalAmount.compareTo(value)>=0){//剩余金额大于等于投资人投资的金额时，可以投资
			String bidderAddress = "0x882f967839a9984ca20569d703900aa180b9b548";//默认由第一个用户调用合约;
	        Address buyer = new Address(request.getParameter("buyer")); //投资人
			EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(bidderAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
		    BigInteger nonce = ethGetTransactionCount.getTransactionCount();
		    Function function = new Function("buyTicket", Arrays.<Type>asList(buyer), Collections.<TypeReference<?>>emptyList());
			String encodedFunction = FunctionEncoder.encode(function);
			Transaction transaction = Transaction.createFunctionCallTransaction(bidderAddress, nonce,new BigInteger("60000"), new BigInteger("900000"), to, value, encodedFunction);
			EthSendTransaction transactionResponse = parity.personalSignAndSendTransaction(transaction, "111111").sendAsync().get();
			System.out.println(transactionResponse.getResult());
			map.put("result", transactionResponse.getResult());
		}else{
			System.out.println("0");
			map.put("result", 0);
		}
		return map;
	}
	
	@RequestMapping(value = "p2ptarget/getBalance", method = RequestMethod.POST)
	@ResponseBody
	public static Map<String, Object>  getBalance(HttpServletRequest request) throws Exception{
		String address = request.getParameter("contractAddress");//合约地址
		Address buyer = new Address(request.getParameter("buyer"));//投资人
		P2ptarget contract = P2ptarget.load(address, web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"));
		Map<String, Object> map = new HashMap<>();
		System.out.println(contract.getBalance(buyer).get().getValue().toString());
		map.put("result", contract.getBalance(buyer).get().getValue().toString());
		return map;
	}
	
	public static void main(String[] args) throws Exception {
//		deploy();
//		buyTicket();
		//getTotalAmount();
//					getBalance();
	}
}
