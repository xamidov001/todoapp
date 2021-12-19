package uz.pdp.todoapp.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.todoapp.R
import uz.pdp.todoapp.adapters.RecAdapterList
import uz.pdp.todoapp.adapters.RecAdapterTask
import uz.pdp.todoapp.databinding.FragmentMainBinding
import uz.pdp.todoapp.room.database.DatabaseHelper
import uz.pdp.todoapp.room.entities.TodoClass

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private lateinit var recAdapterTask: RecAdapterTask
    private lateinit var recAdapterList: RecAdapterList
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var list: List<TodoClass>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DatabaseHelper.getInstance(requireContext())
        list = ArrayList(databaseHelper.helper().getAllTask())

        recAdapterTask = RecAdapterTask("", list, object : RecAdapterTask.OnMyClickListener{
            override fun onClick(id: Int, checked: Boolean) {
                val task = databaseHelper.helper().getTask(id)
                task.done = checked
                databaseHelper.helper().editTask(task)
            }

        })
        recAdapterList = RecAdapterList(databaseHelper.helper().getAllType(), object : RecAdapterList.OnMyClickListenerList{
            override fun onclick(id: Int) {
                val sheet = BottomSheetFragment.newInstance(id)
                sheet.show(childFragmentManager, "DemoBottomSheetFragment")
            }

        })

        binding.apply {

            recycleTask.adapter = recAdapterTask
            recycleList.adapter = recAdapterList

            circleMenu.setMiniFabsColors(R.color.green_color, R.color.yellow_color)

            circleMenu.setMainFabOnClickListener {
                circleMenu.closeOptionsMenu()
            }

            //Set mini fabs clicklisteners.
            circleMenu.setMiniFabSelectedListener { fabItem ->
                when (fabItem?.itemId) {
                    R.id.fab_task -> {
                        findNavController().navigate(R.id.taskFragment)
                    }
                    R.id.fab_list -> {

                    }
                }
            };
        }
    }
}