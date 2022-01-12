package com.example.savingdatasqllite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etLocation: EditText
    private lateinit var btnSave: Button
    private lateinit var btnRead: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var rvMain: RecyclerView
    private lateinit var rvAdapter: RVAdapter
    private lateinit var people: ArrayList<Person>

    // used for update and delete items
    var selectedPerson: Person? = null

    //allows create one instance of it and its only create ones we uses , DatabaseHelper need context so we pass it applicationContext
    private val databaseHelper by lazy {
        DatabaseHelper(applicationContext)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        people = arrayListOf()

        etName = findViewById(R.id.etName)
        etLocation = findViewById(R.id.etLocation)
        btnSave = findViewById(R.id.btnSubmit)
        btnRead = findViewById(R.id.btnGet)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val location = etLocation.text.toString()

            databaseHelper.saveData(name, location)

            Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show()
            etName.text.clear()
            etLocation.text.clear()

        }

        btnRead.setOnClickListener {
            people = databaseHelper.readData()
            rvAdapter.update(people)

            //Clear two files of editText
            etName.text.clear()
            etLocation.text.clear()

        }

        btnUpdate.setOnClickListener {
            if (selectedPerson != null) {
                val name = etName.text.toString()
                val location = etLocation.text.toString()

                databaseHelper.updateData(Person(selectedPerson!!.pk, name, location))
                Toast.makeText(this, "Update successfully", Toast.LENGTH_SHORT).show()

                selectedPerson = null

                etName.text.clear()
                etLocation.text.clear()

            }
        }
        btnDelete.setOnClickListener {
            if (selectedPerson != null) {
                databaseHelper.deleteData(selectedPerson!!)
                
                Toast.makeText(this, "Delete successfully", Toast.LENGTH_SHORT).show()

                selectedPerson = null
                etName.text.clear()
                etLocation.text.clear()
            }
        }



        rvMain = findViewById(R.id.rvMain)
        rvAdapter = RVAdapter(this)
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(this)


    }

    // This when we click on the item
    fun updateFields() {
        etName.setText(selectedPerson!!.name)
        etLocation.setText(selectedPerson!!.location)
    }
}