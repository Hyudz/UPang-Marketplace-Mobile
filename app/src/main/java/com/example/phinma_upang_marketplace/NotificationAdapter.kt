package com.example.phinma_upang_marketplace

import NotificationGet
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotificationAdapter(private val context: Context, private val notification: List<NotificationGet>, private val authToken: String) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notif_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notifications = notification[position]
        holder.textProductName.text = notifications.message
    }

    override fun getItemCount(): Int {
        return notification.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textProductName: TextView = itemView.findViewById(R.id.notif_content)
    }
}