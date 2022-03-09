package com.dhatuker.zwallet.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhatuker.zwallet.R
import com.dhatuker.zwallet.adapter.TransactionAdapter
import com.dhatuker.zwallet.data.Transaction

class MainActivity : AppCompatActivity() {

    private val transactionData = mutableListOf<Transaction>()
    lateinit var transactionAdapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.transactionAdapter = TransactionAdapter(transactionData)
        val recycle : RecyclerView = findViewById(R.id.recycleTransaction)
        val layoutManager = LinearLayoutManager(applicationContext)
        recycle.layoutManager = layoutManager
        recycle.adapter = transactionAdapter
        prepareData()
        }

    fun prepareData(){
        this.transactionData.add(Transaction(
            transactionImage = getDrawable(R.drawable.img)!!,
            transactionName = "Dhatu Kertayuga",
            transactionType = "Transfer",
            transactionNominal = 129399.00
        ))
    }
}