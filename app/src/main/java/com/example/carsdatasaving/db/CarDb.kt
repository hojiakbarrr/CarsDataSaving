package com.example.carsdatasaving.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database (entities = [Car :: class], version = 1)
abstract class CarDb: RoomDatabase() {


    abstract fun getCarDao(): CarDao

//    companion object{
//        @Volatile
//        private var INSTANCE: CarDb ?=null
//
//        fun getDatabase (context: Context):CarDb{
//            val tempInstance = INSTANCE
//            if(tempInstance != null){
//                return tempInstance
//            }
//            synchronized(this){
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    CarDb::class.java,"car_database"
//                ).build()
//                INSTANCE = instance
//                return instance
//            }
//
//        }
//
//    }
}