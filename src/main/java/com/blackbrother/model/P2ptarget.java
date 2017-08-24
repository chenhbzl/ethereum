package com.blackbrother.model;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version 2.3.0.
 */
public final class P2ptarget extends Contract {
    private static final String BINARY = "6060604052341561000c57fe5b6040516020806101e383398101604052515b670de0b6b3a764000081026000555b505b6101a58061003e6000396000f300606060405263ffffffff60e060020a60003504166313381fbf81146100425780631a39d8ef14610070578063a5f8cdbb14610092578063f8b2cb4f146100ba575bfe5b341561004a57fe5b61005e600160a060020a03600435166100e8565b60408051918252519081900360200190f35b341561007857fe5b61005e6100fa565b60408051918252519081900360200190f35b6100a6600160a060020a0360043516610100565b604080519115158252519081900360200190f35b34156100c257fe5b61005e600160a060020a0360043516610168565b60408051918252519081900360200190f35b60016020526000908152604090205481565b60005481565b600060005434111561011457506000610163565b6000805434908190038255600160a060020a0384168083526001602052604080842083905551909282156108fc02929190818181858888f19350505050151561015f57506000610163565b5060015b919050565b600160a060020a038116315b9190505600a165627a7a72305820d164190a1cf6334d1a0865a806172d607efd0d89a29c24bc74ac4fea54a72e960029";

    private P2ptarget(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private P2ptarget(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public Future<Uint256> registrantsPaid(Address param0) {
        Function function = new Function("registrantsPaid", 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint256> totalAmount() {
        Function function = new Function("totalAmount", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> buyTicket(Address buyer, BigInteger weiValue) {
        Function function = new Function("buyTicket", Arrays.<Type>asList(buyer), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function, weiValue);
    }

    public Future<Uint256> getBalance(Address buyer) {
        Function function = new Function("getBalance", 
                Arrays.<Type>asList(buyer), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<P2ptarget> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, Uint256 _totalAmount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_totalAmount));
        return deployAsync(P2ptarget.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static Future<P2ptarget> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, Uint256 _totalAmount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_totalAmount));
        return deployAsync(P2ptarget.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static P2ptarget load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new P2ptarget(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static P2ptarget load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new P2ptarget(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
