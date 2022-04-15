package com.example.myapplication

import AddPlantFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.fragments.Collection_Fragment
import com.example.myapplication.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(HomeFragment(this))

        val NavigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        NavigationView.setOnNavigationItemSelectedListener  {
            when (it.itemId)
            {
                R.id.home_page -> {
                    loadFragment(HomeFragment(this))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.collection_page -> {
                    loadFragment(Collection_Fragment(this))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_plant_page -> {
                    loadFragment(AddPlantFragment(this))
                    return@setOnNavigationItemSelectedListener  true
                }
                else -> false
            }
        }




    }

    private fun loadFragment(fragment: Fragment) {
        //charger notre repository
        val repo = plantRepository()

        //mette a jour la liste de plante
        repo.updateData {
            //injecter le fragment dans notre boire (fragment_container)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()

        }
    }

}