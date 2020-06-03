package com.himanshu.billsplit.database.expenses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.himanshu.billsplit.database.friends.FriendEntity

@Entity(tableName = "Expenses")
data class ExpenseEntity(
    @PrimaryKey val expense_id: Long,
    @ColumnInfo(name = "expense_cost") val expenseCost: Double,
    @ColumnInfo(name = "friends") val friendList: ArrayList<String>
)