package com.example.passwordmanager

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordmanager.data.AppDatabase
import com.example.passwordmanager.data.entity.Password
import com.example.passwordmanager.data.entity.User
import com.example.passwordmanager.utils.EncryptionUtil

class LoginActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etFullname: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var database: AppDatabase
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.et_username)
        etFullname = findViewById(R.id.et_fullname)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        btnRegister = findViewById(R.id.btn_register)

        database = AppDatabase.getInstance(applicationContext)
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val encryptedPassword = EncryptionUtil.encrypt(password)

                val user = database.userDao().login(username, encryptedPassword)
                if (user != null) {
                    with(sharedPreferences.edit()) {
                        putInt("userId", user.userId ?: -1)
                        apply()
                    }

                    Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Username atau Password salah!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Harap isi semua field!", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegister.setOnClickListener {
            if (etUsername.text.isNotEmpty() && etFullname.text.isNotEmpty() && etPassword.text.isNotEmpty()) {
                val encryptedPassword = EncryptionUtil.encrypt(etPassword.text.toString())
                database.userDao().insertAll(
                    User(
                        null,
                        etUsername.text.toString(),
                        etFullname.text.toString(),
                        encryptedPassword
                    )
                )

                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Silahkan isi semua data dengan valid.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
