package uz.pdp.todoapp.fragments

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.todoapp.R
import uz.pdp.todoapp.adapters.RecAdapterTask
import uz.pdp.todoapp.databinding.FragmentBottomSheetBinding
import uz.pdp.todoapp.room.database.DatabaseHelper
import uz.pdp.todoapp.room.entities.TodoClass
import kotlin.math.roundToInt

class BottomSheetFragment : SuperBottomSheetFragment() {
    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var recAdapterTask: RecAdapterTask
    private lateinit var list: List<TodoClass>
    private lateinit var databaseHelper: DatabaseHelper
    private var ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ID = it.getInt("id")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBottomSheetBinding.inflate(inflater, container,  false)
        databaseHelper = DatabaseHelper.getInstance(requireContext())
        list = ArrayList(databaseHelper.helper().getTypeList(ID))

        binding.apply {

            val type = databaseHelper.helper().getType(ID)
            recAdapterTask = RecAdapterTask(type.colorText, list, object : RecAdapterTask.OnMyClickListener{
                override fun onClick(id: Int, checked: Boolean) {
                    val task = databaseHelper.helper().getTask(id)
                    task.done = checked
                    databaseHelper.helper().editTask(task)
                }

            })
            root.setBackgroundColor(Color.parseColor(type.color))
            CATName.text = type.name
            CATName.setTextColor(Color.parseColor(type.colorText))

            countTask.setTextColor(Color.parseColor(type.coloSecondaryText))
            countTask.text = "${list.size} task"

            recycle.adapter = recAdapterTask

        }


        return binding.root
    }

    override fun animateStatusBar(): Boolean {
        return false
    }

    override fun getCornerRadius(): Float {
        return 100f
    }

    override fun getDim(): Float {
        return 0.5f
    }

    override fun animateCornerRadius(): Boolean {
        return false
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    @SuppressLint("Range")
    override fun getExpandedHeight(): Int {
        return 2100
    }


    companion object {

        @JvmStatic
        fun newInstance(id: Int) =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    this.putInt("id", id)
                }
            }
    }
}