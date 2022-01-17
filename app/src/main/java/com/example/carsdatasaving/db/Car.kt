package com.example.carsdatasaving.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*



@Entity(tableName = "car_database")
data class Car(
    @PrimaryKey
    val id : String = UUID.randomUUID().toString() ,
    var model: String = "",
    var price: String = "" ,
    ): Serializable
