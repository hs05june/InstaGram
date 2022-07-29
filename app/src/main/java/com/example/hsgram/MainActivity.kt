package com.example.hsgram

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var btnlogin: Button
    lateinit var btnsignup: Button

    class MyClass{
        companion object{
            var activity: Activity? = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnlogin = findViewById(R.id.loginButton)
        btnsignup = findViewById(R.id.signupButton)
        MyClass.activity = this@MainActivity

    btnlogin.setOnClickListener{
        val intent = Intent(this,Login::class.java)
        startActivity(intent)
    }

    btnsignup.setOnClickListener{
        val intent1 = Intent(this,SignUp::class.java)
        startActivity(intent1)
    }
    }
}