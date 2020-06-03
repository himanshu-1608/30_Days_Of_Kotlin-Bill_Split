package com.himanshu.billsplit

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.himanshu.billsplit.adapters.FriendShareAdapter
import com.himanshu.billsplit.database.expenses.ExpenseEntity
import com.himanshu.billsplit.database.friends.DBAsyncTaskFriend
import com.himanshu.billsplit.database.friends.FriendEntity
import com.himanshu.billsplit.databinding.ActivityFinalBinding
import java.math.BigDecimal

class FinalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinalBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerAdapter: FriendShareAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@FinalActivity,R.layout.activity_final)
        val list = this.intent.extras?.getParcelableArrayList<FriendEntity>("ListOfFriends")
        val totalCost = this.intent.getDoubleExtra("TotalCost",0.00)
        val indCost = totalCost.div(list!!.size)
        binding.txtCost.text = BigDecimal(totalCost).toPlainString()
        linearLayoutManager = LinearLayoutManager(this@FinalActivity)
        recyclerAdapter = FriendShareAdapter(this@FinalActivity, list)
        binding.recyclerExpense.layoutManager = linearLayoutManager
        binding.recyclerExpense.adapter = recyclerAdapter
        for(i in 0 until list.size) {
            binding.recyclerExpense[i].findViewById<EditText>(R.id.etShare).setText(BigDecimal(indCost).toPlainString())
        }
        binding.btnAddExp.setOnClickListener {
            var total = 0.00
            var expenseArrayList: ArrayList<Double> = arrayListOf()
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
            if(BigDecimal(total).setScale(4,BigDecimal.ROUND_DOWN) == BigDecimal(totalCost).setScale(4,BigDecimal.ROUND_DOWN)) {
                Toast.makeText(this,"$total & ${BigDecimal(totalCost).toPlainString()} & ${total==totalCost}",Toast.LENGTH_SHORT).show()
                for(i in 0 until list.size) {
                    list[i].debt += expenseArrayList[i]
                    val check = DBAsyncTaskFriend(applicationContext, list[i],3).execute().get()
                    if(!check) {
                        Toast.makeText(this,"Some Error Occurred",Toast.LENGTH_SHORT).show()
                    }
                }

            }
            else
                Toast.makeText(this,"Not distributed all the expenses properly",Toast.LENGTH_SHORT).show()
        }
    }
}
