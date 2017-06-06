package com.blackbrother.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.parity.Parity;
import org.web3j.protocol.parity.methods.response.NewAccountIdentifier;
import org.web3j.protocol.parity.methods.response.PersonalUnlockAccount;

import com.blackbrother.model.AccountInfo;
import com.blackbrother.model.EthereumAtranction;
import com.blackbrother.util.JsonRpc;
import com.blackbrother.util.ParityClient;
import com.blackbrother.util.Web3JClient;

@RestController
public class AccountController {
	private static  Parity parity = ParityClient.getParity();
    private static Web3j web3j = Web3JClient.getClient();
    
	@RequestMapping(value = "account/getAllAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAllAccount() {
		List<String> accounts = null;
		try {
			accounts = web3j.ethAccounts().send().getAccounts();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	@RequestMapping(value = "account/newAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createAccount(String accountName,String password,AccountInfo accountInfo){
		String accountId = "";
		try {
			NewAccountIdentifier newAccountIdentifier = parity.personalNewAccount(password).send();
			if(newAccountIdentifier!=null){
				accountId = newAccountIdentifier.getAccountId();
				parity.personalSetAccountName(accountId, accountName);
				
				Map<String,Object> account = new HashMap<String,Object>();
				account.put(accountId, accountInfo);
				parity.personalSetAccountMeta(accountId, account);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("result", accountId);
		return map;
	}
	
	@RequestMapping(value = "account/unlockAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unlockAccount(HttpServletRequest request) {
		String address = request.getParameter("address");
		String password = request.getParameter("password");
		boolean flag = false;
		try {
			PersonalUnlockAccount personalUnlockAccount = parity.personalUnlockAccount(address, password, new BigInteger("10000")).send();
			if(personalUnlockAccount!=null){
				flag = personalUnlockAccount.getResult();
				System.out.println(personalUnlockAccount.getResult());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("result", flag);
		return map;
	}
	
	@RequestMapping(value = "account/sendTransaction", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendTransaction(HttpServletRequest request) {
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		BigDecimal value = new BigDecimal(request.getParameter("value"));
		String password = request.getParameter("password");
		Map<String, Object> map = new HashMap<>();
		Transaction transaction = Transaction.createEtherTransaction(address1, null, null, null, address2, value.toBigInteger());
		try {
			EthSendTransaction ethSendTransaction = ParityClient.getParity().personalSignAndSendTransaction(transaction, password).send();
			if(ethSendTransaction!=null){
				map.put("result", ethSendTransaction.getTransactionHash());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static void main(String[] args) throws IOException {
		PersonalUnlockAccount personalUnlockAccount = parity.personalUnlockAccount("0x56e8e462b874cc88f4a0b3e458b1bb4ba8429738", "123456", new BigInteger("10000")).send();
		if(personalUnlockAccount!=null){
			System.out.println(personalUnlockAccount.getResult());
		}
	}
}
