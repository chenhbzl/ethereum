package com.blackbrother.model;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
 * <p>Generated with web3j version 2.2.2.
 */
public final class Adder extends Contract {
    private static final String BINARY = "6060604052341561000c57fe5b5b6103cc8061001c6000396000f300606060405263ffffffff60e060020a60003504166306fdde03811461003757806317d7de7c146100c7578063c47f002714610157575bfe5b341561003f57fe5b6100476101af565b60408051602080825283518183015283519192839290830191850190808383821561008d575b80518252602083111561008d57601f19909201916020918201910161006d565b505050905090810190601f1680156100b95780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156100cf57fe5b61004761023d565b60408051602080825283518183015283519192839290830191850190808383821561008d575b80518252602083111561008d57601f19909201916020918201910161006d565b505050905090810190601f1680156100b95780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561015f57fe5b6101ad600480803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437509496506102d695505050505050565b005b6000805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156102355780601f1061020a57610100808354040283529160200191610235565b820191906000526020600020905b81548152906001019060200180831161021857829003601f168201915b505050505081565b6102456102ee565b6000805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156102cb5780601f106102a0576101008083540402835291602001916102cb565b820191906000526020600020905b8154815290600101906020018083116102ae57829003601f168201915b505050505090505b90565b80516102e9906000906020840190610300565b505b50565b60408051602081019091526000815290565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061034157805160ff191683800117855561036e565b8280016001018555821561036e579182015b8281111561036e578251825591602001919060010190610353565b5b5061037b92915061037f565b5090565b6102d391905b8082111561037b5760008155600101610385565b5090565b905600a165627a7a7230582069808f6b2783ff16b2bc751ed68c6fd30e77e13e1a9459040fac47ae567030420029";

    private Adder(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Adder(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public Future<Utf8String> name() {
        Function function = new Function("name", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Utf8String> getName() {
        Function function = new Function("getName", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setName(Utf8String _name) {
        Function function = new Function("setName", Arrays.<Type>asList(_name), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public static Future<Adder> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Adder.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<Adder> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Adder.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Adder load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Adder(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Adder load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Adder(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
