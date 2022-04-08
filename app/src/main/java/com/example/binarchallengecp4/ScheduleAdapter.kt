package com.example.binarchallengecp4

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.challenge4.data.Schedule
import com.example.binarchallengecp4.databinding.LayoutAgendaBinding
import java.util.*

class ScheduleAdapter(private val delClick:(Schedule)->Unit,
                      private val editClick:(Schedule)-> Unit)
    : ListAdapter<Schedule, ScheduleAdapter.ViewHolder>(ScheduleComparator()) {


    class ViewHolder(private val binding: LayoutAgendaBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(currentSchedule: Schedule,
                 delClick: (Schedule) -> Unit,
                 editClick: (Schedule) -> Unit){
            binding.judul.text = currentSchedule.judul
            binding.tanggal.text = currentSchedule.tanggal
            binding.edit.setOnClickListener {
                editClick(currentSchedule)
            }
            binding.delet.setOnClickListener {
                delClick(currentSchedule)
            }
        }

    }

    class ScheduleComparator : DiffUtil.ItemCallback<Schedule>() {
        override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutAgendaBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), delClick, editClick)
    }

}