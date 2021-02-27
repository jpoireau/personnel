package personnel;

public class exceptionDateFin extends Exception {
	public exceptionDateFin()
	{
		System.out.println("Erreur ! La date de départ est avant la date d'arrivée de l'employé !");
	}

}
