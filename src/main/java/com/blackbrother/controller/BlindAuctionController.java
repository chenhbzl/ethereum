package com.blackbrother.controller;

import java.io.File;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;

import com.blackbrother.model.BlindAuction;
import com.blackbrother.util.Web3JClient;

public class BlindAuctionController {
	private static Web3j web3j = Web3JClient.getClient();

	private static Credentials credentials = null;
	private static String address = "";
	public static void deployBlindAuction() {
		String password = "111111";
		String pathToWalletFile = "/home/fanjianbin/eth/chain/keystore/UTC--2017-03-24T01-53-19.639609974Z--882f967839a9984ca20569d703900aa180b9b548";
		// String pathToWalletFile =
		// "/home/fanjianbin/eth/chain/keystore/UTC--2017-03-29T05-33-32.102700340Z--eb97bed0631515013f58b066b8e8561694e06a3b";
		File file = new File(pathToWalletFile);

		try {
			credentials = WalletUtils.loadCredentials(password, file);
			BlindAuction contract = BlindAuction
					.deploy(web3j, credentials, new BigInteger("60000"), new BigInteger("900000"), BigInteger.ZERO,
							new Uint256(50), new Uint256(50), new Address("0x882f967839a9984ca20569d703900aa180b9b548"))
					.get();
			address = contract.getContractAddress();
			System.out.println(contract.getContractAddress());
			
			credentials = WalletUtils.loadCredentials(password, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


 
	public static void bids() throws InterruptedException, ExecutionException {
		BlindAuction contract = BlindAuction.load(address, web3j, credentials, new BigInteger("60000"), new BigInteger("900000"));
		contract.bids(new Uint256(25), new Bool(false));
	}
	

}
