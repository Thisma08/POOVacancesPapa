package modele;

import java.util.List;

public class Etudiant {
	private String nom;
	private int age;
	private String section;
	List<String> listeCours;
	
	public Etudiant(String nom, int age, String section, List<String> listeCours) {
		this.nom = nom;
		this.age = age;
		this.section = section;
		this.listeCours = listeCours;
	}
	
	public int compterCours() {
		return listeCours.size();
	}
	
	@Override
	public String toString() {
		return "Etudiant [nom=" + nom + ", age=" + age + ", section=" + section + ", cours=" + listeCours + "]";
	}

	public String getNom() {
		return nom;
	}

	public int getAge() {
		return age;
	}

	public String getSection() {
		return section;
	}

	public List<String> getCours() {
		return listeCours;
	}
}

