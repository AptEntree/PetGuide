package com.fatec.petguide.ui.reminder

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fatec.petguide.data.entity.ReminderEntity
import com.fatec.petguide.databinding.ReminderItemBinding

class ReminderAdapter(
    private val onClickListener: OnClickListener,
    private val renderList: List<ReminderEntity?>
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    inner class ReminderViewHolder(private val binding: ReminderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reminderEntity: ReminderEntity?) {
            reminderEntity?.let {
                with(binding) {
                    reminderTitle.text = "${reminderEntity.petId}: ${reminderEntity.title}"
                    reminderClinic.text = reminderEntity.location
                    dayItem.dayName.text = reminderEntity.date
                    root.setOnClickListener {
                        onClickListener.onClick(reminderEntity.reminderId)
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder =
        ReminderViewHolder(
            ReminderItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = renderList.size

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) =
        holder.bind(renderList[position])

    interface OnClickListener {
        fun onClick(reminderId: String?)
    }
}