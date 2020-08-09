package com.emma.mycollaborators20.model.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CollaboratorRoom::class], version = 1)
abstract  class CollaboratorDatabase : RoomDatabase() {

    abstract val collaboratorDatabaseDao : CollaboratorDatabaseDao

    companion object{

        @Volatile
        private var INSTANCE : CollaboratorDatabase ?= null

        fun getInstance(context: Context) : CollaboratorDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CollaboratorDatabase::class.java,
                        "collaborators_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}