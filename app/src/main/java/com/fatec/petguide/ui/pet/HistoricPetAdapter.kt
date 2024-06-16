package com.fatec.petguide.ui.pet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fatec.petguide.data.entity.HistoricEntity
import com.fatec.petguide.databinding.HistoricItemBinding

class HistoricPetAdapter(
    private val onClickListener: OnClickListener,
    private val renderList: List<HistoricEntity?>
) : RecyclerView.Adapter<HistoricPetAdapter.HistoricLisViewHolder>() {

    inner class HistoricLisViewHolder(private val binding: HistoricItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(historicEntity: HistoricEntity?) {
            with(binding) {
                title.text = historicEntity?.title
                date.text = historicEntity?.date
                file.text = historicEntity?.file
                root.setOnClickListener {
                    onClickListener.onClick(file = historicEntity?.file)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricLisViewHolder =
        HistoricLisViewHolder(
            HistoricItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = renderList.size

    override fun onBindViewHolder(holder: HistoricLisViewHolder, position: Int) =
        holder.bind(renderList[position])

    interface OnClickListener {
        fun onClick(file: String?)
    }
}