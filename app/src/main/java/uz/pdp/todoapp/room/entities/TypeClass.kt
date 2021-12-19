package uz.pdp.todoapp.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TypeClass(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var color: String,
    var colorText: String,
    var coloSecondaryText: String,
    var check: Boolean = false

)