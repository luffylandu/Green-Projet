package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.adapter.PlantAdapter
import com.example.myapplication.adapter.PlantItemDecoration
import com.example.myapplication.plantRepository.Singleton.plantList

class Collection_Fragment(
    private val context: MainActivity
) : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //recuperer ma recyclerview
        val view =inflater?.inflate(R.layout.fragment_collection,container,false)
        val collectionRecyclerView =view.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecyclerView.adapter=PlantAdapter(context,plantList.filter { it.liked },R.layout.item_vertical_plant)
        collectionRecyclerView.layoutManager= LinearLayoutManager(context)
        collectionRecyclerView.addItemDecoration(PlantItemDecoration())
        return view
    }


}