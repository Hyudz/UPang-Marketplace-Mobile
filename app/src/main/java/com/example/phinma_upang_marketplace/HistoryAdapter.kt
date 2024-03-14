package com.example.phinma_upang_marketplace

import GetData
import HistoryResponse
import OrderHistory
import OrderResponse
import ProductDetail
import ProductHistory
import ProductsFetch
import ProductsRequest
import RemoveRequest
import SellerResponse
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
                cancelOrder(currentItem?.order_id!!)
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

class HistoryAdapter2 (context: Context, resource: Int, items: List<SellerResponse>, val authToken : String, val usertype : String) :
    ArrayAdapter<SellerResponse>(context, resource, items) {
    val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_history, parent, false)
        val currentItem = getItem(position)
        val itemNameTextView: TextView = itemView.findViewById(R.id.item_name)
        val priceTextView: TextView = itemView.findViewById(R.id.item_price)
        val statusTextView: TextView = itemView.findViewById(R.id.productStatus)
        val controllerBtn : Button = itemView.findViewById(R.id.controll)
        val editBtn : Button = itemView.findViewById(R.id.Edit)
        val deleteBtn : Button = itemView.findViewById(R.id.Delete)

        Log.d("HistoryAdapter2", "Current Item: $currentItem")

<<<<<<< HEAD
//        if (currentItem?.availability == "to ship") {
//                controllerBtn.text = "Settle"
//                editBtn.visibility = View.GONE
//                deleteBtn.visibility = View.GONE
//        } else if (currentItem?.availability == "approved") {
//            controllerBtn.visibility = View.GONE
//        } else {
//            controllerBtn.visibility = View.GONE
//            editBtn.visibility = View.GONE
//            deleteBtn.visibility = View.GONE
//        }
//
//        controllerBtn.setOnClickListener{
//            if (usertype == "seller"){
//                setlleOrder(currentItem?.id!!)
//                Log.d("HistoryAdapter2", "${currentItem?.id}")
//            }
//        }
//
//        editBtn.setOnClickListener{
//            val intent = android.content.Intent(context, updateProduct::class.java)
//            intent.putExtra("id", currentItem?.id.toString())
//            intent.putExtra("authToken", authToken)
//            context.startActivity(intent)
//        }
//
//        deleteBtn.setOnClickListener{
//            deleteProduct(currentItem?.id!!, authToken)
//        }
//
//        itemNameTextView.text = currentItem?.name
//        priceTextView.text = currentItem?.price.toString()
//        statusTextView.text = currentItem?.availability
=======
        controllerBtn.setOnClickListener{
            if (usertype == "seller"){
                setlleOrder(currentItem?.id!!)
                Log.d("HistoryAdapter2", "${currentItem?.id}")
            }
        }

        editBtn.setOnClickListener{
<<<<<<< Updated upstream
            val intent = android.content.Intent(context, updateProduct::class.java)
            intent.putExtra("id", currentItem?.id.toString())
            intent.putExtra("authToken", authToken)
            context.startActivity(intent)
=======
            Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show()
>>>>>>> Stashed changes
        }

        deleteBtn.setOnClickListener{
            deleteProduct(currentItem?.id!!, authToken)
        }

        itemNameTextView.text = currentItem?.name
        priceTextView.text = currentItem?.price.toString()
        statusTextView.text = currentItem?.availability
>>>>>>> dbb5c4e99703b83ad05a13923564aed1e12901b1

        return itemView
    }

    fun clearData() {
        super.clear()
    }

    fun addAll(items: List<SellerResponse>) {
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
                    Toast.makeText(context, "Error. Please check your internet connection", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<OrderResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to fetch Liked Items", Toast.LENGTH_SHORT).show()
            }
        })
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
