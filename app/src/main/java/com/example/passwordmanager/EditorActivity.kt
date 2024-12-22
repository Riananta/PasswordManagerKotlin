package com.example.passwordmanager

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.passwordmanager.data.AppDatabase
import com.example.passwordmanager.data.entity.Password
import com.example.passwordmanager.utils.EncryptionUtil

class EditorActivity : AppCompatActivity() {
    private lateinit var title : EditText
    private lateinit var username : EditText
    private lateinit var password : EditText
    private lateinit var btnSave : Button
    private lateinit var btnBack : Button
    private lateinit var database : AppDatabase
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        title = findViewById(R.id.title)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        btnSave = findViewById(R.id.btn_save)
        btnBack = findViewById(R.id.btn_back)

        database = AppDatabase.getInstance(applicationContext)

        val intent = intent.extras
        if (intent!=null){
            val id = intent.getInt("Id",0)
            val user = database.passwordDao().get(id)

            title.setText(user.title)
            username.setText(user.username)
            password.setText(user.password)
        }

        btnSave.setOnClickListener {
            if (username.text.isNotEmpty() && title.text.isNotEmpty() && password.text.isNotEmpty()) {
                val encryptedPassword = EncryptionUtil.encrypt(password.text.toString())
                sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
                val userId = sharedPreferences.getInt("userId", -1)

                if (intent!=null){
                    // coding edit data
                    database.passwordDao().update(
                        Password(
                            intent.getInt("Id",0),
                            userId,
                            title.text.toString(),
                            username.text.toString(),
                            encryptedPassword
                        )
                    )
                } else {
                    // coding tambah data
                    database.passwordDao().insertAll(
                        Password(
                            null,
                            userId,
                            username.text.toString(),
                            title.text.toString(),
                            encryptedPassword
                        )
                    )
                }

                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Silahkan isi semua data dengan valid.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}