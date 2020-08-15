package com.emma.mycollaborators20.viewViewModel.ui.fragments.collaboratorMapDetail

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.emma.mycollaborators20.R
import com.emma.mycollaborators20.model.CollaboratorSerializable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class CollaboratorMapDetailFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    lateinit var clickedItem : CollaboratorSerializable

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
        clickedItem = arguments?.getSerializable("collaborator")
        as CollaboratorSerializable
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val zoom = 18f
        val centerMap = LatLng(clickedItem.lat.toDouble(), clickedItem.log.toDouble())
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(centerMap, zoom))

        val markerOptions = MarkerOptions()
        markerOptions.position(centerMap)
        markerOptions.title(clickedItem.name)
        val bitMapCollaborator = context?.applicationContext?.let {
            ContextCompat.getDrawable(it, R.drawable.person_icon)
        } as BitmapDrawable
        val smallMarker = Bitmap.createScaledBitmap(bitMapCollaborator.bitmap,
            150,150,false)
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
        //add marker to map
        googleMap?.addMarker(markerOptions)
        googleMap?.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return true
    }

}
