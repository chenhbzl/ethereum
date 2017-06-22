package com.blackbrother.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthSign;
import org.web3j.protocol.core.methods.response.Web3Sha3;
import org.web3j.protocol.parity.Parity;
import org.web3j.protocol.parity.methods.response.NewAccountIdentifier;
import org.web3j.protocol.parity.methods.response.PersonalEcRecover;
import org.web3j.protocol.parity.methods.response.PersonalUnlockAccount;

import com.blackbrother.model.AccountInfo;
import com.blackbrother.model.EthereumAtranction;
import com.blackbrother.util.HexUtils;
import com.blackbrother.util.ParityClient;
import com.blackbrother.util.Web3JClient;

import rx.Observable;
import rx.Subscription;

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
		
		/*String message = request.getParameter("message");
		Transaction transaction = new Transaction(address1, null, null, null,address2, value.toBigInteger(),message);
		message:可以保存交易的其他信息
		EthTransaction ethTransaction = web3j.ethGetTransactionByHash("0x4495de08cffbd114d2988729be03973a7434902613ca7265469d6df4adebb06e").send();
		System.out.println(HexUtils.decode(ethTransaction.getResult().getInput()));  message保存到input
		*/
		
		/*EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(transaction).send();//根据上面的transaction获取gasLimit
		ethEstimateGas.getAmountUsed(); //获取gasLimit
        web3j.ethGasPrice().send().getGasPrice(); //获取gasPrice  这两个可以用户创建Transaction*/		
		try {
			EthSendTransaction ethSendTransaction = parity.personalSignAndSendTransaction(transaction, password).send();
			if(ethSendTransaction!=null){
				map.put("result", ethSendTransaction.getTransactionHash());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	//转换成十六进制后数据生成哈希串
    @RequestMapping(value = "ecrecover/getSha3", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSha3(HttpServletRequest request) {
		String message = request.getParameter("message");
		Web3Sha3 web3Sha3 = null;
		try {
			web3Sha3 = web3j.web3Sha3(HexUtils.encode(message)).send();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("web3Sha3", web3Sha3.getResult());
		return map;
	}
	
    
    @RequestMapping(value = "ecrecover/getSignedData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSignedData1(HttpServletRequest request) {
		String address = request.getParameter("address");
		String sha3Result = request.getParameter("sha3Result");
		EthSign ethSign = null;
		try {
			ethSign = web3j.ethSign(address, sha3Result).send();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("signature", ethSign.getSignature());
		return map;
	}
	
    
    @RequestMapping(value = "ecrecover/getResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getResult(HttpServletRequest request) {
		String sha3Result = request.getParameter("sha3Result");
		String signedDataResult = request.getParameter("signedDataResult");
		PersonalEcRecover personalEcRecover = null;
		try {
			personalEcRecover = parity.personalEcRecover(sha3Result, signedDataResult).send();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("personalEcRecover", personalEcRecover.getResult());
		return map;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
//		PersonalUnlockAccount personalUnlockAccount = parity.personalUnlockAccount("0x56e8e462b874cc88f4a0b3e458b1bb4ba8429738", "123456", new BigInteger("10000")).send();
//		if(personalUnlockAccount!=null){
//			System.out.println(personalUnlockAccount.getResult());
//		}
		
//		RawTransaction rawTransaction = RawTransaction.createContractTransaction(null, null, null, null,
//				"0x6060604052341561000c57fe5b5b33600060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600160016000600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600001819055505b5b610bd6806100c96000396000f300606060405236156100a2576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630121b93f146100a4578063013cf08b146100d85780632e4176cf1461011b578063429b92bf1461016d5780635c19a95c146101a9578063609ff1bd146101f7578063745fd751146102335780639e7b8d61146102a5578063a3ec138d146102f3578063e503bab614610382575bfe5b34156100ac57fe5b6100c260048080359060200190919050506103f8565b6040518082815260200191505060405180910390f35b34156100e057fe5b6100f660048080359060200190919050506104c4565b6040518083600019166000191681526020018281526020019250505060405180910390f35b341561012357fe5b61012b6104f8565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561017557fe5b61018b600480803590602001909190505061051e565b60405180826000191660001916815260200191505060405180910390f35b34156101b157fe5b6101dd600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190505061054d565b604051808215151515815260200191505060405180910390f35b34156101ff57fe5b6102076108a2565b604051808481526020018381526020018260001916600019168152602001935050505060405180910390f35b341561023b57fe5b61028b600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610958565b604051808215151515815260200191505060405180910390f35b34156102ad57fe5b6102d9600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506109d4565b604051808215151515815260200191505060405180910390f35b34156102fb57fe5b610327600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610adc565b60405180858152602001841515151581526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200194505050505060405180910390f35b341561038a57fe5b6103da600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610b39565b60405180826000191660001916815260200191505060405180910390f35b60006000600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002090508060010160009054906101000a900460ff161561045a5760006000fd5b60018160010160006101000a81548160ff021916908315150217905550828160020181905550806000015460028481548110151561049457fe5b906000526020600020906002020160005b5060010160008282540192505081905550806000015491505b50919050565b6002818154811015156104d357fe5b906000526020600020906002020160005b915090508060000154908060010154905082565b600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600060028281548110151561052f57fe5b906000526020600020906002020160005b506000015490505b919050565b600060006000600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002091508160010160009054906101000a900460ff16156105b15760006000fd5b5b600073ffffffffffffffffffffffffffffffffffffffff16600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141580156106df57503373ffffffffffffffffffffffffffffffffffffffff16600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614155b1561074e57600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1693506105b2565b3373ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff1614156107885760006000fd5b60018260010160006101000a81548160ff021916908315150217905550838260010160016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002090508060010160009054906101000a900460ff161561087f5781600001546002826002015481548110151561085857fe5b906000526020600020906002020160005b5060010160008282540192505081905550610896565b816000015481600001600082825401925050819055505b600192505b5050919050565b6000600060006000600090505b60028054905081101561094857826002828154811015156108cc57fe5b906000526020600020906002020160005b5060010154111561093a576002818154811015156108f757fe5b906000526020600020906002020160005b5060010154925080935060028181548110151561092157fe5b906000526020600020906002020160005b506000015491505b5b80806001019150506108af565b8383839350935093505b50909192565b6000600061096583610b39565b90506002805480600101828161097b9190610b48565b916000526020600020906002020160005b60406040519081016040528085600019168152602001600081525090919091506000820151816000019060001916905560208201518160010155505050600191505b50919050565b6000600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141580610a7f5750600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160009054906101000a900460ff165b15610a8a5760006000fd5b6001600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000181905550600190505b919050565b60016020528060005260406000206000915090508060000154908060010160009054906101000a900460ff16908060010160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060020154905084565b6000602082015190505b919050565b815481835581811511610b7557600202816002028360005260206000209182019101610b749190610b7a565b5b505050565b610ba791905b80821115610ba35760006000820160009055600182016000905550600201610b80565b5090565b905600a165627a7a72305820a1b15de8c40d89dc284ec27751062177721c5b3e6ece942c0be31e2cb933baee0029");
//		EthGetTransactionReceipt.TransactionReceipt transactionReceipt = sendTransactionReceiptRequest("a");
//
//		Optional<String> contractAddressOptional = transactionReceipt.getContractAddress();
		int COUNT = 2;

		CountDownLatch countDownLatch = new CountDownLatch(COUNT);

		System.out.println("Waiting for " + COUNT + " transactions...");
		Observable<BigInteger> transactionValue = web3j.transactionObservable()
		        .take(COUNT)
		        .map(org.web3j.protocol.core.methods.response.Transaction::getValue)
		        .reduce(BigInteger.ZERO, BigInteger::add);

		Subscription subscription = transactionValue.subscribe(total -> {
		    System.out.println("Transaction value: " +
		            new BigDecimal(total) + " Ether");
		    countDownLatch.countDown();
		}, Throwable::printStackTrace);

		countDownLatch.await(1, TimeUnit.MINUTES);
		subscription.unsubscribe();
		
//		Subscription subscription1 = web3j.blockObservable(false).subscribe(block -> {
//		    System.out.println("Sweet, block number " + block.getBlock().getNumber() + " has just been created");
//		}, Throwable::printStackTrace);
//
//		TimeUnit.MINUTES.sleep(2);
//		subscription1.unsubscribe();
	}
}
