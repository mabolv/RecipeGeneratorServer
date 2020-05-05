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
	
    RecipeDao recipeDao = new RecipeDao();  
    @GET 
    @Path("/recipe") 
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Recipe> getRecipe() throws SQLException{ 
       return RecipeDao.getAllRecipes(); 
    }  
   
     
	RecipeDao recipesDao = new RecipeDao();  
	@GET 
	@Path("/recipes") 
	@Produces(MediaType.APPLICATION_JSON) 
	public String getRecipes(@QueryParam("content") String content, @QueryParam("option") int option) throws SQLException, IOException, JSONException{ 
		
	   return RecipeDao.execute(content, option);
	}  
	   
 
	RecipeDao recipesinfoDao = new RecipeDao();  
	@GET 
	@Path("/recipeinfo") 
	@Produces(MediaType.APPLICATION_JSON) 
	public List<Recipe> getRecipesInfo() throws SQLException{ 
		return RecipeDao.getAllRecipes(); 
	}  
      
   @GET 
   //@Path("/recipes") 
   public String addRecipe(@QueryParam("name") String name,@QueryParam("prof") String prof) throws SQLException{ 
      return RecipeDao.addRecipe(name,prof); 
   }  
   
   @GET 
   @Path("/recipeinfo") 
   public String deleteUser(@QueryParam("id") int id) throws SQLException{ 
      return RecipeDao.deleteRecipe(id); 
   } 
}