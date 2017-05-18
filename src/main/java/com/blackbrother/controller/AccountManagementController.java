package com.blackbrother.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blackbrother.model.EthereumAtranction;
import com.blackbrother.util.JsonRpc;

@RestController
public class AccountManagementController {
	@RequestMapping(value = "etf/getAllAccount", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, Object> getAllAccount() {
		String methodName = "personal_listAccounts";
		Object[] params = new Object[0];
		List<String> accounts = (ArrayList<String>) JsonRpc.JsonRpcEthereum(methodName, params);
		List<Object> objlist = new ArrayList<Object>();
		for (String str : accounts) {
			EthereumAtranction atb = new EthereumAtranction();
			atb.setAddress(str);
			objlist.add(atb);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("allAddress", objlist);
		return map;
	}
	
	@RequestMapping(value = "etf/newAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newAccount(HttpServletRequest request) {
		String newPassword = request.getParameter("newPassword");
		String methodName = "personal_newAccount";
		Object[] params = new Object[] {newPassword};
		String address = (String)JsonRpc.JsonRpcEthereum(methodName, params);
		Map<String, Object> map = new HashMap<>();
		map.put("result", address);
		return map;
	}
	
	@RequestMapping(value = "etf/unlockAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unlockAccount(HttpServletRequest request) {
		String address = request.getParameter("address");
		String password = request.getParameter("password");
		String methodName = "personal_unlockAccount";
		Object[] params = new Object[] {address, password, 10000};
		boolean flag = (boolean) JsonRpc.JsonRpcEthereum(methodName, params);
		Map<String, Object> map = new HashMap<>();
		map.put("result", flag);
		return map;
	}
	
	public static void main(String[] args) {
		String methodName = "personal_unlockAccount";
		Object[] params = new Object[] { "0x882f967839a9984ca20569d703900aa180b9b548", "111111", 10000 };
		boolean flag = (boolean) JsonRpc.JsonRpcEthereum(methodName, params);
		System.out.println(flag);
		System.out.println("0xdcac02c0ab7ada9b6ff17fb002ce61af48d36108".length());
	}
}
