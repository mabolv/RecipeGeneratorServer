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

public class RecipeGenerate { 
	//generera måltider
    public static String execute(String timeframe, String targetcal, String diet, String exclude) throws IOException, JSONException { 
    	
	 System.setProperty("http.agent", "Firefox");
	 String rec = "";

     if(targetcal!=null) {
     targetcal="&targetcal="+targetcal;}
     else {targetcal="";}
     if(diet!=null) {
	  diet="&diet="+diet;}
     else {diet="";}
     if(exclude!=null) {
	  exclude="&exclude="+exclude;}
     else {exclude="";}
	 
	 
		int id[] = new int[21];
		String title[] = new String[21];
		int rim[] = new int[21];
		String src[] = new String[21];
		int calories[] = new int[7];
		int protein[] = new int[7];
		int fat[] = new int[7];
		int carbohydrates[] = new int[7];
	 
     URL url = new URL("https://api.spoonacular.com/mealplanner/generate?apiKey=e672224c04114dcfbec9e42bad48afe3&timeFrame="+timeframe+targetcal+diet+exclude);
     HttpURLConnection con = (HttpURLConnection) url.openConnection();
     con.setDoOutput(true);
     con.setRequestMethod("GET");
     con.setRequestProperty("Content-Type", "application/json");
     BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
     String output = br.readLine();
     output = "{results:["+output+"]}";
     
     JSONObject jsonobject = new JSONObject();

    	 jsonobject = new JSONObject(output);

         
         JSONArray jsonarray = new JSONArray();
         
        	 jsonarray = jsonobject.getJSONArray("results");
        	 JSONObject json = new JSONObject();
        	 json=jsonarray.getJSONObject(0);
        	 JSONObject jsono = new JSONObject();
             jsono = json;
             
             try {
            	 JSONArray jsa = new JSONArray();
                 jsa=json.getJSONArray("meals");
                 
                 for(int i=0; i<3; i++) {
                	 title[i]="";

                	 JSONObject jo = new JSONObject();
                	 jo=jsa.getJSONObject(i);
                	 
                  	 id[i] = jo.getInt("id");
                  	 title[i] = jo.getString("title");
                  	 rim[i] = jo.getInt("readyInMinutes");
                  	 src[i] = jo.getString("imageType");
                 }
                 
                 JSONObject jso = new JSONObject();
                 jso=jsono.getJSONObject("nutrients");
                 
                 calories[0]=jso.getInt("calories");
                 protein[0]=jso.getInt("protein");
                 fat[0]=jso.getInt("fat");
                 carbohydrates[0]=jso.getInt("carbohydrates");
             
             }catch(Exception e) {}
             
             
             try {
             JSONObject j = new JSONObject();
             j=jsono.getJSONObject("week");
             
             JSONObject js = new JSONObject();
             js=j.getJSONObject("monday");
             
             JSONArray jsa = new JSONArray();
             jsa=js.getJSONArray("meals");
             
             for(int i=0; i<3; i++) {
            	 title[i]="";

            	 JSONObject jo = new JSONObject();
            	 jo=jsa.getJSONObject(i);
            	 
              	 id[i] = jo.getInt("id");
              	 title[i] = jo.getString("title");
              	 rim[i] = jo.getInt("readyInMinutes");
              	 src[i] = jo.getString("imageType");
             }
             
             JSONObject jso = new JSONObject();
             jso=js.getJSONObject("nutrients");
             calories[0]=jso.getInt("calories");
             protein[0]=jso.getInt("protein");
             fat[0]=jso.getInt("fat");
             carbohydrates[0]=jso.getInt("carbohydrates");
         
        	 try {
             JSONObject js1 = new JSONObject();
             js1=j.getJSONObject("tuesday");
             
             JSONArray jsa1 = new JSONArray();
             jsa1=js1.getJSONArray("meals");
             
             for(int i=3; i<6; i++) {
            	 title[i]="";

            	 JSONObject jo = new JSONObject();
            	 jo=jsa1.getJSONObject(i-3);
            	 
              	 id[i] = jo.getInt("id");
              	 title[i] = jo.getString("title");
              	 rim[i] = jo.getInt("readyInMinutes");
              	src[i] = jo.getString("imageType");
             }
             
             JSONObject jso1 = new JSONObject();
             jso1=js1.getJSONObject("nutrients");
             calories[1]=jso1.getInt("calories");
             protein[1]=jso1.getInt("protein");
             fat[1]=jso1.getInt("fat");
             carbohydrates[1]=jso1.getInt("carbohydrates");
         
             
             JSONObject js2 = new JSONObject();
             js2=j.getJSONObject("wednesday");
             
             JSONArray jsa2 = new JSONArray();
             jsa2=js2.getJSONArray("meals");
             
             
             for(int i=6; i<9; i++) {
            	 title[i]="";

            	 JSONObject jo = new JSONObject();
            	 jo=jsa2.getJSONObject(i-6);
            	 
              	 id[i] = jo.getInt("id");
              	 title[i] = jo.getString("title");
              	 rim[i] = jo.getInt("readyInMinutes");
              	src[i] = jo.getString("imageType");
             }
             
             JSONObject jso2 = new JSONObject();
             jso2=js2.getJSONObject("nutrients");
             calories[2]=jso2.getInt("calories");
             protein[2]=jso2.getInt("protein");
             fat[2]=jso2.getInt("fat");
             carbohydrates[2]=jso2.getInt("carbohydrates");
             
             JSONObject js3 = new JSONObject();
             js3=j.getJSONObject("thursday");
             
             JSONArray jsa3 = new JSONArray();
             jsa3=js3.getJSONArray("meals");
             
             
             for(int i=9; i<12; i++) {
            	 title[i]="";

            	 JSONObject jo = new JSONObject();
            	 jo=jsa3.getJSONObject(i-9);
            	 
              	 id[i] = jo.getInt("id");
              	 title[i] = jo.getString("title");
              	 rim[i] = jo.getInt("readyInMinutes");
              	src[i] = jo.getString("imageType");
             }
             
             JSONObject jso3 = new JSONObject();
             jso3=js3.getJSONObject("nutrients");
             calories[3]=jso3.getInt("calories");
             protein[3]=jso3.getInt("protein");
             fat[3]=jso3.getInt("fat");
             carbohydrates[3]=jso3.getInt("carbohydrates");
             
             JSONObject js4 = new JSONObject();
             js4=j.getJSONObject("friday");
             
             JSONArray jsa4 = new JSONArray();
             jsa4=js4.getJSONArray("meals");
             
             
             for(int i=12; i<15; i++) {
            	 title[i]="";

            	 JSONObject jo = new JSONObject();
            	 jo=jsa4.getJSONObject(i-12);
            	 
              	 id[i] = jo.getInt("id");
              	 title[i] = jo.getString("title");
              	 rim[i] = jo.getInt("readyInMinutes");
              	src[i] = jo.getString("imageType");
             }
             
             JSONObject jso4 = new JSONObject();
             jso4=js4.getJSONObject("nutrients");
             calories[4]=jso4.getInt("calories");
             protein[4]=jso4.getInt("protein");
             fat[4]=jso4.getInt("fat");
             carbohydrates[4]=jso4.getInt("carbohydrates");
             
             JSONObject js5 = new JSONObject();
             js5=j.getJSONObject("saturday");
             
             JSONArray jsa5 = new JSONArray();
             jsa5=js5.getJSONArray("meals");
             
             
             for(int i=15; i<18; i++) {
            	 title[i]="";

            	 JSONObject jo = new JSONObject();
            	 jo=jsa5.getJSONObject(i-15);
            	 
              	 id[i] = jo.getInt("id");
              	 title[i] = jo.getString("title");
              	 rim[i] = jo.getInt("readyInMinutes");
              	src[i] = jo.getString("imageType");
             }
             
             JSONObject jso5 = new JSONObject();
             jso5=js5.getJSONObject("nutrients");
             calories[5]=jso5.getInt("calories");
             protein[5]=jso5.getInt("protein");
             fat[5]=jso5.getInt("fat");
             carbohydrates[5]=jso5.getInt("carbohydrates");
             
             JSONObject js6 = new JSONObject();
             js6=j.getJSONObject("sunday");
             
             JSONArray jsa6 = new JSONArray();
             jsa6=js6.getJSONArray("meals");
             
             
             for(int i=18; i<21; i++) {
            	 title[i]="";

            	 JSONObject jo = new JSONObject();
            	 jo=jsa6.getJSONObject(i-18);
            	 
              	 id[i] = jo.getInt("id");
              	 title[i] = jo.getString("title");
              	 rim[i] = jo.getInt("readyInMinutes");
              	src[i] = jo.getString("imageType");
             }
             
             JSONObject jso6 = new JSONObject();
             jso6=js6.getJSONObject("nutrients");
             calories[6]=jso6.getInt("calories");
             protein[6]=jso6.getInt("protein");
             fat[6]=jso6.getInt("fat");
             carbohydrates[6]=jso6.getInt("carbohydrates");
        	 }catch(Exception e) {}
        	 }catch(Exception e) {
        		 rec = "[{\"title\":\"Could not create weekly meals with these values\"}]";
        		  
        	 }
             System.out.println("g"+title[0]);
             if(title[0].equals("")) {
            	 System.out.println("test");
            	 return rec;
             }
		    	rec = "[";
		    	int k = 0;
		    	   for(int i=0; i<21; i++){

		   	        rec=rec+"{\"id\":\""+id[i]+"\",\"" + 
		   	        		"title\":\""+title[i]+"\",\""  +
		   	        		"img\":\""+src[i]+"\",\""  +
		   	        		"rim\":\""+rim[i]+"\"}";
		   	       
		   	        if(i==2||i==5||i==8||i==11||i==14||i==17||i==20) {
		   	        
		   	        rec=rec+",{\"calories\":\""+calories[k]+"\",\"" +
		   			   	    "protein\":\""+protein[k]+"\",\"" +
		   			   	    "fat\":\""+fat[k]+"\",\"" +
		   			   	    "carbs\":\""+carbohydrates[k]+"\"}";  
		   	        
		   	        		k++;
		   	        		}
		   	        
		   	        		if(i!=20) {
		   	        			rec=rec+",";
		   	        		}
		   	    	}
		    	   
		    	   rec = rec + "]";
		    	   return rec;
		    	   

              
				
	
		}	 
}
