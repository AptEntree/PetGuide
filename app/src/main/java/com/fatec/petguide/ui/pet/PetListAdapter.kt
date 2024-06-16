package com.fatec.petguide.ui.pet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fatec.petguide.data.entity.PetEntity
import com.fatec.petguide.databinding.PetItemBinding

class PetListAdapter(
    private val onClickListener: OnClickListener,
    private val renderList: List<PetEntity?>
) : RecyclerView.Adapter<PetListAdapter.PetListViewHolder>() {

    inner class PetListViewHolder(private val binding: PetItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(petEntity: PetEntity?) {
            with(binding) {
                petName.text = petEntity?.name
                petAge.text = "Idade: ${petEntity?.age}"
                petCoat.text = "Pelagem: ${petEntity?.coat}"
                petRace.text = "Raça: ${petEntity?.race}"
                historicButton.setOnClickListener {
                    onClickListener.onClick(petEntity?.petId?: "")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetListViewHolder =
        PetListViewHolder(
            PetItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = renderList.size

    override fun onBindViewHolder(holder: PetListViewHolder, position: Int) = holder.bind(renderList[position])

    interface OnClickListener {
        fun onClick(id: String)
    }
}