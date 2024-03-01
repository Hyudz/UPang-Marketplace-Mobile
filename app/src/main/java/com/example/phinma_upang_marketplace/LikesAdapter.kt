package com.example.phinma_upang_marketplace

import ProductsFetch
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class LikesAdapter(private val context: Context, private val likes: List<ProductsFetch>, private val authToken: String) :
    RecyclerView.Adapter<LikesAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = likes[position]
        val imagename = product.image
        val imagepath = "https://upmarketplace-com.preview-domain.com/public/uploads/products/$imagename"
        //val placeholder = "data:image/png;base64,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
        Glide.with(this.context).load(imagepath).into(holder.itemProduct)
        holder.textProductName.text = product.name
        holder.textProductPrice.text = product.price
        holder.cardView.setOnClickListener{
            val sellerId = product.user_id
            val productId = product.id
            val intent = Intent(context, ViewProducts::class.java)
            intent.putExtra("authToken", authToken)
            intent.putExtra("product_name", product.name)
            intent.putExtra("product_price", product.price)
            intent.putExtra("product_description", product.description)
            intent.putExtra("productId", productId.toString())
            intent.putExtra("sellerId", sellerId.toString())
            intent.putExtra("image", imagepath)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return likes.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textProductName: TextView = itemView.findViewById(R.id.textProductName)
        val textProductPrice: TextView = itemView.findViewById(R.id.textProductPrice)
        val itemProduct: ImageView = itemView.findViewById(R.id.imageProduct)
        val cardView: View = itemView.findViewById(R.id.cardView)
    }
}