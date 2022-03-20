package com.dhatuker.zwallet.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.databinding.ItemContactTrasnferBinding
import com.dhatuker.zwallet.model.Contact
import com.dhatuker.zwallet.util.BASE_URL

class ContactAdapter(
    private var data: List<Contact>,
    private var clickListener: (Contact, View) -> Unit,
): RecyclerView.Adapter<ContactAdapter.ContactAdapterHolder>() {


    private lateinit var contextAdapter:Context

    class ContactAdapterHolder(private val binding : ItemContactTrasnferBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindData(data: Contact, onClick : (Contact, View) -> Unit){
            binding.nameContact.text = data.name
            binding.numberContact.text = data.phone
            Glide.with(binding.imageContact)
                .load(BASE_URL + data.image)
                .apply(
                    RequestOptions.circleCropTransform()
                        .placeholder(R.drawable.ic_baseline_person_24)
                )
                .into(binding.imageContact)

            binding.root.setOnClickListener { onClick(data, binding.root)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        this.contextAdapter = parent.context
        val binding  = ItemContactTrasnferBinding.inflate(inflater, parent, false)
        return ContactAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactAdapterHolder, position: Int) {
        holder.bindData(this.data[position], clickListener)
    }

    override fun getItemCount(): Int {
        return this.data.size
    }

    fun addData(data: List<Contact>){
        this.data = data
    }

}