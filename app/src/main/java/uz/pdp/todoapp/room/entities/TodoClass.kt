package uz.pdp.todoapp.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoClass(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var task: String,
    var date: Long,
    var type: Int,
    var color: String,
    var done: Boolean = false
)