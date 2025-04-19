package com.aliza.simiex.data.db.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aliza.simiex.data.models.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Upsert
    suspend fun upsertUser(user: UserEntity)

}
