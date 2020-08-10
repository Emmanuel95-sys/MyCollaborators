package com.emma.mycollaborators20.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emma.mycollaborators20.R
import com.emma.mycollaborators20.model.localdb.CollaboratorRoom

class CollaboratorAdapter :  RecyclerView.Adapter<CollaboratorAdapter.ViewHolder>() {

    var collaboratorsData = listOf<CollaboratorRoom>()

    //notifying RV about changes
    set(value){
        field = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.collaborator_list_item,
            parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return collaboratorsData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCollaborator  = collaboratorsData[position]
        holder.itemCollaboratorName.text = currentCollaborator.name
        holder.itemCollaboratorEmail.text = currentCollaborator.mail
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var itemCollaboratorName : TextView = itemView.findViewById(R.id.tvItemCollaboratorName)
        var itemCollaboratorEmail : TextView = itemView.findViewById(R.id.tvItemCollaboratorEmail)
    }
}