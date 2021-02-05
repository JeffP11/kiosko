package contifico.kiosko_app.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import contifico.kiosko_app.error.Error as Error1
import contifico.kiosko_app.R.layout.activity_admin_panel
import contifico.kiosko_app.R

class AdminPanelActivity : AppCompatActivity() {

    private val extraMessage = "mialvera.co.zu.myapplication.ADMIN"
    private var typeMessage = ""

    /**
    private lateinit var realmMemory: Realm
     **/
    private lateinit var realmLocale: Realm
    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_admin_panel)

        val localConfig = RealmConfiguration.Builder()
            .name("localDB.realm")
            //.schemaVersion(1)
            //.migration(MyMigration())
            .deleteRealmIfMigrationNeeded()
            .build()
        realmLocale = Realm.getInstance(localConfig)

        /***
        val memoryConfig = RealmConfiguration.Builder()
        .name("memoryDB.realm")
        .inMemory()
        .build()
        realmMemory = Realm.getInstance(memoryConfig)
        */

        val btnReset = findViewById<Button>(R.id.btnReset)
        val btnConfig = findViewById<Button>(R.id.btnConfig)
        val btnStock = findViewById<Button>(R.id.btnStock)

        btnReset.setOnClickListener {
            //resetDataBase()
        }

        btnConfig.setOnClickListener {
            val intent = Intent(this, AdminConfigActivity::class.java)
            startActivity(intent)
        }

        btnStock.setOnClickListener {
            typeMessage = "404"
            val intent = Intent(this, Error1::class.java).apply {
                putExtra("extraMessage", typeMessage)
            }
            startActivity(intent)
        }

        btnStock.setOnLongClickListener {
            typeMessage = "Not Found"
            val intent = Intent(this, Error1::class.java).apply {
                putExtra("extraMessage", typeMessage)
            }
            startActivity(intent)
            return@setOnLongClickListener true
        }
    }
}
