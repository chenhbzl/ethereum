package com.blackbrother.controller;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.parity.Parity;

import com.blackbrother.model.AuctionUtil;
import com.blackbrother.model.BlindAuction;
import com.blackbrother.util.ParityClient;
import com.blackbrother.util.Web3JClient;
@RestController
public class BlindAuctionController {
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
	
	@RequestMapping(value = "blind/deploy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deploy(HttpServletRequest request) {
		String address = "";
		try {
			BigInteger biddingEnd = new BigInteger(request.getParameter("biddingEnd"));
			BigInteger revealEnd = new BigInteger(request.getParameter("revealEnd"));
			String beneficiary = request.getParameter("beneficiary");
			BlindAuction contract = BlindAuction.deploy(web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"), BigInteger.ZERO,new Uint256(biddingEnd.multiply(new BigInteger("60"))), new Uint256(revealEnd.multiply(new BigInteger("60"))), new Address(beneficiary)).get();
			address = contract.getContractAddress();
			System.out.println(contract.getContractAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("result", address);
		return map;
	}


    //出价，同一个地址可以多次出价
	@RequestMapping(value = "blind/bids", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> bids(HttpServletRequest request) throws InterruptedException, ExecutionException {
		String bidderAddress = request.getParameter("bidderAddress");//出价者  "0xeb97bed0631515013f58b066b8e8561694e06a3b";
		BigInteger bid = new BigInteger(request.getParameter("bid"));//new BigInteger(values);
		boolean fake = new Boolean(request.getParameter("fake"));//new Boolean("false");
		String unencrypted = request.getParameter("unencrypted");//"test";
        String to = request.getParameter("contractAddress");
		EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(bidderAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
	    BigInteger nonce = ethGetTransactionCount.getTransactionCount();
	    
	    AuctionUtil contract = AuctionUtil.load("0x819c460ac6c7fd8e05ad4231156431b02ed2cb3f", web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"));
	    Function function = new Function("bids", Arrays.<Type>asList(contract.getBytes32(new Uint256(bid), new Bool(fake), contract.stringToBytes32(new Utf8String(unencrypted)).get()).get()), Collections.<TypeReference<?>>emptyList());
		String encodedFunction = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createFunctionCallTransaction(bidderAddress, nonce,new BigInteger("60000"), new BigInteger("900000"), to, bid, encodedFunction);
		EthSendTransaction transactionResponse = parity.personalSignAndSendTransaction(transaction, "111111").sendAsync().get();
		Map<String, Object> map = new HashMap<>();
		map.put("result", transactionResponse.getResult());
		return map;
	}
	
    //拍卖结束后，除了最高价值外的所有正常出价会被退款
	@RequestMapping(value = "blind/reveal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reveal(HttpServletRequest request) throws Exception{
		String to = request.getParameter("contractAddress");//		String to = "0xe113ca364e449dc5d0d5c21a5ac57dbcf4c1fe39";//request.getParameter("address");
		String[] bids = request.getParameter("bids").split(",");//{"50","100"};
		List<Uint256> valuesList = new ArrayList<>();
		for (String value : bids) {
			valuesList.add(new Uint256(new BigInteger(value)));
		}
		String[] fakes = request.getParameter("fakes").split(",");//{"false","false"};
		List<Bool> fakeList = new ArrayList<>();
		for (String fake : fakes) {
			fakeList.add(new Bool(new Boolean(fake)));
		}
		
		AuctionUtil auctionContract = AuctionUtil.load("0x819c460ac6c7fd8e05ad4231156431b02ed2cb3f", web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"));
		String[] unencrypteds = request.getParameter("unencrypteds").split(",");
		List<Bytes32> secretList = new ArrayList<>(); 
		for (String unencrypted : unencrypteds) {
			Bytes32 _secret = auctionContract.stringToBytes32(new Utf8String(unencrypted)).get();
			secretList.add(_secret);
		}
		
		BlindAuction contract = BlindAuction.load(to, web3j, getCredentials(2), new BigInteger("60000"), new BigInteger("900000"));
		Future<TransactionReceipt> refundTicketResult = contract.reveal(new DynamicArray<Uint256>(valuesList), new DynamicArray<Bool>(fakeList), new DynamicArray<Bytes32>(secretList));
		Map<String, Object> map = new HashMap<>();
		map.put("result", refundTicketResult.get().getBlockHash());
		return map;
	}
	
	//退款
	@RequestMapping(value = "blind/withdraw", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> withdraw(HttpServletRequest request) throws Exception{
		String address = request.getParameter("contractAddress");
		BlindAuction contract = BlindAuction.load(address, web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"));
		TransactionReceipt  result = contract.withdraw(new BigInteger("0")).get();
		String blockNumber = result.getBlockNumber().toString();
		System.out.println(blockNumber);
		Map<String, Object> map = new HashMap<>();
		map.put("result", blockNumber);
		return map;
	}

	//
	@RequestMapping(value = "blind/auctionEnd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auctionEnd(HttpServletRequest request) throws Exception{
		String address = request.getParameter("contractAddress");
		BlindAuction contract = BlindAuction.load(address, web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"));
		TransactionReceipt  result = contract.auctionEnd().get();
		String blockNumber = result.getBlockNumber().toString();
		System.out.println(blockNumber);
		Map<String, Object> map = new HashMap<>();
		map.put("result", blockNumber);
		return map;
	}
	
	@RequestMapping(value = "blind/getBalance", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  getBalance(HttpServletRequest request) throws Exception{
		String address = request.getParameter("contractAddress");
		BlindAuction contract = BlindAuction.load(address, web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"));
		Map<String, Object> map = new HashMap<>();
		map.put("result", contract.getBalance().get().getValue().toString());
		return map;
	}
	 
	public static void main(String[] args) throws Exception{
//		String a1 = deploy();
//		String a2 = bids("100","0x59c23a357038a20ce180ffd5056cf754206a1bd7");
//		System.out.println(a2);
//		String a3 = reveal("0x59c23a357038a20ce180ffd5056cf754206a1bd7");
//		System.out.println(a3);
//		String a4 = withdraw("0x59c23a357038a20ce180ffd5056cf754206a1bd7");
//		System.out.println(a4);
//		String a5 = auctionEnd("0x59c23a357038a20ce180ffd5056cf754206a1bd7");
//		System.out.println(a5);
		
		BlindAuction contract = BlindAuction.load("0xeb666eeffbc965bbaad6f21bda04b43f56906160", web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"));
		System.out.println(new Address(contract.highestBidder().get().getValue()));
		System.out.println(contract.highestBid().get().getValue());
		System.out.println(contract.ended().get().getValue());
//		
//		AuctionUtil auctionContract = AuctionUtil.load("0x819c460ac6c7fd8e05ad4231156431b02ed2cb3f", web3j, getCredentials(), new BigInteger("60000"), new BigInteger("900000"));
//		System.out.println(new String(auctionContract.getBytes32(new Uint256(new BigInteger("25")), new Bool(false), auctionContract.stringToBytes32(new Utf8String("test")).get()).get().getValue()));
	}
}
