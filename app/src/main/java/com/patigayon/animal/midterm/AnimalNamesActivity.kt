package com.patigayon.animal.midterm

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.patigayon.animal.midterm.databinding.ActivityAnimalNamesBinding

class AnimalNamesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimalNamesBinding
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
        binding = ActivityAnimalNamesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayedAnimals)
        binding.listAnimals.adapter = adapter
        sharedPreferences = getSharedPreferences("AnimalPrefs", MODE_PRIVATE)

        binding.listAnimals.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, AnimalDetailsActivity::class.java).apply {
                putExtra("animal_name", displayedAnimals[position])
            }
            startActivity(intent)
        }

        binding.btnManageBlockedAnimals.setOnClickListener {
            val intent = Intent(this, ManageBlockActivity::class.java)
            startActivity(intent)
        }

        updateAnimalsList()
    }

    override fun onResume() {
        super.onResume()
        updateAnimalsList()
    }

    private fun updateAnimalsList() {
        val blockedAnimals = sharedPreferences.getStringSet("BlockedAnimals", setOf()) ?: setOf()
        displayedAnimals.clear()
        displayedAnimals.addAll(allAnimals.filter { it !in blockedAnimals })
        adapter.notifyDataSetChanged()
    }
}
