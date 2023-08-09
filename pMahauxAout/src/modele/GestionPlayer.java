package modele;

import java.util.ArrayList;
import java.util.List;

import exceptions.NullPlayerException;
import exceptions.PlayersSameNameException;

public class GestionPlayer {
	private List<Player> players;

	public GestionPlayer() 
	{
		super();
		// TODO Auto-generated constructor stub
		players = new ArrayList<>();
	}
	
	public boolean add(Player p) throws PlayersSameNameException, NullPlayerException 
	{	
		for(Player player : getPlayers()) {
			if(player.getName().equals(p.getName())) {
				throw new PlayersSameNameException();		
			}
		}
		if(p==null) {
			throw new NullPlayerException();
		}
		if(!players.contains(p)) {
			return players.add(p);
		}
		return false;
	}
	
	public boolean remove(Player p) 
	{
		return players.remove(p);
	}
	
	public List<Player> getPlayers()
	{
		List<Player> players2 = new ArrayList<>();
		
		for(Player p : players) 
		{
			players2.add(p.clone());
		}
		return players;
	}
	
	public Player getPlayerOfIndex(int i) 
	{
		return players.get(i);
	}
	
	@Override
	public String toString() 
	{
		return "GestionPlayer [players=" + players + "]";
	}
}
