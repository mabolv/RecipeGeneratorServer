package com.tutorialspoint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; 
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipeInfo { 
	//metod för att få info om recepten och skapa JSON
    public static String execute(int id) throws IOException, JSONException { 
	 System.setProperty("http.agent", "Firefox");
	 
	 URL url2 = new URL("https://api.spoonacular.com/recipes/"+id+"/information?apiKey=e672224c04114dcfbec9e42bad48afe3&includeNutrition=true");
     HttpURLConnection con2 = (HttpURLConnection) url2.openConnection();
    
     con2.setDoOutput(true);
     con2.setRequestMethod("GET");
     con2.setRequestProperty("Content-Type", "application/json");
     
     BufferedReader br2 = new BufferedReader(new InputStreamReader((con2.getInputStream())));
     
     String output2 = br2.readLine();
     
     output2 = "{results:["+output2+"]}";
	 
     JSONObject jsonobject2 = new JSONObject(output2);
     JSONArray jsonarray2 = new JSONArray();
	 
     jsonarray2 = jsonobject2.getJSONArray("results");
     jsonobject2 = jsonarray2.getJSONObject(0);
       JSONObject jsono2 = new JSONObject();
       jsono2 = jsonobject2;
     
       String title, image, sum, pairwine = "", pairtext ="", winetitle="", desc="", imageurl="", unit;
       int rim, serv, am;
       
       title = jsonobject2.getString("title");
       rim = jsonobject2.getInt("readyInMinutes");
       serv = jsonobject2.getInt("servings");
       image = jsonobject2.getString("image");
       try {
       sum = jsonobject2.getString("summary");}catch(Exception e) {sum="No summary found";}
       
       JSONObject a2 = new JSONObject();
       try {
       a2 = jsono2.getJSONObject("winePairing");}
       catch(Exception e) {}
       

       
       JSONArray jsonarray3 = new JSONArray();
       try {
       jsonarray3 = a2.getJSONArray("pairedWines");}
       catch(Exception e){}
       for(int i=0; i<jsonarray3.length(); i++) {
    	   if(i==jsonarray3.length()-1) {
        	   pairwine = pairwine+jsonarray3.getString(i);
           }
    	   else {
    	   pairwine = pairwine+jsonarray3.getString(i)+", ";
    	   }
       }
       try {
       pairtext = a2.getString("pairingText");}
       catch(Exception e) {}
       
       
       JSONArray jsonarray4 = new JSONArray();
       try {
       jsonarray4 = a2.getJSONArray("productMatches");}
       catch(Exception e) {}
       
       JSONObject json = new JSONObject();
       try {
       json=jsonarray4.getJSONObject(0);
       
       winetitle=json.getString("title");
       desc=json.getString("description");
       desc=desc.replaceAll("\"", "");
       imageurl=json.getString("imageUrl");
       }
       catch(Exception e) {}
       
       JSONObject a3 = new JSONObject();
       a3 = jsono2.getJSONObject("nutrition");
       JSONArray jsonarray5 = new JSONArray();
       jsonarray5 = a3.getJSONArray("nutrients");
       
       JSONObject json1 = new JSONObject();
       json1=jsonarray5.getJSONObject(0);
       
       am=json1.getInt("amount");
       unit=json1.getString("unit");
       
       if(sum.contains("\"")) {
			sum = sum.replace("\"","¨");
		}
       
       
       String rec2 = "{\"title\":\""+title+"\","+
    		   			"\"rim\":\""+rim+"\","+
    		   			"\"serv\":\""+serv+"\","+
    		   			"\"rimage\":\""+image+"\","+
    		   			"\"sum\":\""+sum+"\","+
    		   			"\"pairwine\":\""+pairwine+"\","+
    		   			"\"pairtext\":\""+pairtext+"\","+
    		   			"\"winetitle\":\""+winetitle+"\","+
    		   			"\"desc\":\""+desc+"\","+
    		   			"\"imageurl\":\""+imageurl+"\","+
    		   			"\"amount\":\""+am+"\","+
    		   			"\"unit\":\""+unit+"\"}]";
       
     //nytt anrop för mer info
     URL url = new URL("https://api.spoonacular.com/recipes/"+id+"/analyzedInstructions?apiKey=e672224c04114dcfbec9e42bad48afe3");
     HttpURLConnection con = (HttpURLConnection) url.openConnection();
    
     con.setDoOutput(true);
     con.setRequestMethod("GET");
     con.setRequestProperty("Content-Type", "application/json");
     
     BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
     
     String output = br.readLine();
     if(output.equals("[]")) {
    	 output="{results:"+"[{\"steps\":[{\"step\":\"Instructions for this recipe is not available from the API unfortunately.\""+"}]}]}";
     }
     else {
     output = "{results:"+output+"}";
     }
     JSONObject jsonobject = new JSONObject(output);
     JSONArray jsonarray = new JSONArray();
     
     jsonarray = jsonobject.getJSONArray("results");
	         jsonobject = jsonarray.getJSONObject(0);
	           JSONObject jsono = new JSONObject();
	           jsono = jsonobject;
	           
	           JSONArray a = new JSONArray();
	           a = jsono.getJSONArray("steps");
	           
	           String rec = "[";
	           
	           JSONObject eqj = new JSONObject();
	           for(int i=0; i<a.length(); i++) {
	        	   eqj = a.getJSONObject(i);
	        	   JSONObject eqjs = new JSONObject();
	        	   eqjs = eqj;
	        	   JSONArray b = new JSONArray();
	        	   try {
	        	   b = eqjs.getJSONArray("equipment");}
	        	   catch(Exception e){
	        	   }
	        	   
	        	   String eq[] = new String[b.length()];
	        	   String img[] = new String[b.length()];
	        	   for (int j = 0; j <=b.length()-1; j++) {
	        		   eqjs = b.getJSONObject(j);
	        		   img[j] = eqjs.getString("image");
	        		   eqjs = b.getJSONObject(j);
	        		   eq[j] = eqjs.getString("name");
	        		   
			    			rec=rec+"{\"image\":\""+img[j]+"\","
			    					+ "\"name\":\""+eq[j]+"\","
			    					+ "\"ingrimage\":\"\","
			    					+ "\"ingrname\":\"\","
			    					+ "\"number\":\"\","
			    					+ "\"step\":\"\"},";
	        	   }	   
	           }
	           
	           JSONObject eqjo = new JSONObject();
	           try {
	           for(int i=0; i<a.length(); i++) {
	        	   eqjo = a.getJSONObject(i);
	        	   JSONObject eqjs = new JSONObject();
	        	   eqjs = eqjo;
	        	   JSONArray b = new JSONArray();
	        	   
	        	   b = eqjs.getJSONArray("ingredients");
	           
	        	   String eq[] = new String[b.length()];
	        	   String img[] = new String[b.length()];
	        	   for (int j = 0; j <=b.length()-1; j++) {
	        		   eqjs = b.getJSONObject(j);
	        		   img[j] = eqjs.getString("image");
	        		   eqjs = b.getJSONObject(j);
	        		   eq[j] = eqjs.getString("name");

			    			rec=rec+"{\"image\":\"\","
			    					+ "\"name\":\"\","
			    					+ "\"ingrimage\":\""+img[j]+"\","
			    					+ "\"ingrname\":\""+eq[j]+"\","
			    					+ "\"number\":\"\","
			    					+ "\"step\":\"\"},";
	        	   }	   
	           }
	           }
	           catch(Exception e) {}

	           int nbr[] = new int[a.length()];
	           String step[] = new String[a.length()];
	           try {
	        	   for (int j = 0; j <=a.length()-1; j++) {
			        	jsono = a.getJSONObject(j);
			        	step[j] = jsono.getString("step");
			        	nbr[j] = jsono.getInt("number");
			       }
	           }
	           catch(Exception e) {}
		    	for(int i=0; i<=a.length()-1; i++) {
		    		if(step[i].contains("\"")) {
		    			step[i] = step[i].replace("\"","¨");
		    		}
		    		if(i==a.length()-1) {
		    			rec=rec+"{\"image\":\"\","
		    					+ "\"name\":\"\","
		    					+ "\"ingrimage\":\"\","
		    					+ "\"ingrname\":\"\","
		    					+ "\"number\":"+nbr[i]+",";
		    			rec=rec+"\"step\":\""+step[i]+"\"}";
		    		}
		    		else {
		    			rec=rec+"{\"image\":\"\","
		    					+ "\"name\":\"\","
		    					+ "\"ingrimage\":\"\","
		    					+ "\"ingrname\":\"\","
		    					+"\"number\":"+nbr[i]+",";
		    			rec=rec+"\"step\":\""+step[i]+"\"},"; 
		    		}	
		    	}

		    	rec = rec+",";
		    	rec = rec+rec2;
				return rec;
		}	
}