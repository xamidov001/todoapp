package uz.pdp.todoapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.todoapp.databinding.ItemListTaskBinding
import uz.pdp.todoapp.room.entities.TypeClass

class RecAdapterTaskAdd(var list: List<TypeClass>, var listener: OnMyClickListener): RecyclerView.Adapter<RecAdapterTaskAdd.VH>() {

    interface OnMyClickListener {
        fun onClick(position: Int, imageView: ImageView, typeClass: TypeClass)
    }

    inner class VH(val binding: ItemListTaskBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemListTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            val typeClass = list[position]

            bigCard.setOnClickListener {
               listener.onClick(position, imageCheck, typeClass)
            }

            bigCard.setCardBackgroundColor(Color.parseColor(typeClass.color))
            typeName.text = typeClass.name
            typeName.setTextColor(Color.parseColor(typeClass.colorText))
            countTaskTxt.setTextColor(Color.parseColor(typeClass.coloSecondaryText))

        }
    }

    override fun getItemCount(): Int = list.size
}