package viewmodel

import java.math.BigInteger

class Character(
        var owner: User,
        var address: String,
        var health: BigInteger,
        var reputation: BigInteger,
        var gameSet: String){

    fun hasGame(): Boolean = gameSet != "0x0000000000000000000000000000000000000000"
}
