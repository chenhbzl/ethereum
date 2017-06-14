package com.blackbrother.util;

import java.io.IOException;
import java.math.BigInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthMining;
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
				"0x882f967839a9984ca20569d703900aa180b9b548", null, new BigInteger("20000000000"), new BigInteger("21545"),
				"0xeb97bed0631515013f58b066b8e8561694e06a3b", new BigInteger("100000"),data);
		try {
			EthSendTransaction ethSendTransaction = parity.personalSignAndSendTransaction(transaction, "111111").send();
			System.out.println(ethSendTransaction.getTransactionHash());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
//		EthTransaction ethTransaction = web3j.ethGetTransactionByHash("0x5782f44eea77bb9d0fd2afe363ed47bc4daf1058f9ca461ab195ced5d015b1e2").send();
//		System.out.println(HexUtils.decode(ethTransaction.getResult().getInput()));
//		System.out.println(ethTransaction.getTransaction());
//		sendTransaction();
//		String data = HexUtils.encode("上海1a");
//		Transaction transaction = new Transaction(
//				"0x882f967839a9984ca20569d703900aa180b9b548", null, null, null,
//				"0xeb97bed0631515013f58b066b8e8561694e06a3b", new BigInteger("50000000000000"),data);
//				EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(transaction).send();
//				System.out.println(ethEstimateGas.getAmountUsed());
//				System.out.println(web3j.ethGasPrice().send().getGasPrice());
//		int a = (50005000 - 49958000) - (50010000 - 50005000);
//		int b = 21000 * 2;
//		System.out.println(a);
//		System.out.println(b);
		EthMining ethMining = web3j.ethMining().send();

		System.out.println(ethMining.getResult());
//		EthBlock ethBlock = web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(new BigInteger("15037")), true).send();
//		System.out.println(ethBlock.getResult().getNonce());
//		EthCoinbase ethCoinbase = parity.ethCoinbase().send();
//		System.out.println(ethCoinbase.getResult());
	}

}
