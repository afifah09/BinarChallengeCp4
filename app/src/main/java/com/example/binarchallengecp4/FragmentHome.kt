package com.example.binarchallengecp4

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.binar.challenge4.data.Schedule
import com.binar.challenge4.database.MyDatabase
import com.example.binarchallengecp4.databinding.FragmentHomeBinding
import com.example.binarchallengecp4.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var myDatabase: MyDatabase? = null
    private lateinit var adapter:ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }
    private fun fetchData(){
        lifecycleScope.launch(Dispatchers.IO) {
            val myDb = myDatabase?.scheduleDao()
            val listschedule = myDb?.getAllSchedule()

            activity?.runOnUiThread {
                listschedule?.let {
                    adapter.submitList(it)
                }
            }
        }
    }
    fun deleteData(schedule:Schedule){
        lifecycleScope.launch(Dispatchers.IO){
            myDatabase?.scheduleDao()?.deleteSchedule(schedule)

        }
    }
    fun editData(schedule: Schedule) {
        lifecycleScope.launch(Dispatchers.IO) {
            myDatabase?.scheduleDao()?.updateSchedule(schedule)

        }
    }
    fun adddata (schedule: Schedule){
        lifecycleScope.launch(Dispatchers.IO) {
            myDatabase?.scheduleDao()?.insertSchedule(schedule)

        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDatabase = MyDatabase.getInstance(requireContext())
        binding.buat.setOnClickListener {
            val scheduleDialog = FragmentSchedule(null)
            scheduleDialog.saveClick={
                adddata(it)
                val snackbar = Snackbar.make(binding.root,"Berhasil Di Tambah!", Snackbar.LENGTH_LONG)
                snackbar.setAction("OK") {
                    snackbar.dismiss()
                }.show()
                fetchData()
            }
            scheduleDialog.show(parentFragmentManager,"Add")
        }
        adapter = ScheduleAdapter({
            val confirmDialog = AlertDialog.Builder(requireContext())
            confirmDialog.setTitle("Konfirmasi Hapus")
            confirmDialog.setMessage("Apakah anda yakin inigin menghapus schedule ini?")
            confirmDialog.setPositiveButton("OK",
                DialogInterface.OnClickListener{ dialogInterface, i ->
                deleteData(it)
                val snackbar = Snackbar.make(binding.root,"Berhasil Delete!", Snackbar.LENGTH_LONG)
                snackbar.setAction("OK") {
                    snackbar.dismiss()
                }.show()
                    fetchData()
            })
            confirmDialog.setNegativeButton("Cancel",
                DialogInterface.OnClickListener{ dialogInterface, i ->
                dialogInterface.dismiss()
            })
            confirmDialog.create().show()


        },{
            val scheduleDialog = FragmentSchedule(it)
            scheduleDialog.show(parentFragmentManager,"Edit")
            scheduleDialog.editClick = {
                editData(it)
                val snackbar = Snackbar.make(binding.root,"Berhasil Edit!", Snackbar.LENGTH_LONG)
                snackbar.setAction("OK") {
                    snackbar.dismiss()
                }.show()
                fetchData()
            }
        })
        binding.reycycleview.adapter = adapter
        fetchData()
        binding.tambah.setOnClickListener {
            PesertaFragment().show(parentFragmentManager,"Peserta")
        }
        val sharedPreference = requireContext().getSharedPreferences("SHARED_FILE", Context.MODE_PRIVATE)
        binding.back.setOnClickListener{
            val editor = sharedPreference.edit()
            editor.clear()
            editor.apply()
            it.findNavController().navigate(R.id.action_fragmentHome_to_fragmentLogin)
        }
    }
}