package com.example.hsgram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class PersonChat : AppCompatActivity() {

    lateinit var messages: RecyclerView
    lateinit var writeMessage: EditText
    lateinit var sendMessage: ImageView
    lateinit var adapter:MessageAdapter
    lateinit var messsageList:ArrayList<Message>
    lateinit var receiverRoom: String
    lateinit var senderRoom: String
    lateinit var DbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        supportActionBar?.title = name

        messages = findViewById(R.id.messageRecylerView)
        writeMessage = findViewById(R.id.editTextTextPersonName2)
        sendMessage = findViewById(R.id.imageView4)
        DbRef = FirebaseDatabase.getInstance().getReference()

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        messsageList = ArrayList()

        DbRef.child("chats").child(senderRoom).child("messages").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messsageList.clear()
                for(i in snapshot.children){
                    var j = i.getValue(Message::class.java)
                    if (j != null) {
                        messsageList.add(j)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        messages.layoutManager = LinearLayoutManager(this)
        adapter = MessageAdapter(messsageList)
        messages.adapter = adapter

        sendMessage.setOnClickListener{
            val toSend = writeMessage.text.toString()
            DbRef.child("chats").child(senderRoom).child("messages").push().setValue(Message(toSend,senderUid)).addOnSuccessListener {
                DbRef.child("chats").child(receiverRoom).child("messages").push().setValue(Message(toSend,senderUid))
            }
            writeMessage.setText("")
        }
    }
}