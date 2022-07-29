package com.example.hsgram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    lateinit var btnsignup:Button
    lateinit var getEmail:EditText
    lateinit var getPassword:EditText
    lateinit var getName:EditText
    lateinit var auth: FirebaseAuth
    lateinit var DbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()
        btnsignup = findViewById(R.id.button2)
        getEmail = findViewById(R.id.editTextEmail)
        getPassword = findViewById(R.id.editTextTextPassword2)
        getName = findViewById(R.id.editTextTextPersonName)
        auth = FirebaseAuth.getInstance()

        btnsignup.setOnClickListener{
            var email = getEmail.text.toString()
            var name = getName.text.toString()
            var password = getPassword.text.toString()

            signUp(email, name, password)
        }
    }

    fun signUp(email:String,name:String,password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "New account Created",
                        Toast.LENGTH_SHORT).show()
                    addUserToDatabase(name,email,auth.currentUser?.uid!!)
                    var intent = Intent(this@SignUp,Chat::class.java)
                    finish()
                    MainActivity.MyClass.activity?.finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUp, "Authentication Failed",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun addUserToDatabase(name:String,email:String,uid:String){
        DbRef = FirebaseDatabase.getInstance().getReference()

        DbRef.child("user").child(uid).setValue(User(name,email,uid))
    }

}