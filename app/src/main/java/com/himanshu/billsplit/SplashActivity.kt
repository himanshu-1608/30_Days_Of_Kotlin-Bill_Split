package com.himanshu.billsplit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.databinding.DataBindingUtil
import com.himanshu.billsplit.database.friends.DBAsyncTaskFriend
import com.himanshu.billsplit.database.friends.FriendEntity
import com.himanshu.billsplit.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@SplashActivity,R.layout.activity_splash)
        sp = getSharedPreferences("DataFile",Context.MODE_PRIVATE)
        if(sp.getString("UserName","User786") == "User786") {
            binding.txtDef.visibility = View.VISIBLE
            binding.etDef.visibility = View.VISIBLE
            binding.txtUser.visibility = View.GONE
            binding.btnOk.visibility = View.VISIBLE
        } else {
            binding.txtDef.visibility = View.GONE
            binding.etDef.visibility = View.GONE
            binding.btnOk.visibility = View.GONE
            binding.txtUser.visibility = View.VISIBLE
            binding.txtUser.text = "Hi, "+sp.getString("UserName","Error")
            Handler().postDelayed({
                val intent = Intent(this@SplashActivity,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            },1000)

        }
        binding.btnOk.setOnClickListener {
            val user = binding.etDef.text.toString().trim()
            if(user.isEmpty()) {
                binding.etDef.error = "Required"
                return@setOnClickListener
            } else {
                sp.edit().putString("UserName",user).apply()
                val check = DBAsyncTaskFriend(applicationContext, FriendEntity(user,0.00),2).execute().get()
                if(check) {
                    val intent = Intent(this@SplashActivity,MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}