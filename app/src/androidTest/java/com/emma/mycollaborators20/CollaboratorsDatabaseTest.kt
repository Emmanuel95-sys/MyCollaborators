package com.emma.mycollaborators20

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.emma.mycollaborators20.model.localdb.CollaboratorDatabase
import com.emma.mycollaborators20.model.localdb.CollaboratorDatabaseDao
import com.emma.mycollaborators20.model.localdb.CollaboratorRoom
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var collaboratorDao: CollaboratorDatabaseDao
    private lateinit var db: CollaboratorDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, CollaboratorDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        collaboratorDao = db.collaboratorDatabaseDao

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCollaborator() {
        val collaborator = CollaboratorRoom(1,"145", "Emmanuel", "emma@gmail.com", "19.2", "-45.21")
        collaboratorDao.insert(collaborator)

        val collaboratorFromDb = collaboratorDao.getCollaborator(1)
        Log.i("TestCase", collaboratorFromDb.toString())
        Assert.assertEquals(collaboratorFromDb, collaborator)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCollaborators() {

        val collaborator = CollaboratorRoom(1,"145", "Emmanuel", "emma@gmail.com", "19.2", "-45.21")
        collaboratorDao.insert(collaborator)

        val collaborator2 = CollaboratorRoom(2,"148", "Diana", "Diana@gmail.com", "19.782", "-45.2141")
        collaboratorDao.insert(collaborator2)

        val collaboratorsLiveData = collaboratorDao.getAllCollaborators()
        val collaboratorsList = collaboratorsLiveData.value

        Log.i("TestCase2", collaboratorsList.toString())
        Assert.assertEquals(2,collaboratorsList?.size )
    }


}
