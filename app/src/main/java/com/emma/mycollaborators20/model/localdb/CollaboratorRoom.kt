package com.emma.mycollaborators20.model.localdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collaborators_table")
data class CollaboratorRoom(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0L,
    @ColumnInfo(name = "json_id")
    var jsonId:String = "no json id",
    var name : String = "Dummy name",
    var mail: String = "defaultemail@dummy.com",
    var lat : String = "19.7824456",
    val log : String = "-99.3975891"

)
