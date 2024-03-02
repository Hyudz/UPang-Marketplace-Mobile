package com.example.phinma_upang_marketplace

import ProductsFetch
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CartAdapter(context: Context, resource: Int, items: List<ProductsFetch>) :
    ArrayAdapter<ProductsFetch>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_cart, parent, false)

        // Assuming you have a CartItem class with corresponding getters
        val currentItem = getItem(position)
        Log.d("CartAdapter", "Current Item: $currentItem")

        // Get references to the views in your cart_item.xml
        val itemNameTextView: TextView = itemView.findViewById(R.id.item_name)
        val quantityTextView: TextView = itemView.findViewById(R.id.item_quantity)
        val priceTextView: TextView = itemView.findViewById(R.id.item_price)

        // Set the data to the views
        itemNameTextView.text = currentItem?.name
        quantityTextView.text = currentItem?.quantity.toString()
        priceTextView.text = currentItem?.price.toString()

        return itemView
    }

    fun clearData() {
        super.clear()
    }

    fun addAll(items: List<ProductsFetch>) {
        super.addAll(items)
    }
}
