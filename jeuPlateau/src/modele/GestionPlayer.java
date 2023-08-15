package modele;

import java.util.ArrayList;
import java.util.List;

import exception.DeuxJoueurMemeNom;
import exception.JoueurNull;

public class GestionPlayer {
	
	private List<Player>players;

	public GestionPlayer() {
		super();
		// TODO Auto-generated constructor stub
		players = new ArrayList<>();
	}
	
	public boolean add(Player p) throws DeuxJoueurMemeNom, JoueurNull {
		
		for(Player player  : getPlayers()) {
			if(player.getName().equals(p.getName())) {
				throw new DeuxJoueurMemeNom();		
			}
		}
		if(p==null) {
			throw new JoueurNull();
		}
		if(!players.contains(p)) {
			return players.add(p);
		}
		return false;
	}
	
	public boolean remove(Player p) {
		return players.remove(p);
	}
	
	public List<Player> getPlayers(){
		List<Player> players2 = new ArrayList<>();
		
		for(Player p : players) {
			players2.add(p.clone());
		}
		return players;
	}
	
	public Player getPlayerOfIndex(int i) {
		return players.get(i);
	}
}
