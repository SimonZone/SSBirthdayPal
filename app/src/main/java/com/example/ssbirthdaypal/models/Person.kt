package com.example.ssbirthdaypal.models

import java.io.Serializable

data class Person(
    val id: Int,
    val userId: String?,
    val name: String,
    val birthYear: Int,
    val birthMonth: Int,
    val birthDayOfMonth: Int,
    val remarks: String?,
    val pictureUrl: Any?,
    val age: Int?
) : Serializable {

    override fun toString(): String {
        return "$name, aged: $age"
    }
}