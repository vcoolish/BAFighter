package com.bafighter.titaniumlabs.bafighter

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.Toast
import com.bafighter.titaniumlabs.bafighter.blockchain.App
import kotlinx.android.synthetic.main.activity_main.*
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ManagedTransaction
import java.math.BigInteger
import android.os.Build
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.WindowManager
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread


class MainActivity : AppCompatActivity() {

    companion object {
        const val APP_ADDRESS = "0xf87623de2a8e52ec3928a2997d3f13c06ceed0a9"
        const val SERVER = "https://rinkeby.infura.io/metamask"
        const val ADMIN_ADDRESS = "0x554862e0ebf0e566708e8038e50a7bdf8ef50dcf"
    }

    lateinit var creds: Credentials
    lateinit var web3: Web3j
    lateinit var appContract: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val acc = getSharedPreferences("Account", Context.MODE_PRIVATE).getString("private_key", "")
        if(acc != "") {
            if(Credentials.create(acc).address == ADMIN_ADDRESS)
                startActivity(Intent(this, AdminActivity::class.java))
            else
                startActivity(Intent(this, AccountActivity::class.java))
        }

        confirm.setOnClickListener {
            if(AccountActivity.isConnected()) {
                if (WalletUtils.isValidPrivateKey(private_key.text.toString())) {
                    showAlert()
                } else {
                    Toast.makeText(this, "Enter valid private key", Toast.LENGTH_SHORT).show()
                    vibrateShort()
                }
            }
            else{
                vibrateShort()
                Toast.makeText(this, "Check internet connection!", Toast.LENGTH_SHORT).show()
            }
        }
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

    fun vibrateShort() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(250)
    }

    private fun showAlert(){
        val builder: AlertDialog.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
        } else {
            AlertDialog.Builder(this)
        }

        builder.setTitle("Create character")
                .setMessage("Are you sure you want to create your game character?")
                .setPositiveButton(android.R.string.yes) { dialog, _ ->
                    dialog.dismiss()

                    getSharedPreferences("Account", Context.MODE_PRIVATE).edit().putString("private_key",
                            private_key.text.toString()).apply()

                    creds = Credentials.create(private_key.text.toString())
                    web3 = Web3jFactory.build(HttpService(SERVER))

                    if(setAppContract()) {

                        if (creds.address.trim() == ADMIN_ADDRESS) {
                            if (rewardUsers())
                                startActivity(Intent(this, AdminActivity::class.java))
                            else
                                alert()
                        } else {
                            getSharedPreferences("Account", Context.MODE_PRIVATE).edit().putString("token",
                                    getGameToken()).apply()

                            if(!createCharacter()) {
                                Toast.makeText(this, "Character exists", Toast.LENGTH_SHORT).show()
                            }
                            val characterAddress = getCharacter()
                            if (characterAddress == "") {
                                alert()
                            } else {
                                getSharedPreferences("Account", Context.MODE_PRIVATE).edit().putString("character",
                                        characterAddress).apply()
                            }
                            startActivity(Intent(this, AccountActivity::class.java))
                        }
                    }
                }
                .setNegativeButton(android.R.string.no) { dialog, _ ->
                    dialog.dismiss()
                }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
    }

    fun rewardUsers(): Boolean {
        return try{
            doAsyncResult {
                appContract.tokenReward(100.toBigInteger(), "0xcf442785d32c4390c44c70e11aa0894944fae084").send()
                appContract.tokenReward(100.toBigInteger(), "0xfa8f66638748e19a0b2fe021fb939960991d6497").send()
                uiThread {
                    showProgress()
                }
            }
            true
        }
        catch (e: Exception){
            e.printStackTrace()
            false
        }
        finally {
            hideProgress()
        }
    }

    fun setAppContract(): Boolean{
        return try {
            showProgress()
            appContract = App.load(
                    APP_ADDRESS, web3, creds,
                    ManagedTransaction.GAS_PRICE, BigInteger.valueOf(6800000))
            true
        }
        catch(e:Exception){
            e.printStackTrace()
            false
        }
        finally {
            hideProgress()
        }
    }

    fun getGameToken(): String{
        return try{
            showProgress()
            appContract.gameToken().sendAsync().get().toString()
        }
        catch (e:Exception){
            e.printStackTrace()
            return ""
        }
        finally {
            hideProgress()
        }
    }

    fun createCharacter(): Boolean{
        return try{
            showProgress()
            appContract.createCharacter().sendAsync().get()
            true
        }
        catch (e: Exception){
            e.printStackTrace()
            false
        }
        finally {
            hideProgress()
        }
    }

    fun getCharacter(): String{
        return try{
            showProgress()
            appContract.characterOf(creds.address).sendAsync().get().toString()
        }
        catch (e:Exception){
            e.printStackTrace()
            return ""
        }
        finally {
            hideProgress()
        }
    }

    fun alert(){
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
    }
}
//"0x554862e0ebf0e566708e8038e50a7bdf8ef50dcf"  231A5D6F6B2DF6E5858A2B6A1D4E24D1C49E260E7CFAAF67A8F3972B3F04AA5B
// WalletUtils.loadBip39Credentials(null, "metal approve pact attract pepper carry fruit rich tag grunt buyer exist")
//balance.text = web3.ethGetBalance(credentials.address, DefaultBlockParameterName.LATEST).sendAsync().get().balance.toString()
//address.text = credentials.address
//val web3:Web3j = Web3jFactory.build(HttpService("https://rinkeby.infura.io/metamask"))
//val credentials = Credentials.create("")