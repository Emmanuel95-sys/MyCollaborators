package com.emma.mycollaborators20.model.localdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CollaboratorDatabaseDao {

    @Insert
    fun insert(collaborator: CollaboratorRoom)


    //get all collaborators
    @Query("SELECT * FROM collaborators_table ORDER BY id DESC")
    fun getAllCollaborators() : LiveData<List<CollaboratorRoom>>

    //get collaborator by id
    @Query("SELECT * FROM collaborators_table WHERE id = :key")
    fun getCollaborator(key: Long) : CollaboratorRoom

}