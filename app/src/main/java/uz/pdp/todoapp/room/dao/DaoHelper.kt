package uz.pdp.todoapp.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import uz.pdp.todoapp.room.entities.TodoClass
import uz.pdp.todoapp.room.entities.TypeClass

@Dao
interface DaoHelper {

    @Insert()
    fun addTask(todoClass: TodoClass)

    @Update
    fun editTask(task: TodoClass)

    @Insert
    fun addType(typeClass: TypeClass)

    @Query("select * from TypeClass")
    fun getAllType(): List<TypeClass>

    @Query("select * from TodoClass")
    fun getAllTask(): List<TodoClass>

    @Query("select * from TypeClass where id=:n")
    fun getType(n: Int): TypeClass

    @Query("select * from TodoClass where id=:n")
    fun getTask(n: Int): TodoClass

    @Query("select * from TodoClass where type=:n")
    fun getTypeList(n: Int): List<TodoClass>
}