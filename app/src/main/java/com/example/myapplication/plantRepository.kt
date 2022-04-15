package com.example.myapplication

import android.net.Uri
import com.example.myapplication.plantRepository.Singleton.databaseRef
import com.example.myapplication.plantRepository.Singleton.downloadUri
import com.example.myapplication.plantRepository.Singleton.plantList
import com.example.myapplication.plantRepository.Singleton.storageReference
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.*
import javax.security.auth.callback.Callback

class plantRepository {


    object Singleton {


    //donner le lien au bucket
        private var BUCKET_Url : String =  "gs://green-d0256.appspot.com"

    // se connecter a la reference "plants"

    val  databaseRef = FirebaseDatabase.getInstance().getReference("plants");

    //creer une liste qui va contenir nos plants
    val plantList = arrayListOf<PlantModel>()

     // se connecter a notre espace de stockage
     val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_Url)

     //contenir le lien de l'image courante
     var downloadUri: Uri? =null
    }

    fun updateData(callback: () -> Unit){
        //absorber les donnés depuis la databaseRef -> liste de plante
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                //retirer les ancienne plantes
                plantList.clear()
                //recolter la liste
                for ( ds in p0.children){
                    //construire un objet plante
                    val plant = ds.getValue(PlantModel::class.java)

                    //verifier que le plante n'est pas null
                    if (plant!= null){
                        //ajouter la plante a notre liste
                        plantList.add(plant)

                    }
                }
                //actioner le callback
                callback()
            }

            override fun onCancelled(p0: DatabaseError) {}


        })

    }
    //creer une fonction pour envoyer des fichiers sur le storage
    fun uploadImage(file: Uri, callback: () -> Unit) {
        //verifier que ce fichier n'est pas null
        if (file != null) {
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)

            //demarer la tache d'envoi
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                // verifier si y a eu probleme lors de l'envoi du fichier
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                return@Continuation ref.downloadUrl
            }).addOnCompleteListener { task ->

                //verifier si touta bien fonctioner
                if (task.isSuccessful) {
                     downloadUri = task.result
                    callback()
                }
            }
        }
    }
    //mettre a jour un objet plante en BDD
    fun updatePlant(plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)

    //inserer un nouvel objet plante en BDD
    fun insertPlant(plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)


    // supprimer une plante
    fun deletePlant(plant:PlantModel)= databaseRef.child(plant.id).removeValue()
}