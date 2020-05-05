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


public class RecipeDao { 
	private static Connection con = DB.getConnection();	
	
	public static String deleteRecipe(int id) throws SQLException {
		PreparedStatement stat = con.prepareStatement("DELETE FROM user WHERE id='"+id+"'");
	    int temp = stat.executeUpdate();
		return temp+" Recipe removed ";
	} 
	
	public static String addRecipe(String name,String prof) throws SQLException {
		PreparedStatement stat = con.prepareStatement("INSERT INTO user (name, profession) VALUES ('"+name+"', '"+prof+"')");
	    int temp = stat.executeUpdate();
		return temp+" Person added";
	}
	
	
	
	
	String res[] = new String[12];
	String used[] = new String[12];
	String src[] = new String[12];
	String titel[] = new String[12];
	String unused[] = new String[12];
	
    public static String execute(String content, int option) throws IOException, JSONException { 
	 System.setProperty("http.agent", "Firefox");
     int count = 1 + content.length() - content.replaceAll(",","").length();
     content = content.replaceAll(" ", "%20");
     
     URL url = new URL("https://api.spoonacular.com/recipes/findByIngredients?apiKey=e672224c04114dcfbec9e42bad48afe3&ingredients="+content+"&ranking="+option+"&number=12");
     HttpURLConnection con = (HttpURLConnection) url.openConnection();
    
     con.setDoOutput(true);
     con.setRequestMethod("GET");
     con.setRequestProperty("Content-Type", "application/json");
     
     BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
     String output = "{results:"+br.readLine()+"}";
     
     JSONObject[] jsonobject = new JSONObject[12];
     
     for(int i=0; i<12; i++) {
    	 jsonobject[i] = new JSONObject(output);
     }
         
         JSONArray[] jsonarray = new JSONArray[12];
         
         for(int i=0; i<12; i++) {
        	 jsonarray[i] = jsonobject[i].getJSONArray("results");
         }
	          
	          content = content.replaceAll("%20", " ");
	          content="\n"+"Your search: "+content;
	          
	          for(int i=0; i<12; i++) {
	        	  jsonobject[i] = jsonarray[i].getJSONObject(i);
	          }

	          
	           int id[] = new int[12];
	           
	           for(int i=0; i<12; i++) {
	        	   id[i] = jsonobject[i].getInt("id");
	           }
	           
	           String titel[] = new String[12];
	           
	           for(int i=0; i<12; i++) {
	        	   titel[i] = jsonobject[i].getString("title");
	           }
	           
	           String src[] = new String[12];
	           
	           for(int i=0; i<12; i++) {
	        	   src[i] = jsonobject[i].getString("image");
	           }
	           
	           JSONObject jsono[] = new JSONObject[12];
	           
	           for(int i=0; i<12; i++) {
	        	   jsono[i] = jsonobject[i];
	           }
	           
	           JSONArray a[] = new JSONArray[12];
	           
	           for(int i=0; i<12; i++) {
	        	   a[i] = jsono[i].getJSONArray("usedIngredients");
	           }
	           
	           JSONArray array[] = new JSONArray[12];
	           
	           for(int i=0; i<12; i++) {
	        	   array[i] = jsono[i].getJSONArray("missedIngredients");
	           }
	           
	           String used[] = new String[12];
	           
	           for(int i=0; i<12; i++) {
	        	   used[i] = "";
	           }
	           
	           String unused[] = new String[12];
	           
	           for(int i=0; i<12; i++) {
	        	   unused[i] = "";
	           }
	           
	           for(int i=0; i<12; i++) {
	        	   
	        	   for (int j = a[i].length()-1; j >=0; j--) {
			        	jsono[i] = a[i].getJSONObject(j);
			        	if(j==0) {used[i] = used[i]+jsono[i].getString("name");}
			          	else {used[i] = used[i]+jsono[i].getString("name")+", ";}
			        	}   
	           }
		        
	           for(int i=0; i<12; i++) {
	        	   
	        	   for (int j = array[i].length()-1; j >=0; j--) {
			        	jsono[i] = array[i].getJSONObject(j);
			        	if(j==0) {unused[i] = unused[i]+jsono[i].getString("name");}
			          	else {unused[i] = unused[i]+jsono[i].getString("name")+", ";}
			        	}   
	           }
		        
	           String res[] = new String[12];
	           
	           for(int i=0; i<12; i++) {
	        	   res[i] = " (" + a[i].length() + "/" + count + " ingredients, " + array[i].length() + " missing)";
	           }
		        
		    	String rec = "[";
		    	
		    	   for(int i=0; i<12; i++){

		   	        rec=rec+"{\"image\":\""+src[i]+"\",\"" + 
		   	        		"title\":\""+titel[i]+"\",\""  +
		   	        		"res\":\""+res[i]+"\",\"" + 
		   	        		"usedIngredients\":\""+used[i]+"\",\"" +
		   	        		"missedIngredients\":\""+unused[i]+"\",\"" +
		   	        		"id\":"+id[i]+"}";  
		   	        	if(i!=11) {
		   	        		rec=rec+",";
		   	        	}
		   	    	}
		    	   
		    	   rec = rec + "]";

				return rec;
	
		}
	
	 public static List<Recipe> getAllRecipes() throws SQLException{ 
		 	 
	 //Database
     List<Recipe> recipeList = null;
     
     PreparedStatement stat = con.prepareStatement("CREATE TABLE IF NOT EXISTS user (id int, name varchar(255), profession varchar(255))");
     stat.executeUpdate();
     
     PreparedStatement statm = con.prepareStatement("SELECT * FROM user");
     ResultSet rs = statm.executeQuery();
 
     if(rs.next()==false) {
    	 PreparedStatement sta = con.prepareStatement("INSERT INTO user (id, name, profession) VALUES ('1', 'Mahesh', 'Teacher')");
         sta.executeUpdate();
     }
     
     PreparedStatement sta = con.prepareStatement("SELECT * FROM user");
     ResultSet s = sta.executeQuery();
     
     String src, titel, res, used, unused;
     int id;
     
     recipeList = new ArrayList<Recipe>(); 
     
	    while (s.next()){
	        
	        src = s.getString("");
	        titel = s.getString("");
	        res = s.getString("");
	        used = s.getString("");
	        unused = s.getString("");
	        id = s.getInt("id");

	        Recipe user = new Recipe(src, titel, res, used, unused, id);
	        recipeList.add(user);  	        
	    	}
	    return recipeList; 
   }	 
}