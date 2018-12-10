package com.bafighter.titaniumlabs.bafighter.blockchain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.5.0.
 */
public class Character extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b50604051608080610b398339810180604052810190808051906020019092919080519060200190929190805190602001909291908051906020019092919050505060008411801561008e5750600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1614155b80156100c75750600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff1614155b80156101005750600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614155b151561010b57600080fd5b60058481151561011757fe5b046001819055508360008190555082600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555081600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600660006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050505061093e806101fb6000396000f3006080604052600436106100ba576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063303bdd2c146100bf5780637633a22c146100f0578063819912a21461011b5780638da5cb5b14610176578063a1580f1a146101cd578063b135cf8c146101f8578063b76564bd14610240578063c3dfdae614610297578063c3fe3e28146102ee578063c52164c614610345578063cfccaa1914610370578063d314cbc3146103a0575b600080fd5b3480156100cb57600080fd5b506100d46103d0565b604051808260ff1660ff16815260200191505060405180910390f35b3480156100fc57600080fd5b506101056103e3565b6040518082815260200191505060405180910390f35b34801561012757600080fd5b5061015c600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506103e9565b604051808215151515815260200191505060405180910390f35b34801561018257600080fd5b5061018b6104cd565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156101d957600080fd5b506101e26104f3565b6040518082815260200191505060405180910390f35b34801561020457600080fd5b50610226600480360381019080803560ff1690602001909291905050506104f9565b604051808215151515815260200191505060405180910390f35b34801561024c57600080fd5b506102556105a2565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156102a357600080fd5b506102ac6105c8565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156102fa57600080fd5b506103036105ee565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561035157600080fd5b5061035a610614565b6040518082815260200191505060405180910390f35b34801561037c57600080fd5b5061039e600480360381019080803560ff16906020019092919050505061061a565b005b3480156103ac57600080fd5b506103ce600480360381019080803560ff169060200190929190505050610694565b005b600360009054906101000a900460ff1681565b60005481565b6000600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561044757600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff161415151561048357600080fd5b81600360016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060019050919050565b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60015481565b6000600360019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561055757600080fd5b6001546000541115610598578160ff16600360009054906101000a900460ff1660ff1614151561058f57600154600054036000819055505b6000905061059d565b600190505b919050565b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600660009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600360019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60025481565b600360019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561067657600080fd5b80600360006101000a81548160ff021916908360ff16021790555050565b600360019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156106f057600080fd5b600060028111156106fd57fe5b60ff168160ff16141561072b5761072060016002546108f690919063ffffffff16565b600281905550610761565b6001600281111561073857fe5b60ff168160ff1614801561074e57506000600254115b15610760576001600254036002819055505b5b600660009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166370a08231600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff166040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050602060405180830381600087803b15801561084057600080fd5b505af1158015610854573d6000803e3d6000fd5b505050506040513d602081101561086a57600080fd5b81019080805190602001909291905050506000819055506000600360016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508060ff167f13762697b4cae05620517de0c3b25541c70f2779850f6eb63748752ef3e472aa60405160405180910390a250565b6000818301905082811015151561090957fe5b809050929150505600a165627a7a72305820c0d31cf3a214f1f5abffaaf5a0f08a433941ed6c977b94e675e548adb54e972d0029";

    public static final String FUNC_BLOCKED = "blocked";

    public static final String FUNC_HEALTH = "health";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_APP = "app";

    public static final String FUNC_GAMETOKEN = "gameToken";

    public static final String FUNC_GAME = "game";

    public static final String FUNC_REPUTATION = "reputation";

    public static final String FUNC_SETGAME = "setGame";

    public static final String FUNC_SETBLOCK = "setBlock";

    public static final String FUNC_DAMAGE = "damage";

    public static final String FUNC_RESET = "reset";

    public static final Event MATCHRESULT_EVENT = new Event("MatchResult", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}),
            Arrays.<TypeReference<?>>asList());
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    protected Character(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Character(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<BigInteger> blocked() {
        final Function function = new Function(FUNC_BLOCKED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> health() {
        final Function function = new Function(FUNC_HEALTH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> app() {
        final Function function = new Function(FUNC_APP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> gameToken() {
        final Function function = new Function(FUNC_GAMETOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> game() {
        final Function function = new Function(FUNC_GAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> reputation() {
        final Function function = new Function(FUNC_REPUTATION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<Character> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _health, String _app, String _owner, String _gameToken) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_health), 
                new org.web3j.abi.datatypes.Address(_app), 
                new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.Address(_gameToken)));
        return deployRemoteCall(Character.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Character> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _health, String _app, String _owner, String _gameToken) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_health), 
                new org.web3j.abi.datatypes.Address(_app), 
                new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.Address(_gameToken)));
        return deployRemoteCall(Character.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public List<MatchResultEventResponse> getMatchResultEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MATCHRESULT_EVENT, transactionReceipt);
        ArrayList<MatchResultEventResponse> responses = new ArrayList<MatchResultEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MatchResultEventResponse typedResponse = new MatchResultEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._matchStatus = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<MatchResultEventResponse> matchResultEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, MatchResultEventResponse>() {
            @Override
            public MatchResultEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MATCHRESULT_EVENT, log);
                MatchResultEventResponse typedResponse = new MatchResultEventResponse();
                typedResponse.log = log;
                typedResponse._matchStatus = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<MatchResultEventResponse> matchResultEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MATCHRESULT_EVENT));
        return matchResultEventObservable(filter);
    }

    public RemoteCall<TransactionReceipt> setGame(String _game) {
        final Function function = new Function(
                FUNC_SETGAME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_game)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setBlock(BigInteger _type) {
        final Function function = new Function(
                FUNC_SETBLOCK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(_type)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> damage() {
        final Function function = new Function(FUNC_DAMAGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> damage(BigInteger _type) {
        final Function function = new Function(
                FUNC_DAMAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(_type)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> reset(BigInteger _result) {
        final Function function = new Function(
                FUNC_RESET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(_result)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static Character load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Character(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Character load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Character(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class MatchResultEventResponse {
        public Log log;

        public BigInteger _matchStatus;
    }
}
