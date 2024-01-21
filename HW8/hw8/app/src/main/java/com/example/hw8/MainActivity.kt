package com.example.hw8

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var contactsList: List<Contact> = emptyList()
    private lateinit var fabAdd: FloatingActionButton
    lateinit var listView: ListView
    lateinit var adapter: ContactAdapter

    private lateinit var contactDao: ContactDao

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val results = result.data?.extras


                lifecycleScope.launch {
                    contactDao.insert(
                        Contact(
                            name = results?.get("name").toString(),
                            phone = results?.get("phone").toString(),
                            email = results?.get("email").toString()
                        )
                    )
                    contactsList = contactDao.getAllContacts()
                }
                adapter = ContactAdapter(this, contactsList)
                listView.adapter = adapter

                adapter.notifyDataSetChanged()


//                textViewName.text = "Name: ${results?.get("name")}"
//                textViewPhone.text = "Phone: ${results?.get("phone")}"
//                textViewEmail.text = "Email: ${results?.get("email")}"


            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabAdd = findViewById(R.id.fabAddContact)
        listView = findViewById(R.id.listView)

        adapter = ContactAdapter(this, contactsList)
        listView.adapter = adapter



        fabAdd.setOnClickListener {
            startForResult.launch(Intent(this, ActivityTwo::class.java))
        }

        listView.setOnItemClickListener { adapterView, view, pos, l ->
            val selectedItem = contactsList[pos]
            val intent = Intent(this, ActivityThree::class.java)
            intent.putExtra("name", selectedItem.name)
            intent.putExtra("phone", selectedItem.phone)
            intent.putExtra("email", selectedItem.email)
            startActivity(intent)
        }

        val db = Room.databaseBuilder(
            applicationContext,
            ContactsDatabase::class.java, "contactsdatabase"
        ).build()

        contactDao = db.contactDao()

        lifecycleScope.launch {

            contactsList = contactDao.getAllContacts()
            adapter = ContactAdapter(this@MainActivity, contactsList)
            listView.adapter = adapter
            adapter.notifyDataSetChanged()

        }
    }
}