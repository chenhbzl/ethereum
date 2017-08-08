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
    private static final String BINARY = "6060604052341561000c57fe5b6040516060806108b58339810160409081528151602083015191909201515b60008054600160a060020a031916600160a060020a0383161790554260018190558301600281905582016003555b5050505b6108498061006c6000396000f300606060405236156100a95763ffffffff60e060020a60003504166301495c1c81146100be57806312065fe0146100f657806312fa6feb146101185780632a24f46c1461013c57806338af3eed146101605780633ccfd60b1461018c578063423b217f146101a85780634f245ef7146101ca5780638f98eeda146101ec578063900f080a146101f957806391f90157146102be578063a6e66477146102ea578063d57bde791461030c575b34156100b157fe5b6100bc5b610000565b565b005b34156100c657fe5b6100dd600160a060020a036004351660243561032e565b6040805192835260208301919091528051918290030190f35b34156100fe57fe5b61010661036b565b60408051918252519081900360200190f35b341561012057fe5b61012861037c565b604080519115158252519081900360200190f35b341561014457fe5b610128610385565b604080519115158252519081900360200190f35b341561016857fe5b610170610440565b60408051600160a060020a039092168252519081900360200190f35b61012861044f565b604080519115158252519081900360200190f35b34156101b057fe5b6101066104d5565b60408051918252519081900360200190f35b34156101d257fe5b6101066104db565b60408051918252519081900360200190f35b6100bc6004356104e1565b005b341561020157fe5b6100bc600480803590602001908201803590602001908080602002602001604051908101604052809392919081815260200183836020028082843750506040805187358901803560208181028481018201909552818452989a998901989297509082019550935083925085019084908082843750506040805187358901803560208181028481018201909552818452989a99890198929750908201955093508392508501908490808284375094965061055195505050505050565b005b34156102c657fe5b610170610728565b60408051600160a060020a039092168252519081900360200190f35b34156102f257fe5b610106610737565b60408051918252519081900360200190f35b341561031457fe5b61010661073d565b60408051918252519081900360200190f35b60056020528160005260406000208181548110151561034957fe5b906000526020600020906002020160005b508054600190910154909250905082565b600054600160a060020a0316315b90565b60045460ff1681565b6000600354804210151561039857610000565b60045460ff16156103a857610000565b60065460075460408051600160a060020a039093168352602083019190915280517fdaec4582d5d9595688c8c98545fdd1c696d41c6aeaeb636737e84ed2f5c00eda9281900390910190a160008054600754604051600160a060020a039092169281156108fc029290818181858888f19350505050151561042857610000565b6004805460ff1916600190811790915591505b5b5090565b600054600160a060020a031681565b600160a060020a033316600090815260086020526040812054818111156104cb57600160a060020a0333166000818152600860205260408082208290555183156108fc0291849190818181858888f1935050505015156104cb57600160a060020a0333166000908152600860205260408120829055915061043b565b5b600191505b5090565b60025481565b60015481565b600254428190106104f157610000565b600160a060020a033316600090815260056020526040902080546001810161051983826107c4565b916000526020600020906002020160005b5060408051808201909152848152346020909101819052848255600190910155505b5b5050565b6000600060006000600060006000600254804211151561057057610000565b6003544281901061058057610000565b600160a060020a0333166000908152600560205260409020548c51909950891415806105ad5750888b5114155b806105b95750888a5114155b156105c357610000565b600096505b888710156106e757600160a060020a03331660009081526005602052604090208054889081106105f457fe5b906000526020600020906002020160005b5095508b8781518110151561061657fe5b906020019060200201518b8881518110151561062e57fe5b906020019060200201518b8981518110151561064657fe5b60209081029091018101516040805185815260f860020a8515150293810193909352602183018290525191829003604101909120895493985091965094501461068e576106dc565b8b8781518110151561069c57fe5b9060200190602002015188019750831580156106bc575084866001015410155b156106d6576106cb3386610743565b156106d65784880397505b5b600086555b6001909601956105c8565b604051600160a060020a0333169089156108fc02908a906000818181858888f19350505050151561071757610000565b5b5b505b5050505050505050505050565b600654600160a060020a031681565b60035481565b60075481565b6007546000908211610757575060006107be565b600654600160a060020a03161561078d57600754600654600160a060020a03166000908152600860205260409020805490910190555b5060078190556006805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03841617905560015b92915050565b8154818355818115116107f0576002028160020283600052602060002091820191016107f091906107f6565b5b505050565b61037991905b8082111561043b57600080825560018201556002016107fc565b5090565b905600a165627a7a72305820908d30379dfc54478a9cfaac9620c5fbe1388d78cf1db0f6ecd654e3c9927dfd0029";

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

    public Future<Uint256> getBalance() {
        Function function = new Function("getBalance", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
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
