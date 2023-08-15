package exception;

public class DeuxJoueurMemeNom extends Exception {
	
	public DeuxJoueurMemeNom() {
		// TODO Auto-generated constructor stub
		System.out.println("Vous essayez d'ajouter deux joueurs avec le même prénom, veuillez changer svp");
	}

}
