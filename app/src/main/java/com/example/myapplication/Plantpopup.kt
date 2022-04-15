package com.example.myapplication

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.adapter.PlantAdapter

class Plantpopup(
    private val adapter: PlantAdapter,
    private val currentPlant: PlantModel)
    : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_plant_details)
        setupComponents()
        setupCloseButton()
        setupDeleteButton()
        setupStarButton()
    }

    private fun setupStarButton() {
        // recuperer
        val starButton =findViewById<ImageView>(R.id.star_button)

        if(currentPlant.liked) {
            starButton.setImageResource(R.drawable.ic_star)
        }
        else{
            starButton.setImageResource(R.drawable.ic_unstar)
        }
        //interaction
        starButton.setOnClickListener {
            currentPlant.liked=!currentPlant.liked
            val repo = plantRepository()
            repo.updatePlant(currentPlant)

            if(currentPlant.liked) {
                starButton.setImageResource(R.drawable.ic_star)
            }
            else{
                starButton.setImageResource(R.drawable.ic_unstar)
            }
        }
    }

    private fun setupDeleteButton() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener {
            // supprimer la plante de la Bdd
            val repo =plantRepository()
            repo.deletePlant(currentPlant)
            dismiss()
        }
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener {
            //fermer la fenetre
        dismiss()
        }
    }

    private fun setupComponents() {
    //actualiser   l'image de la plante
        val plantImage=findViewById<ImageView>(R.id.image_item)
        Glide.with(adapter.context).load(Uri.parse(currentPlant.imageUrl)).into(plantImage)

        //actualiser le nom de la plante
        findViewById<TextView>(R.id.popup_plant_name).text=currentPlant.name

        //Actualiser la description de la plante
        findViewById<TextView>(R.id.popup_plant_description_subtitle).text=currentPlant.description

        //Actualiser la croissance de la plante
        findViewById<TextView>(R.id.popup_plant_grow_subtitle).text=currentPlant.grow

        //Actualiser la consomation de la plante
        findViewById<TextView>(R.id.popup_plant_water_subtitle).text=currentPlant.water

    }
}