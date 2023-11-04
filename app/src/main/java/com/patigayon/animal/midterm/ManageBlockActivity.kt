package com.patigayon.animal.midterm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ManageBlockActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: BlockedAnimalAdapter
    private val blockedAnimals = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_block)
        listView = findViewById(R.id.list_blocked_animals)
        adapter = BlockedAnimalAdapter(this, R.layout.list_item_blocked_animal, blockedAnimals)
        listView.adapter = adapter
        loadBlockedAnimals()

        val backButton = findViewById<ImageView>(R.id.back_button)

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun loadBlockedAnimals() {
        val sharedPreferences = getSharedPreferences("AnimalPrefs", MODE_PRIVATE)
        blockedAnimals.clear()
        blockedAnimals.addAll(sharedPreferences.getStringSet("BlockedAnimals", mutableSetOf())!!)
        adapter.notifyDataSetChanged()
    }

    inner class BlockedAnimalAdapter(context: Context, private val resource: Int, private val items: List<String>) :
        ArrayAdapter<String>(context, resource, items) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val layoutInflater = LayoutInflater.from(context)
            val view = convertView ?: layoutInflater.inflate(resource, parent, false)
            val animalName = items[position]
            view.findViewById<TextView>(R.id.tvAnimalName).text = animalName

            view.findViewById<TextView>(R.id.tvUnblock).setOnClickListener {
                unblockAnimal(animalName)
            }

            return view
        }

        private fun unblockAnimal(animalName: String) {
            val sharedPreferences = context.getSharedPreferences("AnimalPrefs", MODE_PRIVATE)
            val blockedAnimalsSet = sharedPreferences.getStringSet("BlockedAnimals", mutableSetOf())?.toMutableSet()
            blockedAnimalsSet?.remove(animalName)
            sharedPreferences.edit().putStringSet("BlockedAnimals", blockedAnimalsSet).apply()
            loadBlockedAnimals()
        }
    }
}
