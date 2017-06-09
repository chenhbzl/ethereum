package com.blackbrother.util;

import java.io.IOException;
import java.math.BigInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.parity.Parity;

public class Test {
	private static Parity parity = ParityClient.getParity();
	private static Web3j web3j = Web3JClient.getClient();

	public static void sendTransaction() {
		String data = HexUtils.encode("上海1a");
		System.out.println(data);
		Transaction transaction = new Transaction(
				"0x882f967839a9984ca20569d703900aa180b9b548", null, null, null,
				"0xeb97bed0631515013f58b066b8e8561694e06a3b", new BigInteger("100000"),data);
		try {
			EthSendTransaction ethSendTransaction = parity.personalSignAndSendTransaction(transaction, "111111").send();
			System.out.println(ethSendTransaction.getTransactionHash());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		EthTransaction ethTransaction = web3j.ethGetTransactionByHash("0x4495de08cffbd114d2988729be03973a7434902613ca7265469d6df4adebb06e").send();
		System.out.println(HexUtils.decode(ethTransaction.getResult().getInput()));
//		System.out.println(ethTransaction.getTransaction());
		//sendTransaction();
		
	}

}
