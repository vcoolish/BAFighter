package com.bafighter.titaniumlabs.bafighter

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bafighter.titaniumlabs.bafighter.blockchain.Character
import com.bafighter.titaniumlabs.bafighter.blockchain.Game
import kotlinx.android.synthetic.main.activity_fight.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.doAsync
import org.web3j.abi.EventEncoder
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ManagedTransaction
import java.math.BigInteger
import android.animation.Animator
import android.view.ViewAnimationUtils
import android.os.Build
import android.annotation.TargetApi
import android.view.View
import android.view.WindowManager
import rx.Subscription


class FightActivity : AppCompatActivity() {

    enum class BodyPart {
        HEAD, BODY, FEET
    }

    var hitType = BodyPart.HEAD.ordinal
    var blockType = BodyPart.HEAD.ordinal

    lateinit var enemyContract: Character
    lateinit var characterContract: Character
    lateinit var gameContract: Game

    lateinit var gameAddress: String
    lateinit var characterAddress: String
    lateinit var userAddress: String
    lateinit var enemyAddress: String

    lateinit var creds: Credentials
    lateinit var web3: Web3j

    lateinit var initialCharacterHelth: BigInteger
    lateinit var initialEnemyHelth: BigInteger
    var firstHit = true

    lateinit var subscribtion: Subscription

    lateinit var reputation: BigInteger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fight)

        character.backgroundColor = Color.TRANSPARENT
        enemy.backgroundColor = Color.TRANSPARENT
        left_view.minimumHeight = left_view.width
        right_view.minimumHeight = right_view.width

        creds = Credentials.create(getSharedPreferences("Account", Context.MODE_PRIVATE).getString("private_key", ""))
        userAddress = creds.address
        web3 = Web3jFactory.build(HttpService(MainActivity.SERVER))

        characterAddress = getSharedPreferences("Account", Context.MODE_PRIVATE).getString("character", "")
        if (setContracts()) {
            reputation = getRep()
            block_group.setOnCheckedChangeListener { _, i ->
                when (i) {
                    R.id.block_head -> blockType = BodyPart.HEAD.ordinal
                    R.id.block_body -> blockType = BodyPart.BODY.ordinal
                    R.id.block_feet -> blockType = BodyPart.FEET.ordinal
                }
            }
            hit_group.setOnCheckedChangeListener { _, i ->
                when (i) {
                    R.id.hit_head -> hitType = BodyPart.HEAD.ordinal
                    R.id.hit_body -> hitType = BodyPart.BODY.ordinal
                    R.id.hit_feet -> hitType = BodyPart.FEET.ordinal
                }
            }

            val gameSet = getGameSet()

            if (gameSet != "0x0000000000000000000000000000000000000000") {
                setEnemy()
                setEventListener()

                hit.setOnClickListener {
                    if (performHit()) {
                        val characterHealth = getCharacterHealth()
                        val enemyHealth = getEnemyHealth()
                        character_health.text = "$characterHealth HP"
                        enemy_health.text = "$enemyHealth HP"

                        animateHit()
                        if (characterHealth.toInt() == 0) {
                             character.backgroundColor = Color.RED
                             Toast.makeText(this, "You are crashed. --rep", Toast.LENGTH_SHORT).show()
                        }
                        else if (characterHealth.toInt() == 0 && enemyHealth.toInt() == 0){
                            enemy.backgroundColor = Color.RED
                            character.backgroundColor = Color.RED
                            Toast.makeText(this, "It's a DRAW!", Toast.LENGTH_SHORT).show()
                        }
                        else if( enemyHealth.toInt() == 0){
                                enemy.backgroundColor = Color.RED
                                Toast.makeText(this, "You destroyed your enemy!!! ++rep", Toast.LENGTH_SHORT).show()
                            }


                    } else {
                        Toast.makeText(this, "Not your turn!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                hit.setOnClickListener {
                    Toast.makeText(this, "Not your fight!", Toast.LENGTH_SHORT).show()
                }.also { hit.performClick() }
            }
        } else {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
            recreate()
        }
    }

    fun getCharacterHealth(): BigInteger {
        return try {
            showProgress()
            characterContract.health().sendAsync().get()
        } catch (e: Exception) {
            e.printStackTrace()
            return (-1).toBigInteger()
        }
        finally {
            hideProgress()
        }
    }

    fun getEnemyHealth(): BigInteger {
        return try {
            showProgress()
            enemyContract.health().sendAsync().get()
        } catch (e: Exception) {
            e.printStackTrace()
            return (-1).toBigInteger()
        }
        finally {
            hideProgress()
        }
    }

    fun performHit(): Boolean {
        return try {
            showProgress()
            gameContract.hit(hitType.toBigInteger(), blockType.toBigInteger()).sendAsync().get()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
        finally {
            hideProgress()
        }
    }

    fun getRep(): BigInteger {
        return try {
            showProgress()
            characterContract.reputation().sendAsync().get()
        } catch (e: Exception) {
            e.printStackTrace()
            return 0.toBigInteger()
        }
        finally {
            hideProgress()
        }
    }

    fun getGameSet(): String {
        return try {
            showProgress()
            characterContract.game().sendAsync().get().toString()
        } catch (e: Exception) {
            e.printStackTrace()
            return "0x0000000000000000000000000000000000000000"
        }
        finally {
            hideProgress()
        }
    }

    fun setEnemy() {
        try {
            showProgress()
            enemyAddress = gameContract.secondCharacter().sendAsync().get().toString()
            if (enemyAddress == characterAddress) {
                enemyAddress = gameContract.firstCharacter().sendAsync().get().toString()
            }
            enemyContract = Character.load(
                    enemyAddress, web3, creds,
                    ManagedTransaction.GAS_PRICE, BigInteger.valueOf(6800000))
            val health = enemyContract.health().sendAsync().get()
            enemy_health.text = "$health HP"
            initialEnemyHelth = health
            initialCharacterHelth = characterContract.health().sendAsync().get()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        finally {
            hideProgress()
        }
    }

    fun setContracts(): Boolean {
        return try {
            characterContract = Character.load(
                    characterAddress, web3, creds,
                    ManagedTransaction.GAS_PRICE, BigInteger.valueOf(6800000))
            gameAddress = getGameSet()
            gameContract = Game.load(
                    gameAddress, web3, creds,
                    ManagedTransaction.GAS_PRICE, BigInteger.valueOf(6800000))
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun setEventListener() {
        val filter = EthFilter(DefaultBlockParameterName.PENDING, DefaultBlockParameterName.LATEST, characterContract.contractAddress.substring(2))
        val encodedEventSignature = EventEncoder.encode(Character.MATCHRESULT_EVENT)
        filter.addSingleTopic(encodedEventSignature)

        doAsync {
            subscribtion = web3.ethLogObservable(filter).subscribe({ event ->
                println("yougotnoerror ${event.data}")
                parent.runOnUiThread {
                    when(event.data){
                        "0x00" -> Toast.makeText(this@FightActivity, "You've WON!", Toast.LENGTH_LONG).show()
                        "0x01" -> Toast.makeText(this@FightActivity, "You crashed!", Toast.LENGTH_LONG).show()
                        "0x02" -> Toast.makeText(this@FightActivity, "It's a draw!", Toast.LENGTH_LONG).show()
                    }
                }
            }, { onError ->
                println("yougoterror")
                onError.printStackTrace()
            })
        }.get()
    }

    override fun onDestroy() {
        if(::subscribtion.isInitialized && !subscribtion.isUnsubscribed)
            subscribtion.unsubscribe()
        super.onDestroy()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createCircularReveal(leftAndroid: View, rightAndroid: View) {
        leftAndroid.backgroundColor = Color.RED
        val centerX = leftAndroid.x.toInt() + leftAndroid.width / 2
        val centerY = leftAndroid.y.toInt()
        val finalRadius = Math.hypot(rightAndroid.width.toDouble(), rightAndroid.height.toDouble()).toFloat()

        val revealAnimator = ViewAnimationUtils.createCircularReveal(rightAndroid,
                centerX, centerY, 0f, finalRadius)
        rightAndroid.visibility = View.VISIBLE
        revealAnimator.start()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun hideCircularReveal(leftAndroid: View, rightAndroid: View) {
        val cx = leftAndroid.x.toInt() + leftAndroid.width / 2
        val cy = leftAndroid.y.toInt()

        val initialRadius = Math.hypot(rightAndroid.width.toDouble(),
                rightAndroid.height.toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(rightAndroid, cx, cy, initialRadius, 0f)

        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }
            override fun onAnimationEnd(animation: Animator) {
                rightAndroid.visibility = View.INVISIBLE
            }
            override fun onAnimationCancel(animation: Animator) {
            }
            override fun onAnimationRepeat(animation: Animator) {
            }
        })
        anim.start()
        leftAndroid.backgroundColor = Color.TRANSPARENT
    }

    private fun animateHit() {
        createCircularReveal(character, left_view)
        createCircularReveal(enemy, right_view)
        hideCircularReveal(character, left_view)
        hideCircularReveal(enemy, right_view)
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
