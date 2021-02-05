package contifico.kiosko_app.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import contifico.kiosko_app.R
import contifico.kiosko_app.R.layout.activity_main
import contifico.kiosko_app.saleOptions.SaleOptions
import java.util.*

open class MainActivity : AppCompatActivity() {
    private val mHideRunnable = Runnable { hide() }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(0)
    }

    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    companion object {
        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        const val UI_ANIMATION_DELAY = 2

        const val DELAY_MS: Long = 5000

        const val PERIOD_MS: Long  = 5000

        const val GLOBAL: Int = (View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN)

        val mHideHandler = Handler()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        window.decorView.systemUiVisibility = GLOBAL
    }

    // Arreglo de las imagenes que tendran el slider
    var images: Array<Int> = arrayOf(
        R.drawable.bigmac,
        R.drawable.delivery,
        R.drawable.bigmac_3
    )
    // instancia de clase de slider
    var adapter: PagerAdapter = SliderAdapter(this, images)

    // variables para paginacion por puntos
    lateinit var dotsLayout: LinearLayout
    lateinit var dots: Array<ImageView>

    lateinit var viewpager : ViewPager
    // Variables para el uso de hilo
    // Cambiar slider automaticamnete
    lateinit var timer: Timer

    var currentPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        hide()

        viewpager = findViewById(R.id.viewPager)
        viewpager.adapter = adapter

        dotsLayout = findViewById(R.id.dotsLayout)
        dotsLayout.dividerPadding = 20
        createDots(0)

        updatePage()

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                currentPage = position
                createDots(position)
            }
        })

    }

    private fun updatePage(){
        var handler = Handler()
        val update: Runnable = Runnable{
            if(currentPage == images.size){
                currentPage = 0
            }
            viewpager.setCurrentItem(currentPage++, true)
        }
        timer = Timer()
        timer.schedule(object : TimerTask(){
            override fun run(){
                handler.post(update)
            }
        }, DELAY_MS, PERIOD_MS)
    }

    fun createDots(position: Int){
        dotsLayout.removeAllViews()
        dots = Array(images.size) {ImageView(this)}
        for(i in images.indices){
            dots[i] = ImageView(this)
            if (i == position){
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots))
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.inactive_dots))
            }
            var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.setMargins(4,0,4,0)
            dotsLayout.addView(dots[i], params)
        }
    }

    fun orderNow(view: View){
        val intent = Intent(this, SaleOptions::class.java)
        startActivity(intent)
    }

}