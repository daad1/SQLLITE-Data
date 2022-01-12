package com.example.savingdatasqllite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
/*  we need to import three things   Constructor ,onCreate,onUpgrade                                                                DataBase Name*/
class DatabaseHelper(context: Context):SQLiteOpenHelper(context,"details.db",null,2){
                                //Allow as access database in all of functions
    private val sqliteDatabase: SQLiteDatabase = writableDatabase

    //query for creating table
    override fun onCreate(db: SQLiteDatabase?) {

            //SQLite commands / create table called students with name and location, cuz version 2 we add pk means primary key type integer ,also key autoincrement allows sqlite to automatic increased the number each time when we add in database
            db?.execSQL("create table Students(pk INTEGER PRIMARY KEY AUTOINCREMENT,Name text , Location text )")

    }
//when the version number is increased this will executed (Alter commands)
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        //This removes the table if a new version is detected , means table version 1 will be deleted
        db!!.execSQL(" DROP TABLE IF EXISTS Students")
    onCreate(db)
    }


    fun saveData(name: String, location: String){
        val contentValues = ContentValues()

            // Make sure is match what in database (execSQL)
        contentValues.put("Name",name)
        contentValues.put("Location",location)
        //what exactly is writableDatabase                     this allows us take the database came into the function save data grouped and then pass it in the database
        sqliteDatabase.insert("Students",null,contentValues)
    }

    //
    fun readData(): ArrayList<Person>{
        val people = arrayListOf<Person>()

        //Read all data using cursor
        val cursor: Cursor = sqliteDatabase.rawQuery("SELECT * FROM Students",null)

        if (cursor.count < 1){ // Handle empty table
            println("Not Data Found !")

        }
        else{
            while (cursor.moveToNext()){// Iterate through table and populate people ArrayList
                val pk = cursor.getInt(0) // The integer value refers to the column
                val  name = cursor.getString(1)
                val  location = cursor.getString(2)
                people.add(Person(pk,name, location))
            }

        }
        return people
    }

    fun updateData(person: Person){
        val contentValues = ContentValues()
        contentValues.put("Name",person.name)
        contentValues.put("Location",person.location)
        sqliteDatabase.update("Students",contentValues,"pk = ${person.pk}",null)

    }

    fun deleteData(person: Person){
        sqliteDatabase.delete("Students","pk = ${person.pk}",null)
    }



}