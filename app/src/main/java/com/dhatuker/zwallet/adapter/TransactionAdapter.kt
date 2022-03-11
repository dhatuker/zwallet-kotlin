package com.dhatuker.zwallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.data.Transaction
import com.dhatuker.zwallet.model.Invoice
import com.dhatuker.zwallet.util.BASE_URL
import com.google.android.material.imageview.ShapeableImageView

class TransactionAdapter(private var data: List<Invoice>): RecyclerView.Adapter<TransactionAdapter.TransactionAdapterHolder>() {

    lateinit var  contextAdapter:Context

    class TransactionAdapterHolder(view: View) : RecyclerView.ViewHolder(view){
        private val image: ShapeableImageView = view.findViewById(R.id.imageListTransaction)
        private val name: TextView = view.findViewById(R.id.nameListTransaction)
        private val type: TextView = view.findViewById(R.id.typeListTransaction)
        private val amount: TextView = view.findViewById(R.id.amountListTransaction)

        fun bindData(data: Invoice, context: Context, position: Int){
            name.text = data.name
            type.text = data.type
            amount.text = data.amount.toString()
            Glide.with(image)
                .load(BASE_URL + data.image)
                .apply(RequestOptions.circleCropTransform()
                    .placeholder(R.drawable.img))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        this.contextAdapter = parent.context
        val inflatedView : View = inflater.inflate(R.layout.item_transaction_home, parent, false)
        return TransactionAdapterHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TransactionAdapterHolder, position: Int) {
        holder.bindData(this.data[position], contextAdapter, position)
    }

    override fun getItemCount(): Int {
        return this.data.size
    }

    fun addData(data: List<Invoice>){
        this.data = data
    }

}