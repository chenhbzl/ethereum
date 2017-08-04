package com.blackbrother.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
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
 * <p>Generated with web3j version 2.3.0.
 */
public final class BlindAuction extends Contract {
    private static final String BINARY = "6060604052341561000c57fe5b6040516060806109258339810160409081528151602083015191909201515b60008054600160a060020a031916600160a060020a0383161790554260018190558301600281905582016003555b5050505b6108b98061006c6000396000f300606060405236156100b45763ffffffff60e060020a60003504166301495c1c81146100c95780630dbe671f1461010157806312fa6feb1461012d5780632a24f46c1461015157806338af3eed146101755780633ccfd60b146101a1578063423b217f146101bd5780634df7e3d0146101df5780634f245ef7146102015780638f98eeda14610223578063900f080a1461023057806391f90157146102f5578063a6e6647714610321578063d57bde7914610343575b34156100bc57fe5b6100c75b610000565b565b005b34156100d157fe5b6100e8600160a060020a0360043516602435610365565b6040805192835260208301919091528051918290030190f35b341561010957fe5b6101116103a2565b60408051600160a060020a039092168252519081900360200190f35b341561013557fe5b61013d6103b1565b604080519115158252519081900360200190f35b341561015957fe5b61013d6103ba565b604080519115158252519081900360200190f35b341561017d57fe5b6101116104aa565b60408051600160a060020a039092168252519081900360200190f35b61013d6104b9565b604080519115158252519081900360200190f35b34156101c557fe5b6101cd61053f565b60408051918252519081900360200190f35b34156101e757fe5b6101cd610545565b60408051918252519081900360200190f35b341561020957fe5b6101cd61054b565b60408051918252519081900360200190f35b6100c7600435610551565b005b341561023857fe5b6100c7600480803590602001908201803590602001908080602002602001604051908101604052809392919081815260200183836020028082843750506040805187358901803560208181028481018201909552818452989a998901989297509082019550935083925085019084908082843750506040805187358901803560208181028481018201909552818452989a9989019892975090820195509350839250850190849080828437509496506105c195505050505050565b005b34156102fd57fe5b610111610798565b60408051600160a060020a039092168252519081900360200190f35b341561032957fe5b6101cd6107a7565b60408051918252519081900360200190f35b341561034b57fe5b6101cd6107ad565b60408051918252519081900360200190f35b60056020528160005260406000208181548110151561038057fe5b906000526020600020906002020160005b508054600190910154909250905082565b600954600160a060020a031681565b60045460ff1681565b600060035480421015156103cd57610000565b60045460ff16156103dd57610000565b60065460075460408051600160a060020a039093168352602083019190915280517fdaec4582d5d9595688c8c98545fdd1c696d41c6aeaeb636737e84ed2f5c00eda9281900390910190a160008054600754604051600160a060020a039092169281156108fc029290818181858888f19350505050151561045d57610000565b6004805460ff191660019081179091556000546009805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909216919091179055600754600a5591505b5b5090565b600054600160a060020a031681565b600160a060020a0333166000908152600860205260408120548181111561053557600160a060020a0333166000818152600860205260408082208290555183156108fc0291849190818181858888f19350505050151561053557600160a060020a033316600090815260086020526040812082905591506104a5565b5b600191505b5090565b60025481565b600a5481565b60015481565b6002544281901061056157610000565b600160a060020a03331660009081526005602052604090208054600181016105898382610834565b916000526020600020906002020160005b5060408051808201909152848152346020909101819052848255600190910155505b5b5050565b600060006000600060006000600060025480421115156105e057610000565b600354428190106105f057610000565b600160a060020a0333166000908152600560205260409020548c519099508914158061061d5750888b5114155b806106295750888a5114155b1561063357610000565b600096505b8887101561075757600160a060020a033316600090815260056020526040902080548890811061066457fe5b906000526020600020906002020160005b5095508b8781518110151561068657fe5b906020019060200201518b8881518110151561069e57fe5b906020019060200201518b898151811015156106b657fe5b60209081029091018101516040805185815260f860020a851515029381019390935260218301829052519182900360410190912089549398509196509450146106fe5761074c565b8b8781518110151561070c57fe5b90602001906020020151880197508315801561072c575084866001015410155b156107465761073b33866107b3565b156107465784880397505b5b600086555b600190960195610638565b604051600160a060020a0333169089156108fc02908a906000818181858888f19350505050151561078757610000565b5b5b505b5050505050505050505050565b600654600160a060020a031681565b60035481565b60075481565b60075460009082116107c75750600061082e565b600654600160a060020a0316156107fd57600754600654600160a060020a03166000908152600860205260409020805490910190555b5060078190556006805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03841617905560015b92915050565b815481835581811511610860576002028160020283600052602060002091820191016108609190610866565b5b505050565b61088a91905b808211156104a5576000808255600182015560020161086c565b5090565b905600a165627a7a723058200dcde502bdabe7625b1032a21dfd3f756e5babfdfb19fa4e650c0c213fae30c30029";

    private BlindAuction(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private BlindAuction(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<AuctionEndedEventResponse> getAuctionEndedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("AuctionEnded", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<AuctionEndedEventResponse> responses = new ArrayList<AuctionEndedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            AuctionEndedEventResponse typedResponse = new AuctionEndedEventResponse();
            typedResponse.winer = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.highestBid = (Uint256) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<AuctionEndedEventResponse> auctionEndedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("AuctionEnded", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, AuctionEndedEventResponse>() {
            @Override
            public AuctionEndedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                AuctionEndedEventResponse typedResponse = new AuctionEndedEventResponse();
                typedResponse.winer = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.highestBid = (Uint256) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public Future<List<Type>> bids(Address param0, Uint256 param1) {
        Function function = new Function("bids", 
                Arrays.<Type>asList(param0, param1), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
        return executeCallMultipleValueReturnAsync(function);
    }

    public Future<Address> a() {
        Function function = new Function("a", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Bool> ended() {
        Function function = new Function("ended", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> auctionEnd() {
        Function function = new Function("auctionEnd", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Address> beneficiary() {
        Function function = new Function("beneficiary", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> withdraw(BigInteger weiValue) {
        Function function = new Function("withdraw", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function, weiValue);
    }

    public Future<Uint256> biddingEnd() {
        Function function = new Function("biddingEnd", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint256> b() {
        Function function = new Function("b", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint256> auctionStart() {
        Function function = new Function("auctionStart", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> bids(Bytes32 _blindedBid, BigInteger weiValue) {
        Function function = new Function("bids", Arrays.<Type>asList(_blindedBid), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function, weiValue);
    }

    public Future<TransactionReceipt> reveal(DynamicArray<Uint256> _values, DynamicArray<Bool> _fake, DynamicArray<Bytes32> _secret) {
        Function function = new Function("reveal", Arrays.<Type>asList(_values, _fake, _secret), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Address> highestBidder() {
        Function function = new Function("highestBidder", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint256> revealEnd() {
        Function function = new Function("revealEnd", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint256> highestBid() {
        Function function = new Function("highestBid", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<BlindAuction> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, Uint256 _biddingTime, Uint256 _revealTime, Address _beneficiary) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_biddingTime, _revealTime, _beneficiary));
        return deployAsync(BlindAuction.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static Future<BlindAuction> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, Uint256 _biddingTime, Uint256 _revealTime, Address _beneficiary) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_biddingTime, _revealTime, _beneficiary));
        return deployAsync(BlindAuction.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static BlindAuction load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BlindAuction(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static BlindAuction load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BlindAuction(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class AuctionEndedEventResponse {
        public Address winer;

        public Uint256 highestBid;
    }
}
