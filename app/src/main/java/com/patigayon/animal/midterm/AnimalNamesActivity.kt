package com.patigayon.animal.midterm

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class AnimalNamesActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val allAnimals = listOf(
        "Alligator", "Bear", "Cheetah", "Dolphin", "Elephant",
        "Frog", "Giraffe", "Hippo", "Iguana", "Jaguar",
        "Koala", "Lion", "Monkey", "Narwhal", "Ostrich",
        "Penguin", "Quail", "Rabbit", "Snake", "Tiger",
        "Urial", "Vulture", "Walrus", "Xerus", "Yak",
        "Zebra"
    )
    private val displayedAnimals = mutableListOf<String>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_names)

        // Initialize the ListView and SharedPreferences
        listView = findViewById(R.id.list_animals)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayedAnimals)
        listView.adapter = adapter
        sharedPreferences = getSharedPreferences("AnimalPrefs", MODE_PRIVATE)

        // Set up click listener for each animal in the list
        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, AnimalDetailsActivity::class.java).apply {
                putExtra("animal_name", displayedAnimals[position])
            }
            startActivity(intent)
        }

        // Button to manage blocked animals
        findViewById<Button>(R.id.btn_manage_blocked_animals).setOnClickListener {
            val intent = Intent(this, ManageBlockActivity::class.java)
            startActivity(intent)
        }

        // Load animals into the list
        updateAnimalsList()
    }

    override fun onResume() {
        super.onResume()
        // Refresh the list of animals when coming back to this activity
        updateAnimalsList()
    }

    private fun updateAnimalsList() {
        // Retrieve the set of blocked animals from SharedPreferences
        val blockedAnimals = sharedPreferences.getStringSet("BlockedAnimals", setOf()) ?: setOf()
        // Update the displayedAnimals list to exclude blocked animals
        displayedAnimals.clear()
        displayedAnimals.addAll(allAnimals.filter { it !in blockedAnimals })
        // Notify the adapter that the dataset has changed to refresh the list view
        adapter.notifyDataSetChanged()
    }
}
