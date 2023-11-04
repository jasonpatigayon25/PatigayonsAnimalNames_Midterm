package com.patigayon.animal.midterm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.patigayon.animal.midterm.databinding.ActivityManageBlockBinding
import com.patigayon.animal.midterm.databinding.ListItemBlockedAnimalBinding

class ManageBlockActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageBlockBinding
    private lateinit var adapter: BlockedAnimalAdapter
    private val blockedAnimals = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageBlockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = BlockedAnimalAdapter(this, blockedAnimals)
        binding.listBlockedAnimals.adapter = adapter
        loadBlockedAnimals()

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun loadBlockedAnimals() {
        val sharedPreferences = getSharedPreferences("AnimalPrefs", MODE_PRIVATE)
        blockedAnimals.clear()
        blockedAnimals.addAll(sharedPreferences.getStringSet("BlockedAnimals", mutableSetOf())!!)
        adapter.notifyDataSetChanged()
    }

    inner class BlockedAnimalAdapter(context: Context, private val items: List<String>) :
        ArrayAdapter<String>(context, 0, items) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val itemBinding: ListItemBlockedAnimalBinding = if (convertView == null) {
                ListItemBlockedAnimalBinding.inflate(LayoutInflater.from(context), parent, false)
            } else {
                ListItemBlockedAnimalBinding.bind(convertView)
            }
            val animalName = items[position]
            itemBinding.tvAnimalName.text = animalName

            itemBinding.tvUnblock.setOnClickListener {
                unblockAnimal(animalName)
            }

            return itemBinding.root
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
