package personnel;

public class exceptionDatedebut extends Exception {
	public exceptionDatedebut()
	{
		System.out.println("Erreur ! La date d'embauche de l'employé est après la date de départ de l'employé !");
	}

}
