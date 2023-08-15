package modele;

import java.util.Objects;

public class Player {
	
	private String name;
	private int score;
	private boolean alive;
	
	private int id;
	private static int nb = 0;
	
	
	public Player(String name) {
		super();
		this.name = name;
		this.score = 0;
		this.alive = true;
		
		nb++;
		id = nb;
	}
	
	public boolean joueurGagnant() {
		if(score>=100) {
			return true;
		}
		return false;
	}
	
	public Player(String name2, int id2) {
		// TODO Auto-generated constructor stub
		name=name2;
		id=id2;
	}

	public Player clone() {
		return new Player(name, id);
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(alive, id, name, score);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return alive == other.alive && id == other.id && Objects.equals(name, other.name) && score == other.score;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score += score;
	}


	public boolean isAlive() {
		return alive;
	}


	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public int getId() {
		return id;
	}
}
