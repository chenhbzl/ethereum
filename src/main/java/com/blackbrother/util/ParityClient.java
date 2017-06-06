package com.blackbrother.util;

import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.parity.Parity;

public class ParityClient {
	private static String ip = "http://192.168.9.44:8545";

	private ParityClient() {
	}

	private volatile static Parity parity;

	public static Parity getParity() {
		if (parity == null) {
			synchronized (Parity.class) {
				if (parity == null) {
					parity = Parity.build(new HttpService(ip));
				}
			}
		}
		return parity;
	}
}
