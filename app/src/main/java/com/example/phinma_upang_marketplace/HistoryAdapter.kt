package com.example.phinma_upang_marketplace

import GetData
import HistoryResponse
import OrderHistory
import OrderResponse
import ProductHistory
import ProductsFetch
import ProductsRequest
import RemoveRequest
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

class HistoryAdapter (context: Context, resource: Int, items: List<HistoryResponse>, val authToken : String, val usertype : String) :
    ArrayAdapter<HistoryResponse>(context, resource, items) {
    val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_history, parent, false)
        val currentItem = getItem(position)
        val itemNameTextView: TextView = itemView.findViewById(R.id.item_name)
        val priceTextView: TextView = itemView.findViewById(R.id.item_price)
        val statusTextView: TextView = itemView.findViewById(R.id.productStatus)
        val controllerBtn : Button = itemView.findViewById(R.id.controll)

        if (currentItem?.status == "to ship") {
           controllerBtn.text = "Cancel"
        } else {
            controllerBtn.visibility = View.GONE
        }

        controllerBtn.setOnClickListener{
            if (usertype == "buyer"){
                cancelOrder(currentItem?.id!!)
            }
        }

        itemNameTextView.text = currentItem?.name
        priceTextView.text = currentItem?.price.toString()
        statusTextView.text = currentItem?.status

        return itemView
    }

    fun clearData() {
        super.clear()
    }

    fun addAll(items: List<HistoryResponse>) {
        super.addAll(items)
    }

    fun cancelOrder(id:Int){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitData = retrofit.create(ItemsInterface::class.java)
        val request = OrderHistory(id)
        val service = retrofitData.cancelOrder(request, authToken)
        service.enqueue(object : retrofit2.Callback<OrderResponse> {
            override fun onResponse(call: retrofit2.Call<OrderResponse>, response: retrofit2.Response<OrderResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Order Cancelled", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("PurchaseHistory", "Response Body: ${response.body()}")
                    Log.d("PurchaseHistory", "Response Code: ${response.code()}")
                    Log.d("PurchaseHistory", "Response Message: ${response.message()}")
                    Log.d("PurchaseHistory", "Response Error Body: ${response.errorBody()}")
                    Log.d("PurchaseHistory", "Response Raw: ${response.raw()}")
                    Toast.makeText(context, "Error. Please check your internet connection", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<OrderResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to fetch Liked Items", Toast.LENGTH_SHORT).show()
            }
        })
    }

}

class HistoryAdapter2 (context: Context, resource: Int, items: List<ProductsFetch>, val authToken : String, val usertype : String) :
    ArrayAdapter<ProductsFetch>(context, resource, items) {
    val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_history, parent, false)
        val currentItem = getItem(position)
        val itemNameTextView: TextView = itemView.findViewById(R.id.item_name)
        val priceTextView: TextView = itemView.findViewById(R.id.item_price)
        val statusTextView: TextView = itemView.findViewById(R.id.productStatus)
        val controllerBtn : Button = itemView.findViewById(R.id.controll)

        if (currentItem?.availability == "to ship") {
                controllerBtn.text = "Settle"
        } else {
            controllerBtn.visibility = View.GONE
        }

        controllerBtn.setOnClickListener{
            if (usertype == "seller"){
                setlleOrder(currentItem?.id!!)
                Log.d("HistoryAdapter2", "${currentItem?.id}")
            }
        }

        itemNameTextView.text = currentItem?.name
        priceTextView.text = currentItem?.price.toString()
        statusTextView.text = currentItem?.availability

        return itemView
    }

    fun clearData() {
        super.clear()
    }

    fun addAll(items: List<ProductsFetch>) {
        super.addAll(items)
    }

    fun setlleOrder(id: Int){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitData = retrofit.create(ItemsInterface::class.java)
        val request = ProductHistory(id)
        val service = retrofitData.settleOrder(request, authToken)
        service.enqueue(object : retrofit2.Callback<OrderResponse> {
            override fun onResponse(call: retrofit2.Call<OrderResponse>, response: retrofit2.Response<OrderResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Order Settled", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("PurchaseHistory", "Response Body: ${response.body()}")
                    Log.d("PurchaseHistory", "Response Code: ${response.code()}")
                    Log.d("PurchaseHistory", "Response Message: ${response.message()}")
                    Log.d("PurchaseHistory", "Response Error Body: ${response.errorBody()}")
                    Log.d("PurchaseHistory", "Response Raw: ${response.raw()}")
                    Log.d("HistoryAdapter2", "Response Header: ${response.headers()}")

                    Toast.makeText(context, "Error. Please check your internet connection", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<OrderResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to fetch Liked Items", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
