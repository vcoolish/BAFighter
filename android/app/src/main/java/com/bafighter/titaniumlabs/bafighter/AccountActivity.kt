package com.bafighter.titaniumlabs.bafighter

import android.support.v7.app.AppCompatActivity
import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_account.*
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ManagedTransaction.GAS_PRICE
import java.math.BigInteger
import android.content.Intent
import com.bafighter.titaniumlabs.bafighter.blockchain.App
import com.bafighter.titaniumlabs.bafighter.blockchain.Character
import com.bafighter.titaniumlabs.bafighter.blockchain.GameToken
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.tx.ManagedTransaction
import android.widget.Toast
import org.jetbrains.anko.doAsync
import org.web3j.abi.EventEncoder
import org.web3j.protocol.core.methods.request.EthFilter
import java.io.IOException
import android.content.ClipData
import android.view.Menu
import android.view.MenuItem
import rx.Subscription
import android.view.View
import android.view.WindowManager


class AccountActivity : AppCompatActivity() {

    lateinit var characterContract: Character
    lateinit var appContract: App
    lateinit var gameTokenContract: GameToken

    lateinit var tokenAddress: String
    lateinit var characterAddress: String
    lateinit var userAddress: String

    lateinit var creds: Credentials
    lateinit var web3: Web3j
    lateinit var subscription: Subscription

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        if(isConnected()) {
            creds = Credentials.create(getSharedPreferences("Account", Context.MODE_PRIVATE).getString("private_key", ""))
            userAddress = creds.address
            tokenAddress = getSharedPreferences("Account", Context.MODE_PRIVATE).getString("token", "")
            characterAddress = getSharedPreferences("Account", Context.MODE_PRIVATE).getString("character", "")
            web3 = Web3jFactory.build(HttpService(MainActivity.SERVER))
            if(loadContracts()) {
                address.text = "Your address: $userAddress"
                character_address.text = "Character address: $characterAddress"
                character_reputation.text = "Reputation: ${getRep()}"

                val userHealth = getUserHealth()
                setOnClickListeners()
                health.text = String.format("%d HP", userHealth)
                setEventListener()
            }
            else{
                Toast.makeText(this,"Something went wrong", Toast.LENGTH_LONG).show()
                recreate()
            }
        }
        else
            Toast.makeText(this,"Check connection", Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.account_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> recreate()
        }
        return true
    }

    private fun setOnClickListeners() {
        address.setOnLongClickListener {
            val clipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clipData = ClipData.newPlainText("address", userAddress)
            clipboardManager.primaryClip = clipData
            Toast.makeText(this,"Address copied to clipboard", Toast.LENGTH_LONG).show()
            true
        }

        character_address.setOnLongClickListener {
            val clipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clipData = ClipData.newPlainText("address", userAddress)
            clipboardManager.primaryClip = clipData
            Toast.makeText(this,"Address copied to clipboard", Toast.LENGTH_LONG).show()
            true
        }
    }

    private fun startBattle(event: String) {
        val intent = Intent(this, FightActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, event, Toast.LENGTH_LONG).show()
    }

    companion object {
        @Throws(InterruptedException::class, IOException::class)
        fun isConnected(): Boolean {
            val command = "ping -c 1 google.com"
            return Runtime.getRuntime().exec(command).waitFor() == 0
        }
    }

    fun getUserHealth(): BigInteger {
        return try {
            showProgress()
            gameTokenContract.balanceOf(userAddress).sendAsync().get()
        }
        catch (e: Exception){
            e.printStackTrace()
            return (-1).toBigInteger()
        }
        finally {
            hideProgress()
        }
    }

    fun getRep(): BigInteger {
        return try {
            showProgress()
            characterContract.reputation().sendAsync().get()
        }
        catch (e: Exception){
            e.printStackTrace()
            return (-1).toBigInteger()
        }
        finally {
            hideProgress()
        }
    }

    fun loadContracts(): Boolean{
        return try {
            appContract = App.load(
                    MainActivity.APP_ADDRESS, web3, creds,
                    ManagedTransaction.GAS_PRICE, BigInteger.valueOf(6800000))

            gameTokenContract = GameToken.load(
                    tokenAddress, web3, creds, GAS_PRICE, BigInteger.valueOf(8000000))
            characterContract = Character.load(characterAddress, web3, creds, GAS_PRICE, BigInteger.valueOf(8000000))
            true
        }
        catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    fun setEventListener() {
        val filter = EthFilter(DefaultBlockParameterName.PENDING, DefaultBlockParameterName.LATEST, appContract.contractAddress.substring(2))
        val encodedEventSignature = EventEncoder.encode(App.GAMESTARTED_EVENT)
        filter.addSingleTopic(encodedEventSignature)
        android.setOnClickListener {
            startBattle("Go fight!")
        }

        doAsync {
            subscription = web3.ethLogObservable(filter).subscribe({ event ->
                println("yougotnoerror")
                this@AccountActivity.runOnUiThread {
                    startBattle(event.toString())
                }
            }, { onError ->
                println("yougoterror")
                onError.printStackTrace()
            })
        }.get()
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun hideProgress() {
        progress.visibility = View.INVISIBLE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onDestroy() {
        if(!subscription.isUnsubscribed)
            subscription.unsubscribe()
        super.onDestroy()
    }
}