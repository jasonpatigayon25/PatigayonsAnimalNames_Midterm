package com.patigayon.animal.midterm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.patigayon.animal.midterm.databinding.ActivityAnimalDetailsBinding

class AnimalDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimalDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animalName = intent.getStringExtra("animal_name")

        binding.tvAnimalName.text = animalName
        binding.tvAnimalDescription.text = getAnimalDescription(animalName)

        binding.btnBlockAnimal.apply {
            alpha = 0f
            animate().alpha(1f).setDuration(2000).setListener(null)
            setOnClickListener {
                blockAnimal(animalName)
                finish()
            }
        }
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.toolbarAnimalDetails.setNavigationOnClickListener {
            finish()
        }
    }

    private fun getAnimalDescription(animalName: String?): String {
        return when (animalName) {
            "Alligator" -> "Alligators are large reptiles found in the southeastern United States and China."
            "Bear" -> "Bears are large mammals that belong to the family Ursidae."
            "Cheetah" -> "The cheetah is a large cat native to Africa and central Iran and is the fastest land animal."
            "Dolphin" -> "Dolphins are highly intelligent marine mammals known for their playful behavior."
            "Elephant" -> "Elephants are the largest land animals on Earth, known for their complex social structures."
            "Frog" -> "Frogs are amphibians known for their jumping abilities, croaking sounds, and bulging eyes."
            "Giraffe" -> "Giraffes are the tallest mammals on Earth, with long necks and legs."
            "Hippo" -> "The hippopotamus is a large, mostly herbivorous, semi-aquatic mammal native to sub-Saharan Africa."
            "Iguana" -> "Iguanas are herbivorous lizards that are native to tropical areas of Mexico, Central America, and the Caribbean."
            "Jaguar" -> "The jaguar is a large cat species and the only extant member of the genus Panthera native to the Americas."
            "Koala" -> "The koala is an arboreal herbivorous marsupial native to Australia."
            "Lion" -> "The lion is a species in the family Felidae and a member of the genus Panthera."
            "Monkey" -> "Monkeys are non-hominoid simians, generally possessing tails and consisting of about 260 known living species."
            "Narwhal" -> "The narwhal is a medium-sized toothed whale that possesses a large 'tusk' from a protruding canine tooth."
            "Ostrich" -> "The ostrich is a large flightless bird native to Africa."
            "Penguin" -> "Penguins are a group of aquatic flightless birds living almost exclusively in the Southern Hemisphere."
            "Quail" -> "Quails are a species of mid-sized birds generally placed in the order Galliformes."
            "Rabbit" -> "Rabbits are small mammals in the family Leporidae of the order Lagomorpha."
            "Snake" -> "Snakes are elongated, legless, carnivorous reptiles of the suborder Serpentes."
            "Tiger" -> "The tiger is the largest living cat species and a member of the genus Panthera."
            "Urial" -> "The urial, also known as the arkars or shapo, is a wild sheep that is found in central and south Asia."
            "Vulture" -> "Vultures are scavenging birds of prey, feeding mostly on the carcasses of dead animals."
            "Walrus" -> "The walrus is a large marine mammal with flippers, a broad head, tusks and whiskers."
            "Xerus" -> "The xerus is a genus of African ground squirrels, which includes species commonly referred to as African ground squirrels."
            "Yak" -> "The yak is a long-haired domesticated bovid found throughout the Himalaya region of southern Central Asia."
            "Zebra" -> "Zebras are African equines with distinctive black-and-white striped coats."
            else -> "No description available."
        }
    }

    private fun blockAnimal(animalName: String?) {
        animalName?.let {
            val sharedPreferences = getSharedPreferences("AnimalPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val blockedAnimals = sharedPreferences.getStringSet("BlockedAnimals", mutableSetOf())!!
            if (!blockedAnimals.contains(it)) {
                val updatedBlockedAnimals = blockedAnimals.toMutableSet()
                updatedBlockedAnimals.add(it)
                editor.putStringSet("BlockedAnimals", updatedBlockedAnimals).apply()
            }
        }
    }
}

