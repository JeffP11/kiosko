package contifico.kiosko_app.error

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import contifico.kiosko_app.start.MainActivity
import contifico.kiosko_app.R.layout.activity_error
import kotlinx.android.synthetic.main.activity_error.*

class Error: AppCompatActivity() {

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        window.decorView.systemUiVisibility = MainActivity.GLOBAL
    }

    /*** Process context, 404 or Not Found ***/
    private lateinit var process: String

    /*** 404 screen ***/
    private lateinit var bodyE404: ConstraintLayout

    /*** Not Found screen ***/
    private lateinit var bodyENF: ConstraintLayout

    private lateinit var exitBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_error)

        process = intent.getStringExtra("extraMessage")!!
        bodyE404 = e404Body
        bodyENF = eNFBody
        exitBtn = eExitBtn

        if (process.compareTo("404") == 0) {
            bodyE404.visibility = View.VISIBLE
        }

        if (process.compareTo("Not Found") == 0) {
            bodyENF.visibility = View.VISIBLE
        }

        exitBtn.setOnClickListener { restartApp() }
    }

    /***
     * Function to restart app.
     * @return Boolean
     */
    private fun restartApp(): Boolean {
        val i = Intent(applicationContext, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.putExtra("EXIT", true)
        startActivity(i)
        finish()
        return true
    }

}