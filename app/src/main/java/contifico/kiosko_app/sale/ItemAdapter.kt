package contifico.kiosko_app.sale

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import contifico.kiosko_app.R
import java.util.*

class ItemAdapter(
    private val context: Context, private val menuItems: ArrayList<MenuItem>, icon_width: Int,
    icon_height: Int, item_textColor: Int,
    item_backgroundColor: Int, item_marginTop: Int,
    item_marginBottom: Int, item_marginLeft: Int, item_marginRight: Int,
    item_colorSelected: Int, item_textSize: Int, background_notification: Int
) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private var icon_width = 100
    private var icon_height = 100
    private var item_textColor = Color.parseColor("#000000")
    private var item_backgroundColor = Color.parseColor("#FFFFFF")
    private var item_marginTop = 0
    private var item_marginBottom = 0
    private var item_marginLeft = 0
    private var item_marginRight = 0
    private var item_colorSelected = Color.parseColor("#2063f1")
    private var item_textSize = 5
    private val background_notification: Int
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        val menuItem = menuItems[position]
        holder.itemView.setBackgroundColor(item_backgroundColor)
        holder.textItem.setText(menuItem.text)
        holder.textItem.setTextColor(item_textColor)
        holder.textItem.textSize = item_textSize.toFloat()
        holder.itemView.setPadding(
            item_marginLeft,
            item_marginTop,
            item_marginRight,
            item_marginBottom
        )
        Picasso.get().load(menuItem.icon).into(holder.icon)
        //holder.icon.setImageResource(menuItem.getIcon());
        val layoutParams = holder.icon.layoutParams
        layoutParams.width = icon_width
        layoutParams.height = icon_height
        holder.icon.layoutParams = layoutParams
        /***holder.notification.setSolidColor(background_notification)
        holder.notification.setStrokeColor(background_notification)
        holder.notification.setTextColor(Color.parseColor("#ffffff"))
        holder.notification.setText(menuItem.getNumNotifications().toString() + "")*/
        if (menuItem.isSelected) {
            holder.selected.setBackgroundColor(item_colorSelected)
        } else {
            holder.selected.setBackgroundColor(Color.parseColor("#00FFFFFF"))
        }
        /***if (menuItem.isNotifications()) {
            holder.notification.setVisibility(View.VISIBLE)
        } else {
            holder.notification.setVisibility(View.GONE)
        }*/
        holder.item_content.setOnClickListener {
            onHSItemClickListener!!.onHSClick(
                menuItem,
                position
            )
        }
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    inner class ItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        //var notification: CircularTextView = itemView.findViewById<View>(R.id.notification) as CircularTextView
        var textItem: TextView = itemView.findViewById<View>(R.id.textViewItem) as TextView
        var icon: ImageView = itemView.findViewById<View>(R.id.imageViewItem) as ImageView
        var selected: View = itemView.findViewById(R.id.viewItemSelected)
        var item_content: RelativeLayout = itemView.findViewById<View>(R.id.item_content) as RelativeLayout
    }

    interface OnHSItemClickListener {
        fun onHSClick(menuItem: MenuItem?, position: Int)
    }

    private var onHSItemClickListener: OnHSItemClickListener? = null
    fun setOnHSItemClickListener(onHSItemClickListener: OnHSItemClickListener?) {
        this.onHSItemClickListener = onHSItemClickListener
    }

    init {
        this.icon_width = icon_width
        this.icon_height = icon_height
        this.item_textColor = item_textColor
        this.item_backgroundColor = item_backgroundColor
        this.item_marginTop = item_marginTop
        this.item_marginBottom = item_marginBottom
        this.item_marginLeft = item_marginLeft
        this.item_marginRight = item_marginRight
        this.item_colorSelected = item_colorSelected
        this.item_textSize = item_textSize
        this.background_notification = background_notification
    }
}
