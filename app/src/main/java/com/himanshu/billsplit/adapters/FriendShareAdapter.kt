package com.himanshu.billsplit.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.billsplit.R
import com.himanshu.billsplit.database.friends.FriendEntity
import kotlin.collections.ArrayList

class FriendShareAdapter(val context: Context, private val itemList: ArrayList<FriendEntity>):
    RecyclerView.Adapter<FriendShareAdapter.FriendShareHolder>(){

    class FriendShareHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtFriendName: TextView = view.findViewById(R.id.txtFriendName)
        val etShare: EditText = view.findViewById(R.id.etShare)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendShareHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_single_expense,parent,false)
        return FriendShareHolder(view)
    }
    override fun getItemCount() = itemList.size
    override fun onBindViewHolder(holder: FriendShareHolder, position: Int) {
        val itemObject = itemList[holder.adapterPosition]
        holder.txtFriendName.text = itemObject.friend_name
    }
}