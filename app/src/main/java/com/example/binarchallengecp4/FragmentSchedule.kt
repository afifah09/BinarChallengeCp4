package com.example.binarchallengecp4

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.binar.challenge4.data.Schedule
import com.binar.challenge4.database.MyDatabase
import com.example.binarchallengecp4.databinding.FragmentHomeBinding
import com.example.binarchallengecp4.databinding.FragmentRegisterBinding
import com.example.binarchallengecp4.databinding.FragmentScheduleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentSchedule(val schedule: Schedule?) : DialogFragment() {

    var saveClick:(Schedule)->Unit = {}
    var editClick:(Schedule)->Unit = {}

    private lateinit var binding: FragmentScheduleBinding
    private var myDatabase: MyDatabase? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScheduleBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDatabase = MyDatabase.getInstance(requireContext())
        binding.btnPilih.setOnClickListener {
            val calender = CalenderFragment(System.currentTimeMillis()) {
                binding.btnPilih.text = it
            }
            calender.show(parentFragmentManager,"Calender")
        }
        //untuk mengisi data spinner dengan data peserta
        val spinnerAdapter = ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lifecycleScope.launch(Dispatchers.IO) {
            val myDb = myDatabase?.scheduleDao()
            val listPeserta = myDb?.getAllPeserta()

            activity?.runOnUiThread {
                val spinnerlist = arrayListOf("Pilih Peserta")
                listPeserta?.let {
                    listPeserta.forEach{
                        spinnerlist.add(it.nama)
                    }
                }
                spinnerAdapter.addAll(spinnerlist)
                binding.spinnerPeserta.adapter = spinnerAdapter
            }
        }
        //untuk menampilkan peserta yang di klik dari spinner
        binding.spinnerPeserta.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var peserta = binding.colPeserta.text.toString()
                if (p2!=0){
                    peserta += if (binding.colPeserta.text.isEmpty()){
                        p0?.getItemAtPosition(p2).toString()
                    }else{
                        ", ${p0?.getItemAtPosition(p2).toString()}"
                    }
                    binding.colPeserta.setText(peserta)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }

// mengecek apakah ini insert atau update
        if (schedule==null){
            binding.saveAcara.setOnClickListener {
               val newschedule= Schedule(
                   null,
                   binding.colJudul.text.toString(),
                   binding.colPeserta.text.toString(),
                   binding.btnPilih.text.toString(),
                   binding.colDeskrip.text.toString()
               )
                saveClick(newschedule)
                dismiss()
            }
            //edit
        }else{
            binding.colJudul.setText(schedule.judul)
            binding.colPeserta.setText(schedule.peserta)
            binding.btnPilih.setText(schedule.tanggal)
            binding.colDeskrip.setText(schedule.deskripsi)
            binding.saveAcara.setOnClickListener{
                val newschedule= Schedule(
                    schedule.id,
                    binding.colJudul.text.toString(),
                    binding.colPeserta.text.toString(),
                    binding.btnPilih.text.toString(),
                    binding.colDeskrip.text.toString()
                )
                editClick(newschedule)
                dismiss()
            }
        }
    }
}