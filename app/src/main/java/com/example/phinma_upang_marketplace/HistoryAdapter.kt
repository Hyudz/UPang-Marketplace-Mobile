package com.example.phinma_upang_marketplace

import HistoryResponse
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class HistoryAdapter (context: Context, resource: Int, items: List<HistoryResponse>) :
ArrayAdapter<HistoryResponse>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_history, parent, false)

        val currentItem = getItem(position)

        val itemNameTextView: TextView = itemView.findViewById(R.id.item_name)
        val quantityTextView: TextView = itemView.findViewById(R.id.item_quantity)
        val priceTextView: TextView = itemView.findViewById(R.id.item_price)
        val product_status: TextView = itemView.findViewById(R.id.productStatus)

        val description = currentItem?.descriptions
        val order = currentItem?.order

        Log.d("HistoryAdapter", "Description: $description")
        Log.d("HistoryAdapter", "Order: $order")

        return itemView
    }

    fun clearData() {
        super.clear()
    }

    fun addAll(items: List<HistoryResponse>) {
        super.addAll(items)
    }

}