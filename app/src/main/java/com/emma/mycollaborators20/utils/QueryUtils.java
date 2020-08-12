package com.emma.mycollaborators20.utils;

import android.util.Log;

import com.emma.mycollaborators20.model.Collaborator;
import com.emma.mycollaborators20.model.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {


    public static List<Collaborator> extractCollaboratorsFromJsonString(String collaboratorsJsonString){
        //create an empty list
        ArrayList<Collaborator> collaborators = new ArrayList<>();
        //try to parse the JsonString If there's a problem with the way the JSON
        //is formatted, a JSONException object will be thrown
        // Catch the exception so the app doesn't crash
        try{
            JSONObject root = new JSONObject(collaboratorsJsonString);
            JSONObject data = root.getJSONObject("data");

            JSONArray employees = data.getJSONArray("employees");

            for(int i = 0; i < employees.length(); i++){
                //reference the current employee object
                JSONObject employee = employees.getJSONObject(i);

                //reference the location JSON object
                JSONObject locationJSON = employee.getJSONObject("location");
                String lat = locationJSON.getString("lat");
                String log = locationJSON.getString("log");

                //create Location model object
                Location currentLocation = new Location(lat, log);
                Log.i("LOCATION OBJECT", currentLocation.toString());

                //get reference to the variables
                String id = employee.getString("id");
                String name = employee.getString("name");
                String mail = employee.getString("mail");
                //create Collaborator object
                Collaborator collaborator = new Collaborator(id,currentLocation, mail, name);

                //Add the collaborator to the list
                collaborators.add(collaborator);
            }

        }catch (JSONException e){
            Log.e("QueryUtils","There is a problem parsing the  JSON results",e);
        }
        Log.i("QueryUtils", collaborators.toString());
        return collaborators;
    }

}
