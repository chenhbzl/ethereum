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
 * <p>Generated with web3j version 2.2.2.
 */
public final class BlindAuction extends Contract {
    private static final String BINARY = "6060604052341561000c57fe5b60405160608061087c8339810160409081528151602083015191909201515b60008054600160a060020a031916600160a060020a0383161790554260018190558301600281905582016003555b5050505b6108108061006c6000396000f3006060604052361561009e5763ffffffff60e060020a60003504166301495c1c81146100b357806312fa6feb146100eb5780632a24f46c1461010f57806338af3eed146101215780633ccfd60b1461014d578063423b217f146101695780634f245ef71461018b57806350b11ba4146101ad57806391f901571461023a578063a6e6647714610266578063d57bde7914610288578063d9d26c11146102aa575b34156100a657fe5b6100b15b610000565b565b005b34156100bb57fe5b6100d2600160a060020a03600435166024356102bc565b6040805192835260208301919091528051918290030190f35b34156100f357fe5b6100fb6102f9565b604080519115158252519081900360200190f35b341561011757fe5b6100b1610302565b005b341561012957fe5b6101316103b8565b60408051600160a060020a039092168252519081900360200190f35b6100fb6103c7565b604080519115158252519081900360200190f35b341561017157fe5b61017961044d565b60408051918252519081900360200190f35b341561019357fe5b610179610453565b60408051918252519081900360200190f35b34156101b557fe5b6100b1600480803590602001908201803590602001908080602002602001604051908101604052809392919081815260200183836020028082843750506040805187358901803560208181028481018201909552818452989a99890198929750908201955093508392508501908490808284375094965061045995505050505050565b005b341561024257fe5b6101316105eb565b60408051600160a060020a039092168252519081900360200190f35b341561026e57fe5b6101796105fa565b60408051918252519081900360200190f35b341561029057fe5b610179610600565b60408051918252519081900360200190f35b6100b16004356024351515610606565b005b6005602052816000526040600020818154811015156102d757fe5b906000526020600020906002020160005b508054600190910154909250905082565b60045460ff1681565b6003544281901061031257610000565b60045460ff161561032257610000565b60075460085460408051600160a060020a039093168352602083019190915280517fdaec4582d5d9595688c8c98545fdd1c696d41c6aeaeb636737e84ed2f5c00eda9281900390910190a160008054604051600160a060020a0391821692309092163180156108fc0292909190818181858888f1935050505015156103a657610000565b6004805460ff191660011790555b5b50565b600054600160a060020a031681565b600160a060020a0333166000908152600660205260408120548181111561044357600160a060020a0333166000818152600660205260408082208290555183156108fc0291849190818181858888f19350505050151561044357600160a060020a03331660009081526006602052604081208290559150610449565b5b600191505b5090565b60025481565b60015481565b600060006000600060006000600254804211151561047657610000565b6003544281901061048657610000565b600160a060020a0333166000908152600560205260409020548a5190985088146104af57610000565b885188146104bc57610000565b600095505b878610156105ac57600160a060020a03331660009081526005602052604090208054879081106104ed57fe5b906000526020600020906002020160005b509450898681518110151561050f57fe5b90602001906020020151898781518110151561052757fe5b60209081029091018101516040805184815260f860020a83151502938101939093525191829003602101909120875492965090945014610566576105a1565b84600101548701965082158015610581575083856001015410155b1561059b57610590338561070a565b1561059b5783870396505b5b600085555b6001909501946104c1565b604051600160a060020a0333169088156108fc029089906000818181858888f1935050505015156105dc57610000565b5b5b505b505050505050505050565b600754600160a060020a031681565b60035481565b60085481565b6000600254804210151561061957610000565b60408051600060209182015281517f15085139000000000000000000000000000000000000000000000000000000008152600481018790528515156024820152915173__AuctionLib.sol:AuctionLib_____________926315085139926044808301939192829003018186803b151561068f57fe5b60325a03f4151561069c57fe5b50506040805151600160a060020a033316600090815260056020529190912080549194509150600181016106d0838261078b565b916000526020600020906002020160005b5060408051808201909152848152346020909101819052848255600190910155505b5b50505050565b600854600090821161071e57506000610785565b600754600160a060020a03161561075457600854600754600160a060020a03166000908152600660205260409020805490910190555b5060088190556007805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03841617905560015b92915050565b8154818355818115116107b7576002028160020283600052602060002091820191016107b791906107bd565b5b505050565b6107e191905b8082111561044957600080825560018201556002016107c3565b5090565b905600a165627a7a72305820e2c45263ffb399af50dd3cd62b20e730a4302edaeec590a9ce0e615d2ab4bdcc0029";

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

    public Future<TransactionReceipt> withdraw() {
        Function function = new Function("withdraw", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
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

    public Future<TransactionReceipt> reveal(DynamicArray<Uint256> _values, DynamicArray<Bool> _fake) {
        Function function = new Function("reveal", Arrays.<Type>asList(_values, _fake), Collections.<TypeReference<?>>emptyList());
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

    public Future<TransactionReceipt> bids(Uint256 _values, Bool _fake) {
        Function function = new Function("bids", Arrays.<Type>asList(_values, _fake), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
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
