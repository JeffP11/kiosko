package contifico.kiosko_app.billing

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import contifico.kiosko_app.start.MainActivity.Companion.GLOBAL
import contifico.kiosko_app.R.layout.activity_pay_processing
import kotlinx.android.synthetic.main.activity_pay_processing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.droidsonroids.gif.GifImageView

class PayProcessing : AppCompatActivity() {

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        window.decorView.systemUiVisibility = GLOBAL
    }

    /*** Process context, card or cash ***/
    private lateinit var process: String

    /*** Pay Processing screen ***/
    private lateinit var minBanner: ImageView
    private lateinit var firstBody: ConstraintLayout
    private lateinit var firstImg: GifImageView
    private lateinit var cashWaitTxt: TextView
    private lateinit var cashMakingTxt: TextView
    private lateinit var cardWaitTxt: TextView
    private lateinit var cardMakingTxt: TextView
    private lateinit var cardImg: ImageView
    private lateinit var foot: LinearLayout

    /*** Pay Processed screen ***/
    private lateinit var secondBody: ConstraintLayout
    private lateinit var secondImg: ImageView
    private lateinit var cashMadeTxt: TextView
    private lateinit var cardMadeTxt: TextView

    /*** Final screen, THANK YOU ***/
    private lateinit var final: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_pay_processing)

        process = intent.getStringExtra("extraMessage")!!

        minBanner = imageViewBanner
        firstBody = waitingBody
        firstImg = waitingImg
        cashWaitTxt = waitingText
        cashMakingTxt = makingPayOrder
        cardWaitTxt = waitingText2
        cardMakingTxt = processingCardPay
        cardImg = swipeCard
        foot = footer

        secondBody = finalBody
        secondImg = billImg
        cashMadeTxt = finalTextCash
        cardMadeTxt = finalTextCard

        final = finalScreen

        if (process.compareTo("cash") == 0) {
            /*** TO DO: Cash process ***/
            CoroutineScope(IO).launch {
                fakeApiRequest(0)
            }
        }
        if (process.compareTo("card") == 0) {
            /*** TO DO: Card process ***/
            CoroutineScope(IO).launch {
                fakeApiRequest(1)
            }
        }

    }

    private fun showFirstScreen(int: Int) {
        minBanner.visibility = View.VISIBLE
        firstBody.visibility = View.VISIBLE
        foot.visibility = View.VISIBLE
        if (int == 0) {
            cashWaitTxt.visibility = View.VISIBLE
        }
        if (int == 1) {
            cashWaitTxt.visibility = View.GONE
        }
    }

    private fun showCashWaiting() {
        firstImg.visibility = View.VISIBLE
    }

    private fun showCashWaiting2() {
        cashWaitTxt.visibility = View.INVISIBLE
        cashMakingTxt.visibility = View.VISIBLE
    }

    private fun showCardWaiting() {
        firstImg.visibility = View.VISIBLE
        cashWaitTxt.visibility = View.GONE
        firstImg.visibility = View.GONE
        cardWaitTxt.visibility = View.VISIBLE
        cardImg.visibility = View.VISIBLE
    }

    private fun showCardWaiting2() {
        cardWaitTxt.visibility = View.GONE
        cardImg.visibility = View.GONE
        cashWaitTxt.visibility = View.INVISIBLE
        cardMakingTxt.visibility = View.VISIBLE
        firstImg.visibility = View.VISIBLE
    }

    private fun showcashMade() {
        firstBody.visibility = View.GONE
        cashMadeTxt.visibility = View.VISIBLE
        cardMadeTxt.visibility = View.GONE
        secondBody.visibility = View.VISIBLE
    }

    private fun showcardMade() {
        firstBody.visibility = View.GONE
        cashMadeTxt.visibility = View.GONE
        cardMadeTxt.visibility = View.VISIBLE
        secondBody.visibility = View.VISIBLE
    }

    private fun showFinalScreen() {
        minBanner.visibility = View.GONE
        secondBody.visibility = View.GONE
        foot.visibility = View.GONE
        final.visibility = View.VISIBLE
    }

    private suspend fun setTextOnMainThread(call: Int) {
        when (call) {
            1 ->
                withContext (Main) {
                    showFirstScreen(0)
                    showCashWaiting()
                }
            2 ->
                withContext (Main) {
                    showCashWaiting2()
                }
            3 ->
                withContext (Main) {
                    showcashMade()
                }
            4 ->
                withContext (Main) {
                    showFirstScreen(1)
                    showCardWaiting()
                }
            5 ->
                withContext (Main) {
                    showCardWaiting2()
                }
            6 ->
                withContext (Main) {
                    showcardMade()
                }
            7 ->
                withContext (Main) {
                    showFinalScreen()
                }
            else -> print("Error\n")
        }
    }

    private suspend fun fakeApiRequest(int: Int) {
        logThread("fakeApiRequest")

        if (int == 0) {
            val result1 = getResult1FromApi() // cash
            if (result1 == "Result 1") {
                setTextOnMainThread(1)

                val result2 = getResult2FromApi() // wait until job is done
                if (result2 == "Result 2") {
                    setTextOnMainThread(2)
                }

                val result3 = getResult3FromApi() // wait until job is done
                if (result3 == "Result 3") {
                    setTextOnMainThread(3)
                }

                val result7 = getResult7FromApi() // wait until job is done
                if (result7 == "Result 7") {
                    setTextOnMainThread(7)
                }
            }
        }

        if (int == 1) {
            val result4 = getResult4FromApi() // card
            if (result4 == "Result 4") {
                logThread("fakeApiRequest")
                setTextOnMainThread(4)

                val result5 = getResult5FromApi() // wait until job is done
                if (result5 == "Result 5") {
                    setTextOnMainThread(5)
                }

                val result6 = getResult6FromApi() // wait until job is done
                if (result6 == "Result 6") {
                    setTextOnMainThread(6)
                }

                val result7 = getResult7FromApi() // wait until job is done
                if (result7 == "Result 7") {
                    setTextOnMainThread(7)
                }
            }
        }
    }


    private suspend fun getResult1FromApi(): String {
        logThread("getResult1FromApi")
        delay(1000) // Le hace delay a la corutina dentro del thread pero no afecta el thread
        return "Result 1"
    }

    private suspend fun getResult2FromApi(): String {
        logThread("getResult2FromApi")
        delay(3000)
        return "Result 2"
    }

    private suspend fun getResult3FromApi(): String {
        logThread("getResult3FromApi")
        delay(5000)
        return "Result 3"
    }

    private suspend fun getResult4FromApi(): String {
        logThread("getResult4FromApi")
        delay(1000)
        return "Result 4"
    }

    private suspend fun getResult5FromApi(): String {
        logThread("getResult5FromApi")
        delay(3000)
        return "Result 5"
    }

    private suspend fun getResult6FromApi(): String {
        logThread("getResult6FromApi")
        delay(5000)
        return "Result 6"
    }

    private suspend fun getResult7FromApi(): String {
        logThread("getResult7FromApi")
        delay(4000)
        return "Result 7"
    }

    private fun logThread(methodName: String){
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }

}