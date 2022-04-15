
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.PlantModel
import com.example.myapplication.R
import com.example.myapplication.plantRepository
import com.example.myapplication.plantRepository.Singleton.downloadUri
import java.util.*

class AddPlantFragment(
    private val context: MainActivity) : Fragment(){
    private var uploadedImage:ImageView?=null
    private var file: Uri?=null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_plant, container, false)

        //récuperer uploadedImage pour lui associer son composant
        uploadedImage = view.findViewById(R.id.preview_image)

        //récuperer le boutton uploader l'image
        val pickupImageButton=view.findViewById<Button>(R.id.upload_button)

        //lorsqu'on appuie dessus on récupere les images du telephone
        pickupImageButton.setOnClickListener { pickupImage() }

        // recuperer le bouton confirmer
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
         confirmButton.setOnClickListener{
             sendForm(view)}





        return view
    }

    private fun sendForm(view: View) {

        val repo=plantRepository()
        repo.uploadImage(file!!) {
            val plantName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val plantDesciption = view.findViewById<EditText>(R.id.description_input).text.toString()
            val grow = view.findViewById<Spinner>(R.id.grow_spinner).selectedItem.toString()
            val water = view.findViewById<Spinner>(R.id.water_spinner).selectedItem.toString()
            val downloadImageUrl= downloadUri
            val plant =PlantModel(
                UUID.randomUUID().toString(),
                plantName,
                plantDesciption,
                downloadImageUrl.toString(),
                grow,
                water
            )
            repo.insertPlant(plant)
        }
        }

    private fun pickupImage() {
        val intent=Intent()
        intent.type="image/"
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode,resultCode,data)
        if(requestCode==47&& resultCode == Activity.RESULT_OK){
            //verifier si les données sont nulles
            if(data==null|| data.data == null) return

            //recuperer l'image
            val file=data.data

            //mettre a jour l'apercu de l'image
            uploadedImage?.setImageURI(file)





        }
    }


}