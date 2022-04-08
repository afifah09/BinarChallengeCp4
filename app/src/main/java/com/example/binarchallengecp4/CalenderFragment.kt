package com.example.binarchallengecp4

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class CalenderFragment( val date: Long, val onDateSet: (String)->Unit) : DialogFragment(), DatePickerDialog.OnDateSetListener {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        c.timeInMillis = date
        // Create a new instance of DatePickerDialog and return it
        val datePicker = DatePickerDialog(requireContext(), this, c.get(Calendar.YEAR), c.get(
            Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
//        datePicker.datePicker.minDate = System.currentTimeMillis()
        return datePicker
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
//        val calendar = Calendar.getInstance()
//        calendar.set(year,month,day)
//        val dateMillis = calendar.timeInMillis
        val date = "$day-$month-$year"
        onDateSet(date)
    }
}