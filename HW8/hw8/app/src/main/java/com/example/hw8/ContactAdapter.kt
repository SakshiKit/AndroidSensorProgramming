package com.example.hw8

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ContactAdapter(private val context: Context, private val data: List<Contact>) :
    BaseAdapter() {
    override fun getCount(): Int = data.size
    override fun getItem(position: Int): Any = data[position]
    override fun getItemId(position: Int): Long {

        return data[position].id.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {

        val itemView: View =
            view ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val textView = itemView.findViewById<TextView>(R.id.nameTextView)
        textView.text = data[position].name

        return itemView
    }
}