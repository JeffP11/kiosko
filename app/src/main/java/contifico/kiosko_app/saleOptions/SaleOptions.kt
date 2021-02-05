package contifico.kiosko_app.saleOptions

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import contifico.kiosko_app.R.layout.activity_sale_options
import contifico.kiosko_app.billing.PayProcessing
import contifico.kiosko_app.models.ExitDialog
import contifico.kiosko_app.sale.Sale
import contifico.kiosko_app.start.MainActivity
import contifico.kiosko_app.start.MainActivity.Companion.GLOBAL
import contifico.kiosko_app.start.MainActivity.Companion.mHideHandler
import kotlinx.android.synthetic.main.activity_sale_options.*

class SaleOptions : AppCompatActivity() {
    private val mHideRunnable = Runnable { hide() }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delayedHide(0)
    }

    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()
    }

    private fun delayedHide(i: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, i.toLong())
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        window.decorView.systemUiVisibility = GLOBAL
    }

    private var bannerTouchCount:Int = 0
    val extraMessage = "SALETYPE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_sale_options)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        hide()

        val banner = imageViewBanner
        banner.setOnClickListener{
            bannerTouchCount++
            if (bannerTouchCount==3){
                Toast.makeText(this,"Has tocado tres veces, entonces abrire el modo desarrollador para ti",Toast.LENGTH_SHORT).show()
                /*val intent = Intent(this, AdminPanelActivity::class.java)
                startActivity(intent)*/
            }
        }

        val toGoOrder = firstOptionImg
        toGoOrder.setOnClickListener {
            ventaParaLlevar()
        }

        val forHereOrder = secondOptionImg
        forHereOrder.setOnClickListener {
            ventaParaServir()
        }

        val exitBtn = exitBtn
        exitBtn.setOnClickListener {
            ExitDialog(this).show()
        }

    }

    override fun onRestart() {
        super.onRestart()
        bannerTouchCount = 0
    }

    fun ventaParaLlevar() {
//        val message = "Pantalla de ventas para llevar"
//        val intent = Intent(this, Sale::class.java).apply {
//            putExtra(extraMessage, message)
//        }
//        startActivity(intent)
        val order_Message = "cash"
        val intent = Intent(this, PayProcessing::class.java).apply {
            putExtra("extraMessage", order_Message)
        }
        startActivity(intent)
    }

    fun ventaParaServir() {
//        val message = "Pantalla de ventas para servir"
//        val intent = Intent(this, Sale::class.java).apply {
//            putExtra(extraMessage, message)
//        }
//        startActivity(intent)
        val order_Message = "card"
        val intent = Intent(this, PayProcessing::class.java).apply {
            putExtra("extraMessage", order_Message)
        }
        startActivity(intent)
    }

    /***
     * Función para reiniciar la aplicación
     */
    fun restartApp() {
        val i = Intent(applicationContext, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.putExtra("EXIT", true)
        startActivity(i)
        finish()
    }

}

/***val order_Message = "card"
val intent = Intent(this, PayProcessing::class.java).apply {
putExtra("extraMessage", order_Message)
}
startActivity(intent)*/