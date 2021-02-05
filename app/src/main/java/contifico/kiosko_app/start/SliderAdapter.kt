package contifico.kiosko_app.start

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import contifico.kiosko_app.R

class SliderAdapter: PagerAdapter {

    var context: Context
    var images: Array<Int>

    lateinit var inflater: LayoutInflater

    constructor(context: Context,images:Array<Int>):super(){
        this.context = context
        this.images = images
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var image: ImageView
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view :View = inflater.inflate(R.layout.item_image_slider, container, false)
        image = view.findViewById(R.id.image)
        image.setImageResource(images[position])
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as FrameLayout)
    }
}