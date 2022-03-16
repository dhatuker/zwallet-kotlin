package com.dhatuker.zwallet.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.model.Contact
import com.dhatuker.zwallet.util.BASE_URL
import com.google.android.material.imageview.ShapeableImageView

class ContactAdapter(
    private var data: List<Contact>): RecyclerView.Adapter<ContactAdapter.ContactAdapterHolder>() {

    private lateinit var contextAdapter:Context

    class ContactAdapterHolder(view: View) : RecyclerView.ViewHolder(view){
        private val image: ShapeableImageView = view.findViewById(R.id.imageContact)
        private val name: TextView = view.findViewById(R.id.nameContact)
        private val phone: TextView = view.findViewById(R.id.numberContact)

        fun bindData(data: Contact, context: Context, position: Int){
            name.text = data.name
            phone.text = data.phone
            Glide.with(image)
                .load(BASE_URL + data.image)
                .apply(RequestOptions.circleCropTransform()
                    .placeholder(R.drawable.img))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        this.contextAdapter = parent.context
        val inflatedView : View = inflater.inflate(R.layout.item_contact_trasnfer, parent, false)
        return ContactAdapterHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ContactAdapterHolder, position: Int) {
        holder.bindData(this.data[position], contextAdapter, position)
    }

    override fun getItemCount(): Int {
        return this.data.size
    }

    fun addData(data: List<Contact>){
        this.data = data
    }

}