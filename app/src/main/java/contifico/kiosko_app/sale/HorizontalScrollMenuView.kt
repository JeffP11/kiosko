package contifico.kiosko_app.sale

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import contifico.kiosko_app.R
import java.util.*

class HorizontalScrollMenuView : LinearLayout {
    private var context: AppCompatActivity?
    private var recyclerView: RecyclerView? = null
    private var itemAdapter: ItemAdapter? = null
    private val menuItems = ArrayList<MenuItem>()

    /**
     * @return retorna  la posicion del item seleccionado en el menu
     */
    var itemSeletected = 0
        private set

    //attrs
    private var icon_width = 200
    private var icon_height = 200
    private var backgroundMenuColor = Color.parseColor("#FFFFFF")
    private var backgroundNotifications = Color.parseColor("#FF0000")
    private var item_textColor = Color.parseColor("#000000")
    private var item_backgroundColor = Color.parseColor("#FFFFFF")
    private var item_marginTop = 0
    private var item_marginBottom = 0
    private var item_marginLeft = 0
    private var item_marginRight = 0
    private var item_colorSelected = Color.parseColor("#0099cc")
    private var item_textSize = 5

    constructor(context: Context?) : super(context) {
        this.context = context as AppCompatActivity?
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        this.context = context as AppCompatActivity
        val a =
            context.obtainStyledAttributes(attrs, R.styleable.HorizontalScrollMenuView, 0, 0)
        icon_height =
            a.getDimensionPixelSize(R.styleable.HorizontalScrollMenuView_icon_height, icon_height)
        icon_width =
            a.getDimensionPixelSize(R.styleable.HorizontalScrollMenuView_icon_width, icon_width)
        backgroundMenuColor = a.getColor(
            R.styleable.HorizontalScrollMenuView_backgroundMenuColor,
            backgroundMenuColor
        )
        backgroundNotifications = a.getColor(
            R.styleable.HorizontalScrollMenuView_backgroundNotifications,
            backgroundNotifications
        )
        item_textColor =
            a.getColor(R.styleable.HorizontalScrollMenuView_item_textColor, item_textColor)
        item_backgroundColor = a.getColor(
            R.styleable.HorizontalScrollMenuView_item_backgroundColor,
            item_backgroundColor
        )
        item_colorSelected =
            a.getColor(R.styleable.HorizontalScrollMenuView_item_colorSelected, item_colorSelected)
        item_marginTop =
            a.getDimensionPixelSize(R.styleable.HorizontalScrollMenuView_item_marginTop, 0)
        item_marginBottom =
            a.getDimensionPixelSize(R.styleable.HorizontalScrollMenuView_item_marginBottom, 0)
        item_marginLeft =
            a.getDimensionPixelSize(R.styleable.HorizontalScrollMenuView_item_marginLeft, 0)
        item_marginRight =
            a.getDimensionPixelSize(R.styleable.HorizontalScrollMenuView_item_marginRight, 0)
        item_textSize = a.getDimensionPixelSize(
            R.styleable.HorizontalScrollMenuView_item_textSize,
            item_textSize
        )
        a.recycle()
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.hscroll_menu, this, true)
        init()
    }

    private fun init() {
        recyclerView = findViewById<View>(R.id.recyclerViewItems) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        itemAdapter = ItemAdapter(
            context!!, menuItems, icon_width, icon_height, item_textColor,
            item_backgroundColor, item_marginTop, item_marginBottom, item_marginLeft,
            item_marginRight, item_colorSelected, item_textSize, backgroundNotifications
        )
        itemAdapter!!.setOnHSItemClickListener(object : ItemAdapter.OnHSItemClickListener {
            override fun onHSClick(menuItem: MenuItem?, position: Int) {
                onHSMenuClickListener!!.onHSMClick(menuItem, position)
            }
        })
        recyclerView!!.adapter = itemAdapter
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        recyclerView!!.setBackgroundColor(backgroundMenuColor)
    }

    /**
     * agrega un nuevo item al menu
     *
     * @param text texto del item
     * @param icon icono para el item
     */
    fun addItem(text: String?, icon: String?) {
        menuItems.add(MenuItem(icon!!, text!!))
    }

    /**
     * agrega un nuevo item al menu, pero este se mostrara como seleccionado
     *
     * @param text    texto del item
     * @param icon    icono para el item
     * @param seleted
     */
    fun addItem(text: String?, icon: String?, seleted: Boolean) {
        menuItems.add(MenuItem(icon!!, text!!, seleted))
    }

    /**
     * agrega un nuevo item al menu, pero este se mostrara como seleccionado
     *
     * @param text    texto del item
     * @param icon    icono para el item
     * @param seleted
     */
    fun addItem(
        text: String?,
        icon: String?,
        seleted: Boolean,
        numNotifications: Int
    ) {
        menuItems.add(MenuItem(icon!!, text!!, seleted))
        menuItems[menuItems.size - 1].numNotifications = numNotifications
    }

    /**
     * agrega un nuevo item al menu, pero este se mostrara como seleccionado
     *
     * @param text texto del item
     */
    fun addItem(text: String?, icon: Int, numNotifications: Int) {
        menuItems[menuItems.size - 1].numNotifications = numNotifications
    }

    /**
     * edita un item del menu
     * @param position          posicion del item en el menu
     * @param text              nuevo texto para el item
     * @param icon              nuevo icono para el item
     * @param showNotifications true o false para decidir si se muestra el badge de notificaciones
     * @param numNotifications  numero de notificaciones en el badge
     */
    fun editItem(
        position: Int,
        text: String?,
        icon: Int,
        showNotifications: Boolean,
        numNotifications: Int
    ) {
        //menuItems.get(position).setIcon(icon);
        menuItems[position].text = text!!
        itemAdapter!!.notifyItemChanged(position)
        itemAdapter!!.notifyDataSetChanged()
    }

    /**
     * muestra todos los items del menu
     */
    fun showItems() {
        itemAdapter!!.notifyItemRangeInserted(0, menuItems.size - 1)
        itemAdapter!!.notifyDataSetChanged()
    }

    /**
     * cambia el estado de un item a seleccionado
     *
     * @param position posicion del item en el menu
     */
    fun setItemSelected(position: Int) {
        if (menuItems.size > 0) {
            for (i in menuItems.indices) {
                if (i == position) menuItems[i].isSelected = (true) else menuItems[i].isSelected = (false)
            }
            itemAdapter!!.notifyItemRangeChanged(0, menuItems.size - 1)
            itemAdapter!!.notifyDataSetChanged()
            recyclerView!!.scrollToPosition(position)
            itemSeletected = position
        }
    }

    /**
     * @return retorna el numero de items que tiene el menu
     */
    fun numItems(): Int {
        return menuItems.size
    }

    /**
     * retorna un item del menu
     *
     * @param position posicion del item en el menu (desde 0)
     * @return
     */
    fun getItem(position: Int): MenuItem {
        return menuItems[position]
    }

    interface OnHSMenuClickListener {
        /**
         * para capturar los eventos cuando se da click sobre un item del menu
         *
         * @param menuItem item seleccionado
         * @param position posicion del item en el menu (iniciando desde 0)
         */
        fun onHSMClick(menuItem: MenuItem?, position: Int)
    }

    private var onHSMenuClickListener: OnHSMenuClickListener? = null
    fun setOnHSMenuClickListener(onHSMenuClickListener: OnHSMenuClickListener?) {
        this.onHSMenuClickListener = onHSMenuClickListener
    }
}