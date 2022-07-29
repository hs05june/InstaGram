package com.example.hsgram

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class CustomAdapter(private val mList: ArrayList<User>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_row_item,parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        // sets the image to the imageview from our itemHolder class
//        item.image?.let { holder.imageView.setImageResource(it) }

        // sets the text to the textview from our itemHolder class
        holder.textView.text = item.name
        holder.textView2.text = item.email

        holder.itemView.setOnClickListener{
            val intent = Intent(Chat.MeClass.activity,PersonChat::class.java)
            intent.putExtra("name",item.name)
            intent.putExtra("uid", item.uid)
            Chat.MeClass.activity?.startActivity(intent)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView2: TextView = itemView.findViewById(R.id.textView2)

//        val imageView: ImageView = itemView.findViewById(R.id.imageView4)
        val textView: TextView = itemView.findViewById(R.id.textView)
//        val textview2:TextView = itemView.findViewById(R.id.textView2)
    }
}
