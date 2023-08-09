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
import exceptions.NullPlayerException;
import exceptions.PlayersSameNameException;
import modele.GestionPlayer;
import modele.Player;

class TestGestionPlayer {	
	private GestionPlayer gestion;
	private List<Player> listPlayers;
	private Player p1;
	private Player p2;
	private Player p3;

	@BeforeAll
	static void setUpBeforeClass() throws Exception 
	{
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception 
	{
	}

	@BeforeEach
	void setUp() throws Exception 
	{
		gestion = new GestionPlayer(); 
		Field fPlayers = GestionPlayer.class.getDeclaredField("players");
		fPlayers.setAccessible(true);
		listPlayers=(List<Player>)fPlayers.get(gestion);
		p1 = new Player("Louis");
		p2 = p1;
		p3 = null;	
	}

	@AfterEach
	void tearDown() throws Exception 
	{
	}
	
	@Test
	void testAdd() 
	{
		Class<?> cGestion = gestion.getClass();
		Method methode = null;		
		try 
		{
			assertEquals(0, listPlayers.size());
			methode =  cGestion.getDeclaredMethod("add", Player.class);
			assertTrue((boolean)methode.invoke(gestion, p1));
			assertFalse((boolean)methode.invoke(gestion, p1));
			assertEquals(1, listPlayers.size());
			assertThrows(PlayersSameNameException.class,()->listPlayers.add(p2));
			assertEquals(1, listPlayers.size());
			assertThrows(NullPlayerException.class,()->listPlayers.add(p3));
			assertFalse((boolean)methode.invoke(gestion, p3));			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test 
	void testRemove() 
	{		
		Class<?> cGestion = gestion.getClass();
		Method methode = null;	
		try 
		{
			methode =  cGestion.getDeclaredMethod("remove", Player.class);
			assertEquals(0, listPlayers.size() );
			assertFalse((boolean) methode.invoke(gestion, p1));		
			listPlayers.add(p1);
			assertEquals(1, listPlayers.size());
			assertTrue((boolean) methode.invoke(gestion, p1));
			assertEquals(0, listPlayers.size());			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
