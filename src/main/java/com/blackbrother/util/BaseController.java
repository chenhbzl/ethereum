package com.blackbrother.util;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.parity.Parity;

 
public class BaseController {
	private static Parity parity = ParityClient.getParity();
	public static Web3j web3j = Web3JClient.getClient();
}
