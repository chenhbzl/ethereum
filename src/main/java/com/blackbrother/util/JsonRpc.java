package com.blackbrother.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

/**
 * @author zhuzhisheng
 * @Description
 * @date on 2016/12/31.
 */
public class JsonRpc {

	public static void main(String[] args) {

		// TODO 多个参数时使用例子
		// String[] temp = new String[]{"0x12341234"};
		// Object[] params = new Object[]{"0x1", "0x2",
		// "0x8888f1f195afa192cfee860698584c030f4c9db1", temp};

		// 密码为123456
		Object[] params = new Object[] { "123456" };
		String methodName = "personal_newAccount";
		try {
			JsonRpcHttpClient client = new JsonRpcHttpClient(new URL("http://192.168.9.44:8545"));
			// Object newAddress = client.invoke(methodName, params,
			// Object.class);
			// System.out.println(newAddress);

			methodName = "personal_listAccounts";
			params = new Object[0];
			List<String> accounts = (ArrayList<String>) client.invoke(methodName, params, Object.class);
			System.out.println(accounts);

			methodName = "personal_unlockAccount";
			params = new Object[] { accounts.get(0), "111111", 10000 };
			boolean flag = (boolean) client.invoke(methodName, params, Object.class);
			System.out.println(flag);

			methodName = "personal_sign";
			params = new Object[] { "0xdeadbeaf", accounts.get(0), "111111" };
			String signature = (String) client.invoke(methodName, params, Object.class);
			System.out.println(signature);

			methodName = "personal_ecRecover";
			params = new Object[] { "0xdeadbeaf", signature };
			String address = (String) client.invoke(methodName, params, Object.class);
			System.out.println(address);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	public static Object JsonRpcEthereum(String methodName,Object[] params) {
		Object object = null;
		try {
			JsonRpcHttpClient client = new JsonRpcHttpClient(new URL("http://192.168.9.44:8545"));
			object = client.invoke(methodName, params, Object.class);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		return object;
	}
}