package com.himanshu.billsplit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.himanshu.billsplit.database.FriendEntity
import com.himanshu.billsplit.databinding.ActivityFinalBinding

class FinalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinalBinding
    var cost = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@FinalActivity,R.layout.activity_final)
        binding.txtCost.text = "$cost"
        val list = this.intent.extras?.getParcelableArrayList<FriendEntity>("ListOfFriends")
        Toast.makeText(this,"$list",Toast.LENGTH_LONG).show()
    }
}
