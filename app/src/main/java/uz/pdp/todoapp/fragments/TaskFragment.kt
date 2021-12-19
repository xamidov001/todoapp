package uz.pdp.todoapp.fragments

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.todoapp.R
import uz.pdp.todoapp.adapters.RecAdapterList
import uz.pdp.todoapp.adapters.RecAdapterTaskAdd
import uz.pdp.todoapp.databinding.FragmentTaskBinding
import uz.pdp.todoapp.receiver.TimeReceiver
import uz.pdp.todoapp.room.database.DatabaseHelper
import uz.pdp.todoapp.room.entities.TodoClass
import uz.pdp.todoapp.room.entities.TypeClass
import java.text.SimpleDateFormat

class TaskFragment : Fragment(R.layout.fragment_task) {

    private val binding by viewBinding(FragmentTaskBinding::bind)
    private lateinit var recAdapterTaskAdd: RecAdapterTaskAdd
    private var lastImageView: ImageView? = null
    private var positionImg = 0
    private lateinit var databaseHelper: DatabaseHelper
    private var date: String = ""
    private var typeClass: TypeClass? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DatabaseHelper.getInstance(requireContext())

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        showKeyboard()
        binding.apply {

            recAdapterTaskAdd = RecAdapterTaskAdd(databaseHelper.helper().getAllType(), object : RecAdapterTaskAdd.OnMyClickListener{

                override fun onClick(position: Int, imageView: ImageView, typeClass: TypeClass) {
                    cardColor.setCardBackgroundColor(Color.parseColor(typeClass.color))
                    typeTxt.text = typeClass.name
                    typeTxt.setTextColor(Color.parseColor("#006CFF"))
                    this@TaskFragment.typeClass = typeClass
                    if (lastImageView != null) {
                        lastImageView?.visibility = View.GONE
                    }
                    lastImageView = imageView
                    positionImg = position
                    lastImageView?.visibility = View.VISIBLE
//                    recAdapterTaskAdd.notifyItemChanged(position)
//                    recAdapterTaskAdd.notifyItemChanged(positionImg)
                }

            })

            recycle.adapter = recAdapterTaskAdd

            hourPicker.minValue = 0
            minPicker.minValue = 0

            hourPicker.maxValue = 23
            minPicker.maxValue = 59

            calendarImg.setOnClickListener {
                hideKeyboard()
                hide()
                calendar.visibility = View.VISIBLE
            }

            calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
                var m = "${month+1}"
                var d = "$dayOfMonth"
                if (month+1<10 && dayOfMonth<10) {
                    m = "0${month+1}"
                    d = "0$dayOfMonth"
                }
                if (month+1<10) {
                    m = "0${month+1}"
                }
                if (dayOfMonth<10) {
                    d = "0$dayOfMonth"
                }
                date = "$year.${m}.$d"
                Toast.makeText(requireContext(), "$month\n$date", Toast.LENGTH_SHORT).show()
                calendarImg.setImageResource(R.drawable.ic_calendar_active)
            }

            alarmImg.setOnClickListener {
                hideKeyboard()
                hide()
                hourPicker.visibility = View.VISIBLE
                minPicker.visibility = View.VISIBLE
                alarmImg.setImageResource(R.drawable.ic_alarm_active)
            }

            typeTxt.setOnClickListener {
                hideKeyboard()
                hide()
                recycle.visibility = View.VISIBLE
            }

            taskEdt.setOnClickListener {
                hide()
                showKeyboard()
            }

            doneTxt.setOnClickListener {
                val edit = taskEdt.text.toString()
                if (typeClass != null && date.isNotEmpty() && edit.isNotEmpty()) {
                    val hour = hourPicker.value
                    val min = minPicker.value

                    databaseHelper.helper().addTask(TodoClass(task = edit, date = getMilli("$date/$hour:$min:00"), type = typeClass!!.id, color = typeClass!!.color))
                    val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val intent = Intent(requireContext(), TimeReceiver::class.java)
                    val pendingIntent = PendingIntent.getBroadcast(requireContext(), System.currentTimeMillis().toInt(), intent, 0)
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, getMilli("$date/$hour:$min:00"), pendingIntent)
                    findNavController().popBackStack()
                }
            }

            cancel.setOnClickListener {
                findNavController().popBackStack()
            }

        }
    }

    private fun showKeyboard() {
        binding.calendar.visibility = View.GONE
        val systemService = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        systemService.showSoftInput(binding.taskEdt.rootView, InputMethodManager.SHOW_IMPLICIT)
        binding.taskEdt.requestFocus()
    }

    private fun hideKeyboard() {
        val systemService = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        systemService.hideSoftInputFromWindow(binding.taskEdt.applicationWindowToken, 0)
    }

    private fun hide() {
        binding.apply {
            calendar.visibility = View.GONE
            hourPicker.visibility = View.GONE
            minPicker.visibility = View.GONE
            recycle.visibility = View.GONE

        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getMilli(date: String): Long {
        var milli: Long
        val str = SimpleDateFormat("yyyy.MM.dd/HH:mm:ss").parse(date)
        milli = str.time

        return milli
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TaskFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}