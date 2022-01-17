package com.example.carsdatasaving.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.carsdatasaving.db.Car


@Dao
interface CarDao {

    @Insert
    fun addNewCar(car : Car)

    @Update
    fun updateCar (car : Car)

    @Delete
    fun delete (car : Car)

    @Query("select * from car_database")
    fun getAllCars(): MutableList<Car>?

    @Query("select * from car_database where id ==:carId")
    fun getCar(carId : String) : Car

}