package com.himanshu.billsplit

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEachIndexed
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.himanshu.billsplit.adapters.FriendListAdapter
import com.himanshu.billsplit.database.GetFriends
import com.himanshu.billsplit.databinding.ActivityExpenseBinding

class ExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpenseBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerAdapter: FriendListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@ExpenseActivity,R.layout.activity_expense)
        layoutManager = LinearLayoutManager(this@ExpenseActivity)
        supportActionBar?.title = "Add Expense"
        val friendsList = GetFriends(applicationContext).execute().get()
        if(friendsList.size==0) {
            binding.txtDef.visibility = View.VISIBLE
            binding.recyclerList.visibility = View.GONE
            binding.txtCheck.visibility = View.GONE
        } else {
            binding.txtDef.visibility = View.GONE
            binding.recyclerList.visibility = View.VISIBLE
            binding.txtCheck.visibility = View.VISIBLE
            binding.layoutBill.visibility = View.VISIBLE
        }
        recyclerAdapter = FriendListAdapter(this@ExpenseActivity,friendsList)
        binding.recyclerList.layoutManager = layoutManager
        binding.recyclerList.adapter = recyclerAdapter
        binding.btnSplit.setOnClickListener {
            if(checker(binding.etCost.text.toString().trim())) {
                val intent = Intent(this@ExpenseActivity, FinalActivity::class.java)
                intent.putExtra("ListOfFriends", recyclerAdapter.checkList)
                intent.putExtra("TotalCost",binding.etCost.text)
                startActivity(intent)
            }
        }
    }

    private fun checker(finalString: String): Boolean {
        if(finalString.isEmpty()) {
            binding.etCost.error = "Required"
            binding.etCost.requestFocus()
            return false
        } else if(finalString[0]=='.' || finalString[finalString.length-1]=='.') {
            binding.etCost.error = "Enter valid cost"
            binding.etCost.requestFocus()
            return false
        } else {
            val ind = finalString.indexOf('.')
            val dec = finalString.substring(ind)
            if(dec.length>2 || dec.contains('.')) {
                binding.etCost.error = "Enter valid cost"
                binding.etCost.requestFocus()
                return false
            } else return true
        }
    }

}
