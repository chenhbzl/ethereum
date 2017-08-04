package com.blackbrother.controller;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.parity.Parity;

import com.blackbrother.model.AuctionUtil;
import com.blackbrother.model.BlindAuction;
import com.blackbrother.util.ParityClient;
import com.blackbrother.util.Web3JClient;

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
	public static String deploy() {
		String address = "";
		try {
			BlindAuction contract = BlindAuction.deploy(web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"), BigInteger.ZERO,new Uint256(300), new Uint256(300), new Address("0x56e8e462b874cc88f4a0b3e458b1bb4ba8429738")).get();
			//	BlindAuction2 contract = BlindAuction2.deploy(web3j, getCredentials(), new BigInteger("60000"), new BigInteger("900000"),BigInteger.ZERO).get();
			address = contract.getContractAddress();
			System.out.println(contract.getContractAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return address;
	}


    //出价，同一个地址可以多次出价
	public static String bids(String values,String to/*HttpServletRequest request*/) throws InterruptedException, ExecutionException {
		String address = "0xeb97bed0631515013f58b066b8e8561694e06a3b";//request.getParameter("address");
		BigInteger _values = new BigInteger(values);//new BigInteger(request.getParameter("_values"));
		boolean _fake = new Boolean("false");//new Boolean(request.getParameter("_fake"));
		String unencrypted = "test";//request.getParameter("unencrypted");
//		BlindAuction contract = BlindAuction.load(address, web3j, credentials, new BigInteger("60000"), new BigInteger("900000"));
		EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount("0xeb97bed0631515013f58b066b8e8561694e06a3b", DefaultBlockParameterName.LATEST).sendAsync().get();
	    BigInteger nonce = ethGetTransactionCount.getTransactionCount();
	    
	    AuctionUtil contract = AuctionUtil.load("0x819c460ac6c7fd8e05ad4231156431b02ed2cb3f", web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"));
		
	    Function function = new Function("bids", Arrays.<Type>asList(contract.getBytes32(new Uint256(_values), new Bool(_fake), contract.stringToBytes32(new Utf8String(unencrypted)).get()).get()), Collections.<TypeReference<?>>emptyList());
		String encodedFunction = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createFunctionCallTransaction(address, nonce,new BigInteger("60000"), new BigInteger("900000"), to, _values, encodedFunction);
		org.web3j.protocol.core.methods.response.EthSendTransaction transactionResponse = parity.personalSignAndSendTransaction(transaction, "111111").sendAsync().get();
		String hash = transactionResponse.getResult();
		System.out.println(transactionResponse.getResult());
		return hash;
	}
	
    //拍卖结束后，除了最高价值外的所有正常出价会被退款
	public static String reveal(String to/*HttpServletRequest request*/) throws Exception{
//		String to = "0xe113ca364e449dc5d0d5c21a5ac57dbcf4c1fe39";//request.getParameter("address");
		String[] values = {"50","100"};//request.getParameter("values").split(",");
		List<Uint256> valuesList = new ArrayList<>();
		for (String value : values) {
			valuesList.add(new Uint256(new BigInteger(value)));
		}
		String[] fakes = {"false","false"};//request.getParameter("_fake").split(",");
		List<Bool> fakeList = new ArrayList<>();
		for (String fake : fakes) {
			fakeList.add(new Bool(new Boolean(fake)));
		}
		//String unencrypted = "test-test";//request.getParameter("unencrypted");//"多个出价记录的描述-隔开"

		AuctionUtil auctionContract = AuctionUtil.load("0x819c460ac6c7fd8e05ad4231156431b02ed2cb3f", web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"));
		Bytes32 _secret = auctionContract.stringToBytes32(new Utf8String("test")).get();
		List<Bytes32> secretList = new ArrayList<>(); 
		secretList.add(_secret);
		secretList.add(_secret);
		
		BlindAuction contract = BlindAuction.load(to, web3j, getCredentials(2), new BigInteger("60000"), new BigInteger("900000"));
		Future<TransactionReceipt> refundTicketResult = contract.reveal(new DynamicArray<Uint256>(valuesList), new DynamicArray<Bool>(fakeList), new DynamicArray<Bytes32>(secretList));
		System.out.println(refundTicketResult.get());
		return null;
	}
	
	//退款
	public static String withdraw(String address/*HttpServletRequest request*/) throws Exception{
		//String address = request.getParameter("address");
		BlindAuction contract = BlindAuction.load(address, web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"));
		TransactionReceipt  result = contract.withdraw(new BigInteger("0")).get();
		String blockNumber = result.getBlockNumber().toString();
		System.out.println(blockNumber);
		return blockNumber;
	}

	//
	public static String auctionEnd(String address/*HttpServletRequest request*/) throws Exception{
		//String address = request.getParameter("address");
		BlindAuction contract = BlindAuction.load(address, web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"));
		TransactionReceipt  result = contract.auctionEnd().get();
		String blockNumber = result.getBlockNumber().toString();
		System.out.println(blockNumber);
		return blockNumber;
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
		
		BlindAuction contract = BlindAuction.load("0x59c23a357038a20ce180ffd5056cf754206a1bd7", web3j, getCredentials(1), new BigInteger("60000"), new BigInteger("900000"));
		System.out.println(new Address(contract.highestBidder().get().getValue()));
		System.out.println(contract.highestBid().get().getValue());
		System.out.println(contract.ended().get().getValue());
		System.out.println(new Address(contract.a().get().getValue()));
		System.out.println(contract.b().get().getValue());
//		
//		AuctionUtil auctionContract = AuctionUtil.load("0x819c460ac6c7fd8e05ad4231156431b02ed2cb3f", web3j, getCredentials(), new BigInteger("60000"), new BigInteger("900000"));
//		System.out.println(new String(auctionContract.getBytes32(new Uint256(new BigInteger("25")), new Bool(false), auctionContract.stringToBytes32(new Utf8String("test")).get()).get().getValue()));
	}
}
