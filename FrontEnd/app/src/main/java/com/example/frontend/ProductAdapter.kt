package com.example.frontend

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class ProductAdapter(private val context: Context) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    var datas = mutableListOf<ProductData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas!![position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtName: TextView = itemView.findViewById(R.id.rv_name)
        private val txtInform: TextView = itemView.findViewById(R.id.rv_imform)
        private val imgProduct: ImageView = itemView.findViewById(R.id.rv_photo)
        private val txtPrice: TextView = itemView.findViewById(R.id.rv_price)

        fun bind(item: ProductData) {
            txtName.text = item.name
            txtInform.text = item.inform
            txtPrice.text = DecimalFormat("###,###").format(item.price).toString() + "원"

            val imgSource: Int = context.resources.getIdentifier(item.img,"drawable", context.packageName)
            Glide.with(itemView).load(imgSource).into(imgProduct)

            itemView.setOnClickListener {
                Intent(context, DetailActivity::class.java).apply {
                    putExtra("data", item)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }
        }
    }
}