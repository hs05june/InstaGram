package com.example.hsgram

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class Chat : AppCompatActivity() {
    lateinit var userView:RecyclerView
    lateinit var userList: ArrayList<User>
    lateinit var adapter: CustomAdapter
    lateinit var auth: FirebaseAuth
    lateinit var DbRef : DatabaseReference

    class MeClass{
        companion object{
            var activity: Activity? = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar?.title="CONTACTS"

        MeClass.activity= this@Chat
        auth = FirebaseAuth.getInstance()
        DbRef = FirebaseDatabase.getInstance().getReference()
        userList = ArrayList()
        DbRef.child("user").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                adapter.notifyDataSetChanged()
                for(member in  snapshot.children){
                    var currentUser = member.getValue(User::class.java)
                    if(auth.currentUser?.uid!=currentUser?.uid){ userList.add(currentUser!!) }
//                    Log.e("user",currentUser.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        userView = findViewById(R.id.recyclerView)
        adapter = CustomAdapter(userList)
        userView.layoutManager = LinearLayoutManager(this)
        userView.adapter = adapter

        userView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.button3){
            auth.signOut()
            Toast.makeText(this@Chat,"Logged Out",Toast.LENGTH_LONG)
            finish()
            var intent = Intent(this@Chat,MainActivity::class.java)
            startActivity(intent)
            return true
        }
        return true
    }
}