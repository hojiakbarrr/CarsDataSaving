package com.example.carsdatasaving

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.carsdatasaving.db.CarDb
import com.example.carsdatasaving.db.Car
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), CarAdapter.carClickListener {
    private var myAdapter: CarAdapter? = null
    private var myResycler: RecyclerView? = null
    var carsList : MutableList<Car> = ArrayList()
    private var add: FloatingActionButton ? = null
    lateinit var database: CarDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myResycler = findViewById(R.id.recyclerView)
        add = findViewById(R.id.addToList)

        database = Room.databaseBuilder(this,
            CarDb :: class.java,
            "carsDB")
            .allowMainThreadQueries()
            .build()

        carsList = database.getCarDao().getAllCars()!!



//        carsList.add(Car("mercedes", "12500"))
//        carsList.add(Car("bmw", "11234"))
//        carsList.add(Car("toyota", "3456"))
//        carsList.add(Car("tico", "34512"))
//        carsList.add(Car("corolla", "24560"))
//        carsList.add(Car("kia",45623))

        myAdapter = CarAdapter(carsList, this)
        myResycler?.layoutManager = LinearLayoutManager(this)

        myResycler?.setHasFixedSize(true)
        myResycler?.adapter = myAdapter
        myAdapter?.notifyDataSetChanged()

        add?.setOnClickListener {
            addToList()
        }

    }

    override fun fonItemClick(position: Int) {
        changeCar(position)
    }

    private fun addToList() {

        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.add, null)
        val name: EditText = dialogLayout.findViewById(R.id.edit_new_model)
        val prise: EditText = dialogLayout.findViewById(R.id.edit_new_price)

        with(builder) {
            setTitle("Add to new car")
            setPositiveButton("Add") { dialog, which ->
                val title = name.text.toString()
                val price = prise.text.toString()
                val newCar = Car()
                newCar.model = title
                newCar.price = price
                carsList.add(newCar)
                myAdapter?.notifyDataSetChanged()

                database.getCarDao().addNewCar(newCar)
                Toast.makeText(this@MainActivity, "New car is added", Toast.LENGTH_SHORT).show()

            }
            setNegativeButton("Cancel"){ dialog, which ->
                dialog.dismiss()
            }
            setView(dialogLayout).show()
        }


    }

    private fun changeCar(position: Int) {


        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.okno, null)

        val name: EditText = dialogLayout.findViewById(R.id.edit_model)
        val prise: EditText = dialogLayout.findViewById(R.id.edit_price)

        with(builder) {
            setTitle("Update car")
            name.setText(  carsList[position].model)
            prise.setText(carsList[position].price)
            setPositiveButton("Update") { dialog, which ->
                val carsName = name.text.toString()
                val carsPrice = prise.text.toString()
//                carsList[position].model = carsName.toString()
//                carsList[position].price = carsPrice.toString()
                val cars = database.getCarDao().getCar(carsList.get(position).id)
                cars.model = carsName
                cars.price = carsPrice
                carsList.set(position,cars)
                database.getCarDao().updateCar(cars)

                myAdapter?.notifyDataSetChanged()
                Toast.makeText(this@MainActivity, " Car is Update", Toast.LENGTH_SHORT).show()

            }
            setNegativeButton("Delete"){ dialog, which ->
                carsList.removeAt(position)
                val cars = carsList.get(position)
                database.getCarDao().delete(cars)
                myAdapter?.notifyDataSetChanged()
                Toast.makeText(this@MainActivity, " Car is deleted", Toast.LENGTH_SHORT).show()
            }

            setNeutralButton("Cancel"){
                    dialog, which ->
                dialog.dismiss()
            }
            setView(dialogLayout).show()
        }


    }
}