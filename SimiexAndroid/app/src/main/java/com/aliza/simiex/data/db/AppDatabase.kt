package com.aliza.simiex.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aliza.simiex.data.db.daos.UserDao
import com.aliza.simiex.data.models.UserEntity
import com.aliza.simiex.utils.converter.ParseFileConverter

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
@TypeConverters(ParseFileConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}