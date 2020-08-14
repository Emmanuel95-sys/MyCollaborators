package com.emma.mycollaborators20.utils

import android.util.Log
import com.emma.mycollaborators20.model.Collaborator
import com.emma.mycollaborators20.model.Location
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class QueryUtilsKt {
    fun extractCollaboratorsFromJsonString(collaboratorsJsonString: String?): List<Collaborator>? {
        //create an empty list
        val collaborators = ArrayList<Collaborator>()
        //try to parse the JsonString If there's a problem with the way the JSON
        //is formatted, a JSONException object will be thrown
        // Catch the exception so the app doesn't crash
        try {
            val root = JSONObject(collaboratorsJsonString)
            val data = root.getJSONObject("data")
            val employees = data.getJSONArray("employees")
            for (i in 0 until employees.length()) {
                //reference the current employee object
                val employee = employees.getJSONObject(i)

                //reference the location JSON object
                val locationJSON = employee.getJSONObject("location")
                val lat = locationJSON.getString("lat")
                val log = locationJSON.getString("log")

                //create Location model object
                val currentLocation =
                    Location(lat, log)
                Log.i("LOCATION OBJECT", currentLocation.toString())

                //get reference to the variables
                val id = employee.getString("id")
                val name = employee.getString("name")
                val mail = employee.getString("mail")
                //create Collaborator object
                val collaborator = Collaborator(id, currentLocation, mail, name)

                //Add the collaborator to the list
                collaborators.add(collaborator)
            }
        } catch (e: JSONException) {
            Log.e("QueryUtils", "There is a problem parsing the  JSON results", e)
        }
        Log.i("QueryUtils", collaborators.toString())
        return collaborators
    }


}
