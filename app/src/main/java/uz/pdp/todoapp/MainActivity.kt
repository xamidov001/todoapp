package uz.pdp.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import uz.pdp.todoapp.databinding.ActivityMainBinding
import uz.pdp.todoapp.room.database.DatabaseHelper
import uz.pdp.todoapp.room.entities.TypeClass

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        databaseHelper = DatabaseHelper.getInstance(this)
//        databaseHelper.helper().addType(TypeClass(name = "Inbox", color = "#EBEFF5", colorText = "#252A31", coloSecondaryText = "#80252A30"))
//        databaseHelper.helper().addType(TypeClass(name = "Work", color = "#61DEA4", colorText = "#ffffff", coloSecondaryText = "#80FFFFFF"))
//        databaseHelper.helper().addType(TypeClass(name = "Shopping", color = "#F45E6D", colorText = "#ffffff", coloSecondaryText = "#80FFFFFF"))
//        databaseHelper.helper().addType(TypeClass(name = "Family", color = "#FFE761", colorText = "#252A31", coloSecondaryText = "#80252A30"))
//        databaseHelper.helper().addType(TypeClass(name = "Personal", color = "#B678FF", colorText = "#ffffff", coloSecondaryText = "#80FFFFFF"))


    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.my_support_nav).navigateUp()
    }
}