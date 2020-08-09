package com.emma.mycollaborators20.model.localdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collaborators_table")
data class CollaboratorRoom(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0L,

    @ColumnInfo(name = "json_id")
    var jsonId : String,
    var name : String,
    var mail: String,
    var lat : String,
    val log : String

)
