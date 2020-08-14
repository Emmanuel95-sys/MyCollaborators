package com.emma.mycollaborators20.viewViewModel.ui.fragments.collaboratorMapDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.emma.mycollaborators20.R
import com.emma.mycollaborators20.model.CollaboratorSerializable
import com.emma.mycollaborators20.model.localdb.CollaboratorRoom
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class CollaboratorMapDetailFragment : Fragment(), OnMapReadyCallback {

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collaborator_map_detail,
        container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.detailMap)
        as SupportMapFragment
        mapFragment.getMapAsync(this)
        //catching the object
        val clickedItem = arguments?.getSerializable("collaborator")
        as CollaboratorSerializable
        Log.i("MapDetail", clickedItem.name)

    }

    override fun onMapReady(p0: GoogleMap?) {

    }

}
