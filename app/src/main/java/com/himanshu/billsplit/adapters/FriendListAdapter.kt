package com.himanshu.billsplit.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.billsplit.R
import com.himanshu.billsplit.database.FriendEntity

class FriendListAdapter(val context: Context, private val itemList: ArrayList<FriendEntity>):RecyclerView.Adapter<FriendListAdapter.FriendViewHolder>() {

    var checkList: ArrayList<FriendEntity> = arrayListOf()

    class FriendViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtFriendName: TextView = view.findViewById(R.id.txtFriendName)
        val imgCheck: ImageView = view.findViewById(R.id.imgCheck)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_single_friend,parent,false)
        return FriendViewHolder(view)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val itemObject = itemList[position]
        holder.txtFriendName.text = itemObject.friend_name
        holder.imgCheck.setOnClickListener {
            if(checkList.contains(itemObject)) {
                holder.imgCheck.setImageResource(R.drawable.ic_cb_empty)
                checkList.remove(itemObject)
            } else {
                holder.imgCheck.setImageResource(R.drawable.ic_cb_full)
                checkList.add(itemObject)
            }
        }
    }
}