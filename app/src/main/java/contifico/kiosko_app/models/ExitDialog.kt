package contifico.kiosko_app.models

import android.app.Dialog
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import contifico.kiosko_app.R
import contifico.kiosko_app.R.layout.dialog_exit
import contifico.kiosko_app.saleOptions.SaleOptions
import contifico.kiosko_app.start.MainActivity.Companion.GLOBAL
import kotlinx.android.synthetic.main.dialog_exit.*

class ExitDialog: Dialog {

    private var sOContext: SaleOptions
    //private var contexto
    var btnAccept: Button
    var btnCancel: Button

    constructor(context: SaleOptions): super(context, R.style.Theme_AppCompat_Light_Dialog_Alert){
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setCancelable(false)
        this.setContentView(dialog_exit)
        this.sOContext = context

        btnAccept = acceptBtn
        btnCancel = cancelBtn

        btnAccept.setOnClickListener {
            /** TO DO */
        }

        btnCancel.setOnClickListener{
            this.dismiss()
        }
    }

    override fun show() {
        // Set alertDialog "not focusable" so nav bar still hiding:
        this.window!!.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        // Set full-sreen mode (immersive sticky):
        this.window!!.decorView.systemUiVisibility = GLOBAL;
        super.show()
        // Set dialog focusable so we can avoid touching outside:
        this.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) this.window!!.decorView.systemUiVisibility = GLOBAL
    }
}