package com.himanshu.billsplit

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.himanshu.billsplit.adapters.FriendShareAdapter
import com.himanshu.billsplit.database.attachs.AttachEntity
import com.himanshu.billsplit.database.attachs.DBAsyncTaskAttach
import com.himanshu.billsplit.database.expenses.DBAsyncTaskExpense
import com.himanshu.billsplit.database.expenses.ExpenseEntity
import com.himanshu.billsplit.database.friends.DBAsyncTaskFriend
import com.himanshu.billsplit.database.friends.FriendEntity
import com.himanshu.billsplit.databinding.ActivityFinalBinding
import java.math.BigDecimal
import java.text.DateFormat
import java.util.*


class FinalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinalBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerAdapter: FriendShareAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@FinalActivity,R.layout.activity_final)
        val list: ArrayList<FriendEntity> = this.intent.extras?.getParcelableArrayList<FriendEntity>("ListOfFriends") as ArrayList<FriendEntity>
        val totalCost = this.intent.getDoubleExtra("TotalCost",0.00)
        val indCost = totalCost.div(list.size)
        binding.txtCost.text = BigDecimal(totalCost).toPlainString()
        linearLayoutManager = LinearLayoutManager(this@FinalActivity)
        recyclerAdapter = FriendShareAdapter(this@FinalActivity, list,indCost)
        binding.recyclerExpense.layoutManager = linearLayoutManager
        binding.recyclerExpense.adapter = recyclerAdapter

        binding.btnAddExp.setOnClickListener {
            var total = 0.00
            val expenseArrayList: ArrayList<Double> = arrayListOf()
            for(i in 0 until list.size) {
                val view = binding.recyclerExpense[i]
                val editText = view.findViewById<EditText>(R.id.etShare)
                val share = editText.text.toString().trim()
                if(share.isEmpty()) {
                    editText.error = "Add a value here"
                    editText.requestFocus()
                    break
                } else {
                    total += share.toDouble()
                    expenseArrayList.add(share.toDouble())
                }
            }
            when {
                list.isEmpty() -> {
                    Toast.makeText(this,"Add a friend or choose yourself",Toast.LENGTH_LONG).show()
                }
                BigDecimal(total).setScale(4,BigDecimal.ROUND_DOWN) == BigDecimal(totalCost).setScale(4,BigDecimal.ROUND_DOWN) -> {

                    val id = DateFormat.getDateTimeInstance().format(Date())
                    var check = DBAsyncTaskExpense(applicationContext, ExpenseEntity(id,totalCost),1).execute().get()
                    if(!check) {
                        Toast.makeText(this,"Some Error Occurred",Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    for(i in 0 until list.size) {
                        list[i].debt += expenseArrayList[i]
                        check = DBAsyncTaskFriend(applicationContext, list[i],3).execute().get()
                        if(!check) {
                            Toast.makeText(this,"Some Error Occurred",Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        check = DBAsyncTaskAttach(applicationContext,
                            AttachEntity(
                                UUID.randomUUID().toString(),
                                id,
                                list[i].friend_name,
                                expenseArrayList[i]),
                            1).execute().get()
                        if(!check) {
                            Toast.makeText(this,"Some Error Occurred",Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                    }
                    val intent  = Intent(this@FinalActivity,MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                else -> Toast.makeText(this,"Not distributed all the expenses properly",Toast.LENGTH_SHORT).show()
            }
        }
    }

}
