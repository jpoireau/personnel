package personnel;

import java.time.LocalDate;

public class ErreurDateDepart extends Exception{
	public ErreurDateDepart(LocalDate dateDebut, LocalDate dateFin)
	{
		System.out.println("La Date d'arriv�e : "+ dateDebut+" est inf�rieur � la date de d�part : " +dateFin);
	}
}

