package persistence;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import modele.GestionPlayer;

public class Write {	
	public static void write(GestionPlayer g, String fn)
	{
		Gson gson=new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		String json = gson.toJson(g);
		String concat = "src/"+fn;			
		File fichier = new File(concat);
		PrintWriter writer = null;
		try 
		{
			if(!fichier.exists())
			{
				fichier.createNewFile();				
			}
			writer = new PrintWriter(fichier);				 
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		writer.println(json);
		writer.flush();
		writer.close();	
	}
}