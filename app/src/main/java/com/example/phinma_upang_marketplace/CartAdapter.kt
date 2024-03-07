package com.example.phinma_upang_marketplace

import OrderResponse
import ProductsFetch
import RemoveRequest
import android.content.Context
import android.content.Intent
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

class CartAdapter(context: Context, resource: Int, items: List<ProductsFetch>, private val authToken: String, private val fname : String, private val lname : String) :
    ArrayAdapter<ProductsFetch>(context, resource, items) {
    val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_cart, parent, false)

        Log.d("CartAdapter", "$fname $lname")
        val currentItem = getItem(position)
        //Log.d("CartAdapter", "Current Item: $currentItem")
        val authToken = this.authToken

        val itemNameTextView: TextView = itemView.findViewById(R.id.item_name)
        val quantityTextView: TextView = itemView.findViewById(R.id.item_quantity)
        val priceTextView: TextView = itemView.findViewById(R.id.item_price)

        itemNameTextView.text = currentItem?.name
        quantityTextView.text = currentItem?.quantity.toString()
        priceTextView.text = currentItem?.price.toString()

        val removeItem : Button = itemView.findViewById(R.id.remove_item)
        removeItem.setOnClickListener{
            removeItem(position, authToken)
        }

        val buyBtn : Button = itemView.findViewById(R.id.purchase_item)
        buyBtn.setOnClickListener{
            checkOutItem(currentItem!!, authToken, fname, lname)
        }

        return itemView
    }

    fun clearData() {
        super.clear()
    }

    fun addAll(items: List<ProductsFetch>) {
        super.addAll(items)
    }

    fun checkOutItem(currentItem: ProductsFetch, authToken: String, fname: String, lname: String){
        val intent = Intent(context, ConfirmOrder::class.java)
        val buyerName = "$fname $lname"
        intent.putExtra("authToken", authToken)
        intent.putExtra("product_id", currentItem.id.toString())
        intent.putExtra("product_name", currentItem.name)
        intent.putExtra("product_price", currentItem.price)
        intent.putExtra("seller_id", currentItem.user_id.toString())
        intent.putExtra("buyerName", buyerName)
        context.startActivity(intent)
        Log.d("CartAdapter", "Item to checkout: $currentItem")
        Log.d("CartAdapter", "Name" + buyerName)
    }

    fun removeItem(position: Int, authToken: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val getItem = getItem(position)!!.id
        getItem.toString()
        Log.d("CartAdapter", "Item to remove: $getItem")

        val retrofitData = retrofit.create(ItemsInterface::class.java)
        val service = retrofitData.removeFromCart(RemoveRequest(getItem), authToken)
        service.enqueue(object : retrofit2.Callback<OrderResponse> {
            override fun onResponse(call: retrofit2.Call<OrderResponse>, response: retrofit2.Response<OrderResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    Log.d("CartAdapter", "Response: $responseBody")
                    remove(getItem(position))
                    notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "Error. Please check your internet connection", Toast.LENGTH_SHORT).show()
                    Log.d("CartAdapter", "Unsuccessful response: ${response.code()}")
                    Log.d("CartAdapter", "Unsuccessful response: ${response.errorBody()}")
                    Log.d("CartAdapter", "Unsuccessful response: ${response.message()}")
                    Log.d("CartAdapter", "Unsuccessful response: ${response.headers()}")
                    Log.d("CartAdapter", "Unsuccessful response: ${response.raw()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<OrderResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to remove item from cart", Toast.LENGTH_SHORT).show()
                Log.d("CartAdapter", "onFailure" + t.message)
                Log.d("CartAdapter", "onFailure" + call.request().url())
                Log.d("CartAdapter", "onFailure" + call.request().method())
                Log.d("CartAdapter", "onFailure" + call.request().headers())
                Log.d("CartAdapter", "onFailure" + call.request().body())

            }
        })
    }
}
