package uz.pdp.todoapp.adapters

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuonbondol.textviewutil.strike
import uz.pdp.todoapp.databinding.ItemTaskBinding
import uz.pdp.todoapp.room.entities.TodoClass

class RecAdapterTask(var color: String, var list: List<TodoClass>, var listener: OnMyClickListener): RecyclerView.Adapter<RecAdapterTask.VH>() {

    interface OnMyClickListener {
        fun onClick(id: Int, checked: Boolean)
    }

    inner class VH(val binding: ItemTaskBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            val todoClass = list[position]

            root.setOnClickListener {  }

            cardColor.setCardBackgroundColor(Color.parseColor(todoClass.color))

            textTask.text = todoClass.task

            checkImg.setOnCheckedChangeListener { checkBox, isChecked ->
                listener.onClick(todoClass.id, isChecked)
                if (isChecked) {
                    textTask.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    textTask.paintFlags = Paint.ANTI_ALIAS_FLAG
                }
            }

            if (color.isNotEmpty()) {
                textTask.setTextColor(Color.parseColor(color))
            }

            if (todoClass.done) {
                checkImg.setChecked(true, false)
            } else {
                checkImg.setChecked(false, false)
            }

        }
    }

    override fun getItemCount(): Int = list.size
}