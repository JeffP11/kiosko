package contifico.kiosko_app.sale

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import contifico.kiosko_app.start.MainActivity.Companion.GLOBAL
import contifico.kiosko_app.start.MainActivity.Companion.mHideHandler
import contifico.kiosko_app.R.layout.activity_sale
import kotlinx.android.synthetic.main.activity_sale.*

class Sale : AppCompatActivity() {
    private val mHideRunnable = Runnable { hide() }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delayedHide(0)
        hide()
        window.decorView.systemUiVisibility = GLOBAL
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recreate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_sale)

        val horizontalMenu : HorizontalScrollMenuView = categoriesMenu
        initMenu(horizontalMenu)
        Toast.makeText(this, "Hola", Toast.LENGTH_LONG).show()
    }

    fun initMenu(menu: HorizontalScrollMenuView) {
        val string = "https://images.vexels.com/media/users/3/143047/isolated/preview/b0c9678466af11dd45a62163bdcf03fe-icono-plano-de-hamburguesa-de-comida-r--pida-by-vexels.png"
        for (i in 1..8) {
            menu.addItem("product $i", string)
        }
    }

}