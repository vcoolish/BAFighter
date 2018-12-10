package viewmodel

import org.web3j.abi.datatypes.Bool
import java.math.BigInteger

class Game(
        var appAddress: String,
        var tokenAddress: String,
        var firstCharacter: String,
        var secondCharacter: String,
        var status: BigInteger,
        var currentCharacter: String,
        var gameNumber: BigInteger) {

//    fun isMyTurn(): Boolean = currentCharacter == Character.
    fun isEmpty(): Boolean = status == 0.toBigInteger()
}