package com.tutorialspoint;

import java.io.Serializable;  
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement; 
@XmlRootElement(name = "recipe") 

public class Recipe implements Serializable {  
   private static final long serialVersionUID = 1L; 
   private String src;
   private String titel;
   private String res; 
   private String used;
   private String unused;
   private int id; 
   
   
   public Recipe(String src, String titel, String res, String used, String unused, int id){  
      this.src = src; 
      this.titel = titel; 
      this.res = res; 
      this.used = used;
      this.unused = unused;
      this.id = id;
   }

   public String getSrc() { 
      return src; 
   } 
   public void setSrc(String src) { 
      this.src = src; 
   } 
   public String getTitel() { 
      return titel; 
   } 
   @XmlElement 
   public void setTitel(String titel) { 
      this.titel = titel; 
   }   
   public String getRes() {
       return res;
   }
   public void setRes(String res) { 
	   this.res = res;  
   }
   public String getUsed() {
       return used;
   }
   public void setUsed(String used) { 
	   this.used = used;  
   } 
   public String getUnused() {
       return unused;
   }
   public void setUnused(String unused) { 
	   this.unused = unused;  
   }
   public int getId() { 
      return id; 
   }  
   public void setId(int id) { 
      this.id = id; 
   } 
} 