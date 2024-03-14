package com.example.phinma_upang_marketplace

import OrderResponse
import Product
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SellerProductsAdapter (context: Context, resource: Int, items: List<Product>, private val authToken: String, private val fname : String, private val lname : String) :
    ArrayAdapter<Product>(context, resource, items) {
    val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_seller_products, parent, false)

        Log.d("CartAdapter", "$fname $lname")
        val currentItem = getItem(position)
        //Log.d("CartAdapter", "Current Item: $currentItem")
        val authToken = this.authToken

        val itemNameTextView: TextView = itemView.findViewById(R.id.item_name)
        val quantityTextView: TextView = itemView.findViewById(R.id.item_quantity)
        val priceTextView: TextView = itemView.findViewById(R.id.item_price)
        val statusTextView: TextView = itemView.findViewById(R.id.productStatus)

        val quantity : String = "Quantity: " + currentItem?.quantity.toString()
        val price : String = "Price: " + currentItem?.price.toString() + " PHP"

        itemNameTextView.text = currentItem?.name
        quantityTextView.text = quantity
        priceTextView.text = price
        statusTextView.text = currentItem?.availability

        val editBtn : Button = itemView.findViewById(R.id.Edit)
        val deleteBtn : Button = itemView.findViewById(R.id.Delete)

        if (currentItem?.availability != "approved") {
            editBtn.visibility = View.GONE
            deleteBtn.visibility = View.GONE
        }

        editBtn.setOnClickListener{
            val intent = android.content.Intent(context, updateProduct::class.java)
            intent.putExtra("id", currentItem?.id.toString())
            intent.putExtra("authToken", authToken)
            Toast.makeText(context, "ID: ${currentItem?.id}", Toast.LENGTH_SHORT).show()
            context.startActivity(intent)
        }

        deleteBtn.setOnClickListener{
            deleteProduct(currentItem?.id!!, authToken)
        }


        return itemView
    }

    fun clearData() {
        super.clear()
    }

    fun addAll(items: List<Product>) {
        super.addAll(items)
    }

    fun deleteProduct(id: Int, authToken: String){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitData = retrofit.create(ItemsInterface::class.java)
        val service = retrofitData.deleteProduct(authToken, id)
        service.enqueue(object : retrofit2.Callback<OrderResponse> {
            override fun onResponse(call: retrofit2.Call<OrderResponse>, response: retrofit2.Response<OrderResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Product Deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error. Please check your internet connection", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<OrderResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to fetch Liked Items", Toast.LENGTH_SHORT).show()
            }
        })
    }
}