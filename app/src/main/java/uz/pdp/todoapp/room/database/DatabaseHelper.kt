package uz.pdp.todoapp.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.pdp.todoapp.room.dao.DaoHelper
import uz.pdp.todoapp.room.entities.TodoClass
import uz.pdp.todoapp.room.entities.TypeClass

@Database(entities = [TypeClass::class, TodoClass::class], version = 1)
abstract class DatabaseHelper : RoomDatabase() {

    abstract fun helper(): DaoHelper

    companion object {

        var databaseHelper: DatabaseHelper? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseHelper {
            if (databaseHelper == null) {
                databaseHelper = Room.databaseBuilder(context, DatabaseHelper::class.java, "my_db")
                    .allowMainThreadQueries()
                    .build()
            }

            return databaseHelper!!
        }

    }
}