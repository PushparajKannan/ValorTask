package com.example.valortask.view.activity.users_module.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.valortask.databinding.ItemViewPaymentProductListBinding
import com.example.valortask.view.activity.users_module.model.ProductList
import java.text.DecimalFormat

class PaymentSummaryAdapter() : ListAdapter<ProductList, PaymentSummaryAdapter.ViewHolder>(diffCallBack) {

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<ProductList>() {
            override fun areItemsTheSame(
                oldItem: ProductList,
                newItem: ProductList
            ): Boolean {
                return isItemSame(oldItem, newItem)
            }

            override fun areContentsTheSame(
                oldItem: ProductList,
                newItem: ProductList
            ): Boolean {
                return oldItem == newItem
            }

        }

        fun isItemSame(oldItem: ProductList, newItem: ProductList): Boolean {

            return (oldItem.productId == newItem.productId)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemViewPaymentProductListBinding.inflate(
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


    inner class ViewHolder(val binding : ItemViewPaymentProductListBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindView(model: ProductList) {

            binding.model = model


            val actualPrice = (model.productPrice ?: 0.0) * (model.productQuantity ?: 1)

            binding.actualPrice = DecimalFormat("#.##").format(actualPrice)


        }


    }

    /* override fun submitList(list: List<RecyclerViewItems>?) {
         super.submitList(list?.let { ArrayList(it) })
     }*/
}