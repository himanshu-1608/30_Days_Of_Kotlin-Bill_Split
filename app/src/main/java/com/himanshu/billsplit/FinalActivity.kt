package com.himanshu.billsplit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.himanshu.billsplit.database.FriendEntity

class FinalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)
        val list = this.intent.extras?.getParcelableArrayList<FriendEntity>("ListOfFriends")
        //Toast.makeText(this,"$list",Toast.LENGTH_LONG).show()
        val objectMap: MutableMap<Any, Any> = HashMap()
        val phone: MutableMap<Any, Any> = HashMap()

        phone["mobile"] = 1234567890
        objectMap["FirstName"] = "Fred"
        objectMap["LastName"] = "Poulsen"
        objectMap["Age"] = "18"
        objectMap["Phone"] = phone
        Toast.makeText(this,"$objectMap",Toast.LENGTH_LONG).show()
    }
}
