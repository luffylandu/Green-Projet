package com.example.myapplication.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.*
import org.w3c.dom.Text

class PlantAdapter(
    val context: MainActivity ,
    private val plantList:List<PlantModel> ,
    private val layoutId : Int ) : RecyclerView.Adapter<PlantAdapter.ViewHolder>(){

    // Boite a ranger tous les composants a controler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)  {

        // image de la plante
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName:TextView? = view.findViewById(R.id.name_item)
        val plantDescription:TextView? = view.findViewById(R.id.description_item)
        val starIcon=view.findViewById<ImageView>(R.id.star_icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater
            .from(parent.context)
            .inflate(layoutId,parent,false)

    return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // recuperer les information de la plante
        val currentPlant= plantList[position]

        //recuperer le repository
        val repo = plantRepository()

        //utiliser glide pour récuperer l'image a partir de son lien -> composant
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)

        // mettre a jour la description de la plante
        holder.plantName?.text=currentPlant.name

        // mettre a jour la description de la plante
        holder.plantDescription?.text=currentPlant.description

        //verifier si la plante a été liké ou non
        if(currentPlant.liked){
            holder.starIcon.setImageResource(R.drawable.ic_star)
        }
        else{
            holder.starIcon.setImageResource(R.drawable.ic_unstar)

        }
        //rajouter une interaction sur cette étoile

        holder.starIcon.setOnClickListener {
            // inverse si le bouton est like ou non
            currentPlant.liked=!currentPlant.liked
            //mettre a jour l'objet plante
            repo.updatePlant(currentPlant)
        }

        //interaction lors du clique sur une plante
        holder.itemView.setOnClickListener{
            // afficher la popup
            Plantpopup(this,currentPlant).show()
        }


    }

    override fun getItemCount(): Int = plantList.size


}