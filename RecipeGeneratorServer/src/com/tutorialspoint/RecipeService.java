package com.tutorialspoint;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List; 
import javax.ws.rs.GET; 
import javax.ws.rs.Path; 
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;  
@Path("/RecipeService") 

public class RecipeService {  
	
	@GET 
	@Path("/login") 
	@Produces(MediaType.APPLICATION_JSON) 
	public String Login(@QueryParam("user") String user, @QueryParam("pass") String pass) throws SQLException{ 
	   return RecipeDao.Login(user,pass); 
	} 
	
	@GET 
	@Path("/register") 
	@Produces(MediaType.APPLICATION_JSON) 
	public String Register(@QueryParam("user") String user, @QueryParam("pass") String pass) throws SQLException{ 
	   return RecipeDao.Register(user,pass); 
	} 
   
     
	RecipeDao recipesDao = new RecipeDao();  
	@GET 
	@Path("/recipes") 
	@Produces(MediaType.APPLICATION_JSON) 
	public String getRecipes(@QueryParam("content") String content, @QueryParam("option") int option) throws SQLException, IOException, JSONException{ 
		
	   return RecipeDao.execute(content, option);
	}  
	   
 
	RecipeInfo recipesinfoDao = new RecipeInfo();  
	@GET 
	@Path("/recipesinfo") 
	@Produces(MediaType.APPLICATION_JSON) 
	public String getRecipeInfo(@QueryParam("id") int id) throws SQLException, IOException, JSONException{ 
		return RecipeInfo.execute(id); 
	}  
    
	@GET 
	@Path("/savedrecipes") 
	@Produces(MediaType.APPLICATION_JSON) 
	public String saveRecipe(@QueryParam("user") String user) throws SQLException{ 
	   return RecipeDao.saveRecipe(user); 
	}  
	
	@GET 
	@Path("/deleterecipe") 
	@Produces(MediaType.APPLICATION_JSON) 
	public String deleteRecipe(@QueryParam("id") int id, @QueryParam("name") String name, @QueryParam("user") String user) throws SQLException{ 
	   return RecipeDao.deleteRecipe(id,name,user); 
	}  
	
   @GET 
   @Path("/addrecipe") 
   @Produces(MediaType.APPLICATION_JSON) 
   public String addRecipe(@QueryParam("id") int id, @QueryParam("name") String name, @QueryParam("user") String user) throws SQLException{ 
      return RecipeDao.addRecipe(id,name,user); 
   }  
   
   RecipeGenerate recgen = new RecipeGenerate();
   @GET 
   @Path("/generate") 
   @Produces(MediaType.APPLICATION_JSON)
   public String generate(@QueryParam("timeframe") String timeframe, @QueryParam("targetcal") String targetcal, @QueryParam("diet") String diet, @QueryParam("exclude") String exclude) throws IOException, JSONException{ 
      return RecipeGenerate.execute(timeframe, targetcal, diet, exclude); 
   } 
   
}