package com.emma.mycollaborators20.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emma.mycollaborators20.R
import com.emma.mycollaborators20.model.localdb.CollaboratorRoom

class CollaboratorAdapter :  RecyclerView.Adapter<CollaboratorAdapter.ViewHolder>() {

    var collaboratorsAdapter = listOf<CollaboratorRoom>()
    //notifying RV about changes
    set(value){
        field = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): CollaboratorAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.collaborator_list_item,
            parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return collaboratorsAdapter.size
    }

    override fun onBindViewHolder(holder: CollaboratorAdapter.ViewHolder, position: Int) {
        val currentCollaborator  = collaboratorsAdapter[position]
        holder.itemCollaboratorName.text = currentCollaborator.name
        holder.itemCollaboratorEmail.text = currentCollaborator.mail
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var itemCollaboratorName = itemView.findViewById<TextView>(R.id.tvItemCollaboratorName)
        var itemCollaboratorEmail = itemView.findViewById<TextView>(R.id.tvItemCollaboratorEmail)
    }


}