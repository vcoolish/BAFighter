package com.bafighter.titaniumlabs.bafighter.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.5.0.
 */
public class Game extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b506040516040806111868339810180604052810190808051906020019092919080519060200190929190505050600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16141580156100a75750600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614155b15156100b257600080fd5b81600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506000600560146101000a81548160ff021916908360ff1602179055505050611025806101616000396000f3006080604052600436106100ba576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063200d2ed2146100bf5780633602c7b3146100f05780633ff9ca57146101475780635269264d1461019e57806369fa709c146101cf5780638c3c4c6214610200578063a1de79a914610217578063a542fc5c1461026e578063b76564bd146102d1578063c3dfdae614610328578063f201f0381461037f578063fbf979eb146103bc575b600080fd5b3480156100cb57600080fd5b506100d4610413565b604051808260ff1660ff16815260200191505060405180910390f35b3480156100fc57600080fd5b50610105610426565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561015357600080fd5b5061015c61044c565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156101aa57600080fd5b506101b3610472565b604051808260ff1660ff16815260200191505060405180910390f35b3480156101db57600080fd5b506101e4610485565b604051808260ff1660ff16815260200191505060405180910390f35b34801561020c57600080fd5b50610215610498565b005b34801561022357600080fd5b5061022c61051d565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561027a57600080fd5b506102cf600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610542565b005b3480156102dd57600080fd5b506102e66106cb565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561033457600080fd5b5061033d6106f1565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561038b57600080fd5b506103ba600480360381019080803560ff169060200190929190803560ff169060200190929190505050610717565b005b3480156103c857600080fd5b506103d1610d7d565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b600560149054906101000a900460ff1681565b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600560159054906101000a900460ff1681565b600560169054906101000a900460ff1681565b3373ffffffffffffffffffffffffffffffffffffffff16600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415156104f457600080fd5b6000600181111561050157fe5b600560146101000a81548160ff021916908360ff160217905550565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b3373ffffffffffffffffffffffffffffffffffffffff16600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151561059e57600080fd5b6001808111156105aa57fe5b600560146101000a81548160ff021916908360ff160217905550816000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555081600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b3373ffffffffffffffffffffffffffffffffffffffff16600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16638da5cb5b6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156107b457600080fd5b505af11580156107c8573d6000803e3d6000fd5b505050506040513d60208110156107de57600080fd5b810190808051906020019092919050505073ffffffffffffffffffffffffffffffffffffffff1614151561081157600080fd5b60018081111561081d57fe5b60ff16600560149054906101000a900460ff1660ff1614151561083f57600080fd5b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156108f95781600560156101000a81548160ff021916908360ff16021790555080600560166101000a81548160ff021916908360ff1602179055506108f4610da3565b610d79565b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663cfccaa19600560169054906101000a900460ff166040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808260ff1660ff168152602001915050600060405180830381600087803b15801561099f57600080fd5b505af11580156109b3573d6000803e3d6000fd5b50505050600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663cfccaa19826040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808260ff1660ff168152602001915050600060405180830381600087803b158015610a4e57600080fd5b505af1158015610a62573d6000803e3d6000fd5b50505050600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663b135cf8c600560159054906101000a900460ff166040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808260ff1660ff168152602001915050602060405180830381600087803b158015610b0c57600080fd5b505af1158015610b20573d6000803e3d6000fd5b505050506040513d6020811015610b3657600080fd5b810190808051906020019092919050505015610c7057600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663b135cf8c836040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808260ff1660ff168152602001915050602060405180830381600087803b158015610be357600080fd5b505af1158015610bf7573d6000803e3d6000fd5b505050506040513d6020811015610c0d57600080fd5b810190808051906020019092919050505015610c4857610c43600280811115610c3257fe5b600280811115610c3e57fe5b610e71565b610c6b565b610c6a60016002811115610c5857fe5b60006002811115610c6557fe5b610e71565b5b610d78565b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663b135cf8c836040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808260ff1660ff168152602001915050602060405180830381600087803b158015610d0757600080fd5b505af1158015610d1b573d6000803e3d6000fd5b505050506040513d6020811015610d3157600080fd5b810190808051906020019092919050505015610d6e57610d6960006002811115610d5757fe5b60016002811115610d6457fe5b610e71565b610d77565b610d76610da3565b5b5b5b5050565b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b60006001811115610e7e57fe5b600560146101000a81548160ff021916908360ff160217905550600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663d314cbc3836040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808260ff1660ff168152602001915050600060405180830381600087803b158015610f2f57600080fd5b505af1158015610f43573d6000803e3d6000fd5b505050506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663d314cbc3826040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808260ff1660ff168152602001915050600060405180830381600087803b158015610fdd57600080fd5b505af1158015610ff1573d6000803e3d6000fd5b5050505050505600a165627a7a72305820e1da21603231260397965cb6ee2b469784cedfc6109b159abcc4268820f6749c0029";

    public static final String FUNC_STATUS = "status";

    public static final String FUNC_INACTIVECHARACTER = "inactiveCharacter";

    public static final String FUNC_SECONDCHARACTER = "secondCharacter";

    public static final String FUNC_HITTYPE = "hitType";

    public static final String FUNC_BLOCKTYPE = "blockType";

    public static final String FUNC_FIRSTCHARACTER = "firstCharacter";

    public static final String FUNC_APP = "app";

    public static final String FUNC_GAMETOKEN = "gameToken";

    public static final String FUNC_ACTIVECHARACTER = "activeCharacter";

    public static final String FUNC_HIT = "hit";

    public static final String FUNC_STARTFIGHT = "startFight";

    public static final String FUNC_RESETSTATUS = "resetStatus";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    protected Game(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Game(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<BigInteger> status() {
        final Function function = new Function(FUNC_STATUS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> inactiveCharacter() {
        final Function function = new Function(FUNC_INACTIVECHARACTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> secondCharacter() {
        final Function function = new Function(FUNC_SECONDCHARACTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> hitType() {
        final Function function = new Function(FUNC_HITTYPE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> blockType() {
        final Function function = new Function(FUNC_BLOCKTYPE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> firstCharacter() {
        final Function function = new Function(FUNC_FIRSTCHARACTER, 
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

    public RemoteCall<String> activeCharacter() {
        final Function function = new Function(FUNC_ACTIVECHARACTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<Game> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _app, String _gameToken) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_app), 
                new org.web3j.abi.datatypes.Address(_gameToken)));
        return deployRemoteCall(Game.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Game> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _app, String _gameToken) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_app), 
                new org.web3j.abi.datatypes.Address(_gameToken)));
        return deployRemoteCall(Game.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public RemoteCall<TransactionReceipt> hit(BigInteger _hitType, BigInteger _blockType) {
        final Function function = new Function(
                FUNC_HIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(_hitType), 
                new org.web3j.abi.datatypes.generated.Uint8(_blockType)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> startFight(String _firstCharacter, String _secondCharacter) {
        final Function function = new Function(
                FUNC_STARTFIGHT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_firstCharacter), 
                new org.web3j.abi.datatypes.Address(_secondCharacter)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> resetStatus() {
        final Function function = new Function(
                FUNC_RESETSTATUS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static Game load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Game(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Game load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Game(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
