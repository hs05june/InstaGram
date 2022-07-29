package com.example.hsgram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;
    private lateinit var loginbtn: Button
    private lateinit var getEmail: EditText
    private lateinit var getPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        loginbtn = findViewById(R.id.button)
        auth = FirebaseAuth.getInstance()
        getEmail = findViewById(R.id.editTextPhone)
        getPassword = findViewById(R.id.editTextTextPassword)

        loginbtn.setOnClickListener{
            var email = getEmail.text.toString();
            var password = getPassword.text.toString()
            login(email,password)
        }
    }
    fun login(email:String,password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@Login,"Logged In Succesfully",Toast.LENGTH_LONG).show()
                    var intent = Intent(this@Login,Chat::class.java)
                    finish()
                    MainActivity.MyClass.activity?.finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login,"Wrong email or password",Toast.LENGTH_SHORT).show()
                }
            }
    }
}