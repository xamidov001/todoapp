package uz.pdp.todoapp.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.todoapp.R
import uz.pdp.todoapp.databinding.ItemListBinding
import uz.pdp.todoapp.room.entities.TypeClass

class RecAdapterList(var list: List<TypeClass>, var listener: OnMyClickListenerList): RecyclerView.Adapter<RecAdapterList.VH>() {

    interface OnMyClickListenerList {
        fun onclick(id: Int)
    }

    inner class VH(val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            val typeClass = list[position]

            bigCard.setOnClickListener {
                listener.onclick(typeClass.id)
            }

            bigCard.setCardBackgroundColor(Color.parseColor(typeClass.color))
            typeName.text = typeClass.name
            typeName.setTextColor(Color.parseColor(typeClass.colorText))
            countTaskTxt.setTextColor(Color.parseColor(typeClass.coloSecondaryText))

        }
    }

    override fun getItemCount(): Int = list.size
}