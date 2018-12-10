package viewmodel

import android.content.Context
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.http.HttpService


class Web3Setting {
    var web3: Web3j = Web3jFactory.build(HttpService(SERVER))
    lateinit var creds: Credentials

    companion object {
        const val APP_ADDRESS = "0xf87623de2a8e52ec3928a2997d3f13c06ceed0a9"
        const val SERVER = "https://rinkeby.infura.io/metamask"
        const val ADMIN_ADDRESS = "0x554862e0ebf0e566708e8038e50a7bdf8ef50dcf"
    }

    constructor(context: Context) {
        val acc = context.getSharedPreferences("Account", Context.MODE_PRIVATE).getString("private_key", "")
        if(acc != "") {
            creds = Credentials.create(acc)
        }
    }

}