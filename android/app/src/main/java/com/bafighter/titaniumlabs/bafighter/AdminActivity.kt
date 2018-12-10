package com.bafighter.titaniumlabs.bafighter

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bafighter.titaniumlabs.bafighter.blockchain.App
import kotlinx.android.synthetic.main.activity_admin.*
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ManagedTransaction
import java.math.BigInteger


class AdminActivity : AppCompatActivity() {

    lateinit var creds: Credentials
    lateinit var web3: Web3j
    lateinit var appContract: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val privateKey = getSharedPreferences("Account", Context.MODE_PRIVATE).
                getString(
                        "private_key",
                "0x0"
                )
        creds = Credentials.create(privateKey)
        web3 = Web3jFactory.build(HttpService(MainActivity.SERVER))

        if(setAppContract()) {
            val characters = arrayListOf<String>()
            val games = arrayListOf<Int>()

            for (i in getCharacters())
                characters.add(i.toString())
            for (i in 0 until getGameCount())
                games.add(i)

            setSpinners(characters, games)
            setOnClickListeners()
        }
        else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            recreate()
        }
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
        fight_them.setOnClickListener {
            startGame()
        }

        reward_btn.setOnClickListener {
            if(reward_edit.text.toString().isNotEmpty() && WalletUtils.isValidAddress(reward_edit.text.toString()))
                rewardUser(reward_edit.text.toString())
            else
                reward_edit.hint = "Enter VALID address!"
        }
    }

    private fun setSpinners(characters: ArrayList<String>, games: ArrayList<Int>) {
        ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                characters
        ).also { adapter ->
            first_characters.adapter = adapter
            second_characters.adapter = adapter
        }

        ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                games
        ).also { adapter ->
            game_spinner.adapter = adapter
        }
    }

    fun setAppContract(): Boolean{
        return try {
            appContract = App.load(
                    MainActivity.APP_ADDRESS, web3, creds,
                    ManagedTransaction.GAS_PRICE, BigInteger.valueOf(6800000))
            true
        }
        catch(e:Exception){
            e.printStackTrace()
            false
        }
    }

    fun getCharacters(): MutableList<Any?> {
        return try {
            showProgress()
            appContract.characters.sendAsync().get()
        }
        catch (e: Exception){
            e.printStackTrace()
            mutableListOf()
        }
        finally {
            hideProgress()
        }
    }

    fun getGameCount(): Int {
        return try {
            showProgress()
            appContract.games.sendAsync().get().size
        }
        catch (e: Exception){
            e.printStackTrace()
            0
        }
        finally {
            hideProgress()
        }
    }

    fun startGame(): Boolean {
        return try {
            showProgress()
            if(first_characters.selectedItem.toString() == second_characters.selectedItem.toString() ||
                    first_characters.selectedItem.toString() == "") {
                Toast.makeText(this, "Check characters!", Toast.LENGTH_LONG).show()
                false
            }
            else {
                appContract.startGame(first_characters.selectedItem.toString(),
                        second_characters.selectedItem.toString(),
                        game_spinner.selectedItem.toString().toBigInteger()).sendAsync().get()
                Toast.makeText(this, "Fight started!", Toast.LENGTH_LONG).show()
                true
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "This fight already started!", Toast.LENGTH_LONG).show()
            false
        }
        finally {
            hideProgress()
        }
    }

    fun rewardUser(user: String): Boolean {
        return try{
            showProgress()
            appContract.tokenReward(100.toBigInteger(), user).sendAsync().get()
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

    private fun showProgress() {
        progress.visibility = View.VISIBLE
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun hideProgress() {
        progress.visibility = View.INVISIBLE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}
