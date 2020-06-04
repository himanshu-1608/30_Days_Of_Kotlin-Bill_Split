package com.himanshu.billsplit.database.attachs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AttachDao {
    @Insert
    fun insertAttach(attachEntity: AttachEntity)

    @Query("SELECT * FROM Attaches")
    fun getAllAttaches(): List<AttachEntity>
}