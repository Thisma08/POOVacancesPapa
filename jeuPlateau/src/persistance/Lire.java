package persistance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Lire {
	
	//Permet de lire un fichier format JSON afin de récuperer l'objet de type deck
	
		public static String lire() {
			   
			File myFile = new File("src/joueur.json");
	        
	        FileInputStream fIn;

	        try {
	        	fIn = new FileInputStream(myFile);

	            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
	            String aDataRow = "";
	            String aBuffer = "";
				while ((aDataRow = myReader.readLine()) != null) 
				{
					aBuffer += aDataRow ;
				}
				String json = aBuffer.toString();
				myReader.close();
				return json;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "";
		}

}
