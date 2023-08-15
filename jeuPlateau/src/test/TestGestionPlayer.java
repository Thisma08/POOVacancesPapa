package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.DeuxJoueurMemeNom;
import exception.JoueurNull;
import modele.GestionPlayer;
import modele.Player;

class TestGestionPlayer {
	
	private GestionPlayer gestion;
	private List<Player> listPlayers;
	private Player p1;
	private Player p2;
	private Player p3;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		gestion = new GestionPlayer(); 
		Field fPlayers = GestionPlayer.class.getDeclaredField("players");
		fPlayers.setAccessible(true);
		//Pas compris cette ligne
		listPlayers=(List<Player>)fPlayers.get(gestion);
		p1 = new Player("Louis");
		p2 = p1;
		p3 = null;
		
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testAdd() {
		
		Class<?> cGestion = gestion.getClass();
		Method methode = null;
		
		try {
			//la taille de la liste est sensée être égale à 0 car pas encore d'ajout
			assertEquals(0, listPlayers.size());
			
			//On récupère la méthode "add" via introspection
			methode =  cGestion.getDeclaredMethod("add", Player.class);
			
			//On ajoute le joueur p1 (la méthode add renvoie True si l'ajout se fait correctement)
			assertTrue((boolean)methode.invoke(gestion, p1));
			
			//Tentative ajout du joueur p1 une deuxième fois
			assertFalse((boolean)methode.invoke(gestion, p1));
			
			//La taille est sensée être égale à 1 puisque deuxième ajout = Fail
			assertEquals(1, listPlayers.size());
			
			//Tentative d'ajout du deuxième joueur au contenu identique
			assertThrows(DeuxJoueurMemeNom.class,()->listPlayers.add(p2));
			//assertFalse((boolean)methode.invoke(gestion, p2));
			
			//La taille est sensée être égale à 1 puisque troisième ajout = fail
			assertEquals(1, listPlayers.size());
			
			//Tentative d'ajout d'un joueur ==  null
			assertThrows(JoueurNull.class,()->listPlayers.add(p3));
			assertFalse((boolean)methode.invoke(gestion, p3));
			
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test 
	void testRemove() {
		
		Class<?> cGestion = gestion.getClass();
		Method methode = null;
		
		try {
			methode =  cGestion.getDeclaredMethod("remove", Player.class);
			
			//on vérifie que la liste est vide pour commencer
			assertEquals(0, listPlayers.size() );
			
			//Tentative de remove d'un joueur non présent dans la liste
			assertFalse((boolean) methode.invoke(gestion, p1));
			
			//Ajout d'un joueur pour tenter de le remove
				listPlayers.add(p1); //Collection.add != Gestion.add
				assertEquals(1, listPlayers.size()); // verifie que l'ajout se soit bien passé
				assertTrue((boolean) methode.invoke(gestion, p1)); //Tentative de remove
				assertEquals(0, listPlayers.size()); // On revérifie que la liste soit de vide
			
				
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void testMettreAjour() {		
	}
}
