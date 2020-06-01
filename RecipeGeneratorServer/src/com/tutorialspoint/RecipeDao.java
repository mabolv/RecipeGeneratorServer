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
	//inloggning
public static String Login(String user, String pass) throws SQLException {
		
		String g = "";
		PreparedStatement sta = con.prepareStatement("select user from login where user = '"+user+"' && pass='"+pass+"'");
		ResultSet r = sta.executeQuery();
		System.out.println(sta);
		
		while (r.next()){
		g = r.getString("user");
		}
		if(g=="") {
	    
	    String rec="[{\"login\":\"Wrong username or password\"}]";
	    return rec;
	    
		}
		else {
	        
		String rec="[{\"login\":\"Welcome "+g+"\"}]";	   	    
		return rec;
		}
	}
	
//registrera ny användare
public static String Register(String user, String pass) throws SQLException {
		System.out.println(user);
		String g = "";
		PreparedStatement sta = con.prepareStatement("select user from login where user = '"+user+"'");
		ResultSet r = sta.executeQuery();
		while (r.next()){
		g = r.getString("user");
		}
		System.out.println(g);
		if(g=="") {
		PreparedStatement stat = con.prepareStatement("INSERT INTO login (user, pass) VALUES ('"+user+"', '"+pass+"')");
	    int temp = stat.executeUpdate();
	    
	    String rec="[{\"login\":\"Success\"}]";
	    return rec;
	    
		}
		else {
	        
		String rec="[{\"login\":\"Username taken\"}]";	   	    
		return rec;
		}
	}
	//se sparade recept
	public static String saveRecipe(String user) throws SQLException {

	    PreparedStatement statm = con.prepareStatement("SELECT * FROM recipes where user='"+user+"'");
        ResultSet rs = statm.executeQuery();
        String titel;
        int id2;
        String rec="[";
        
   	    while (rs.next()){
   	        titel = rs.getString("name");
   	        id2 = rs.getInt("id");

   	     rec=rec+"{\"titel\":\""+titel+"\",\"" + 
   	        		"id\":\""+id2+"\"},";	      
   	    	}
   	    if(rec!="[") {
   	    rec=rec.substring(0, rec.length() - 1);
   	    }
   	    rec=rec+"]";
   	    
	return rec;
	} 
	
	//ta bort sparat recept
	public static String deleteRecipe(int id, String name, String user) throws SQLException {
		name=name.replaceAll("_", " ");
		name=name.replaceAll("@", "&");
		PreparedStatement stat = con.prepareStatement("DELETE FROM recipes WHERE id='"+id+"' && name='"+name+"' && user='"+user+"'");
	    int temp = stat.executeUpdate();
	   
	    PreparedStatement statm = con.prepareStatement("SELECT * FROM recipes where user = '"+user+"'");
        ResultSet rs = statm.executeQuery();
        String titel;
        int id2;
        String rec="[";
        
   	    while (rs.next()){
   	        titel = rs.getString("name");
   	        id2 = rs.getInt("id");

   	     rec=rec+"{\"titel\":\""+titel+"\",\"" + 
   	        		"id\":\""+id2+"\"},";	      
   	    	}
   	    rec=rec.substring(0, rec.length() - 1);
   	    rec=rec+"]";
   	    
	return rec;
	} 
	
	//spara recept
	public static String addRecipe(int id, String name, String user) throws SQLException {
		System.out.println(user);
		name=name.replaceAll("_", " ");
		name=name.replaceAll("@", "&");
		String g = "";
		PreparedStatement sta = con.prepareStatement("select name from recipes where exists (select '"+id+"' from recipes where name = '"+name+"' && user='"+user+"')");
		ResultSet r = sta.executeQuery();
		while (r.next()){
		g = r.getString("name");
		}
		System.out.println(g);
		if(g=="") {
		PreparedStatement stat = con.prepareStatement("INSERT INTO recipes (id, name, user) VALUES ('"+id+"', '"+name+"', '"+user+"')");
	    int temp = stat.executeUpdate();
		}
	        PreparedStatement statm = con.prepareStatement("SELECT * FROM recipes where user='"+user+"'");
	        ResultSet rs = statm.executeQuery();
	        String titel;
	        int id2;
	        String rec="[";
	        
	   	    while (rs.next()){
	   	        titel = rs.getString("name");
	   	        id2 = rs.getInt("id");

	   	     rec=rec+"{\"titel\":\""+titel+"\",\"" + 
	   	        		"id\":\""+id2+"\"},";	      
	   	    	}
	   	    rec=rec.substring(0, rec.length() - 1);
	   	    rec=rec+"]";
	   	    
		return rec;
	}
	
	
	//sökning på recept genom inmatade ingredienser
	
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
	        	   res[i] = " (" + a[i].length() + "/" + count + " ingredients used, " + array[i].length() + " missing)";
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
	
	
}