package com.example.valortask.view.activity.users_module.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.valortask.databinding.ItemViewSavedCardListBinding
import com.example.valortask.view.activity.users_module.model.SavedCardModel

class SavedCardListAdapter(private inline val clickListener:
                                (model : SavedCardModel) -> Unit) : ListAdapter<SavedCardModel, SavedCardListAdapter.ViewHolder>(diffCallBack) {

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<SavedCardModel>() {
            override fun areItemsTheSame(
                oldItem: SavedCardModel,
                newItem: SavedCardModel
            ): Boolean {
                return isItemSame(oldItem, newItem)
            }

            override fun areContentsTheSame(
                oldItem: SavedCardModel,
                newItem: SavedCardModel
            ): Boolean {
                return oldItem == newItem
            }

        }

        fun isItemSame(oldItem: SavedCardModel, newItem: SavedCardModel): Boolean {

            return (oldItem.cardId == newItem.cardId)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemViewSavedCardListBinding.inflate(
            LayoutInflater.from(parent.context),parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {

            val tempModel = currentList[position]

            holder.bindView(tempModel)


        } catch (e: Exception) {

        }


    }


    inner class ViewHolder(val binding : ItemViewSavedCardListBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindView(model: SavedCardModel) {

            binding.model = model

            itemView.setOnClickListener {
                clickListener.invoke(model)
            }


        }


    }


}