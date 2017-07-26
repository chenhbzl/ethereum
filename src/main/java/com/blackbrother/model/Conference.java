package com.blackbrother.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version 2.2.2.
 */
public final class Conference extends Contract {
    private static final String BINARY = "6060604052341561000c57fe5b5b60008054600160a060020a03191633600160a060020a03161781556101f46003556002555b5b61037f806100426000396000f300606060405236156100725763ffffffff60e060020a60003504166313381fbf811461007457806361203265146100a2578063705099b9146100ce57806383197ef0146100ef578063a977c71e14610101578063cebe09c914610116578063ec3a6f7314610138578063edca914c1461015a575bfe5b341561007c57fe5b610090600160a060020a0360043516610176565b60408051918252519081900360200190f35b34156100aa57fe5b6100b2610188565b60408051600160a060020a039092168252519081900360200190f35b34156100d657fe5b6100ed600160a060020a0360043516602435610197565b005b34156100f757fe5b6100ed61027e565b005b341561010957fe5b6100ed6004356102a6565b005b341561011e57fe5b6100906102ca565b60408051918252519081900360200190f35b341561014057fe5b6100906102d0565b60408051918252519081900360200190f35b6101626102d6565b604080519115158252519081900360200190f35b60016020526000908152604090205481565b600054600160a060020a031681565b6000805433600160a060020a039081169116146101b357610277565b600160a060020a038316600090815260016020526040902054821415610277575030600160a060020a0381163182901061027757604051600160a060020a0384169083156108fc029084906000818181858888f19350505050151561021757610000565b600160a060020a038316600081815260016020908152604080832092909255600280546000190190558151928352820184905280517fbb28353e4598c3b9199101a66e0989549b659a59a54d2c27fbb183f1932c8e6d9281900390910190a15b5b5b505050565b60005433600160a060020a03908116911614156102a357600054600160a060020a0316ff5b5b565b60005433600160a060020a039081169116146102c1576102c7565b60038190555b50565b60035481565b60025481565b60006003546002541015156102ed57506000610350565b600160a060020a033316600081815260016020818152604092839020349081905560028054909301909255825193845283015280517fe1fffcc4923d04b559f4d29a8bfc6cda04eb5b0d3c460751c2402c5c5cc9109c9281900390910190a15060015b905600a165627a7a723058203796d47fa36e4d7b08f240351e5563589cb6549ae5e19a17179e91fd10f0f5ef0029";

    private Conference(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Conference(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<DepositEventResponse> getDepositEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Deposit", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<DepositEventResponse> responses = new ArrayList<DepositEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            DepositEventResponse typedResponse = new DepositEventResponse();
            typedResponse._from = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse._amount = (Uint256) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<DepositEventResponse> depositEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Deposit", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, DepositEventResponse>() {
            @Override
            public DepositEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                DepositEventResponse typedResponse = new DepositEventResponse();
                typedResponse._from = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse._amount = (Uint256) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public List<RefundEventResponse> getRefundEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Refund", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<RefundEventResponse> responses = new ArrayList<RefundEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            RefundEventResponse typedResponse = new RefundEventResponse();
            typedResponse._to = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse._amount = (Uint256) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RefundEventResponse> refundEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Refund", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, RefundEventResponse>() {
            @Override
            public RefundEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                RefundEventResponse typedResponse = new RefundEventResponse();
                typedResponse._to = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse._amount = (Uint256) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public Future<Uint256> registrantsPaid(Address param0) {
        Function function = new Function("registrantsPaid", 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Address> organizer() {
        Function function = new Function("organizer", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> refundTicket(Address recipient, Uint256 amount) {
        Function function = new Function("refundTicket", Arrays.<Type>asList(recipient, amount), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> destroy() {
        Function function = new Function("destroy", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> changeQuota(Uint256 newquota) {
        Function function = new Function("changeQuota", Arrays.<Type>asList(newquota), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Uint256> quota() {
        Function function = new Function("quota", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint256> numRegistrants() {
        Function function = new Function("numRegistrants", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> buyTicket() {
        Function function = new Function("buyTicket", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public static Future<Conference> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Conference.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<Conference> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Conference.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Conference load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Conference(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Conference load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Conference(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class DepositEventResponse {
        public Address _from;

        public Uint256 _amount;
    }

    public static class RefundEventResponse {
        public Address _to;

        public Uint256 _amount;
    }
}
