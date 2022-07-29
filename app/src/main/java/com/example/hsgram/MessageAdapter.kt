package com.example.hsgram


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(private val mList: ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item

        if(viewType==1){
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.sent, parent, false)
            return SentViewHolder(view)
        }
        else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.receive,parent,false)
            return ReceiveViewHolder(view)
        }

    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            val message = mList[position]
        if (holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
            holder.textView.text = message.message
        }
        else{
            val viewHolder = holder as ReceiveViewHolder
            holder.textView.text = message.message
        }

        // sets the image to the imageview from our itemHolder class
//        holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class

    }

    override fun getItemViewType(position: Int): Int {
        val message = mList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(message.senderid)){
            return 1;
        }
        else{
            return 2;
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class SentViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView4)
    }
    class ReceiveViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView5)
    }
}