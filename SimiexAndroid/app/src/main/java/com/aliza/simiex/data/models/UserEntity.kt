package com.aliza.simiex.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aliza.simiex.utils.constants.USER_FIRSTNAME
import com.aliza.simiex.utils.constants.USER_IMAGE
import com.aliza.simiex.utils.constants.USER_INVENTORY
import com.aliza.simiex.utils.constants.USER_LASTNAME
import com.aliza.simiex.utils.constants.USER_NUMBER_PHONE
import com.parse.ParseObject

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val id: Int = 1,
    val firstName: String,
    val lastName: String,
    val numberPhone: String,
    val userImage: String?,
    val inventory: String?,
) {
    companion object {
        fun fromParseObject(parseObject: ParseObject?): UserEntity {
            return UserEntity(
                firstName = parseObject?.getString(USER_FIRSTNAME) ?: "",
                lastName = parseObject?.getString(USER_LASTNAME) ?: "",
                numberPhone = parseObject?.getString(USER_NUMBER_PHONE) ?: "",
                userImage = parseObject?.getParseFile(USER_IMAGE)?.url,
                inventory = parseObject?.getString(USER_INVENTORY) ?: ""
            )
        }
    }
}