package com.blackbrother.model;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
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
public final class AuctionLib extends Contract {
    private static final String BINARY = "6060604052341561000c57fe5b5b6101118061001c6000396000f300606060405263ffffffff60e060020a600035041663cfb519288114602a578063f088f452146088575bfe5b6076600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284375094965060ab95505050505050565b60408051918252519081900360200190f35b6076600435602435151560443560b6565b60408051918252519081900360200190f35b60208101515b919050565b6040805184815260f860020a8415150260208201526021810183905290519081900360410190205b93925050505600a165627a7a72305820526616729195d0b42cb7ccd8697223d091a8a6bd2c6cca37c04f644224f370e60029";

    private AuctionLib(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private AuctionLib(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public Future<TransactionReceipt> stringToBytes32(Utf8String source) {
        Function function = new Function("stringToBytes32", Arrays.<Type>asList(source), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> getBytes32(Uint256 value, Bool fake, Bytes32 secret) {
        Function function = new Function("getBytes32", Arrays.<Type>asList(value, fake, secret), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public static Future<AuctionLib> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(AuctionLib.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<AuctionLib> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(AuctionLib.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static AuctionLib load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AuctionLib(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static AuctionLib load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AuctionLib(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
