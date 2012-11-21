package EJBCalcul;
import javax.ejb.EJB;
import java.io.*;
public class Main
{
	@EJB
	private static EJBCalcul.ejbCalculRemote ejbCalculRemote;
	public Main(){
	}

	public static void main(String[] args)
	{
		String selection;
		int nbre;
		try{
			do{
				System.out.println("[1] Ajouter un nombre.");
				System.out.println("[2] Recuperer la somme.");
				System.out.println("[3] Recuperer la moyenne.");
				System.out.println("[4] Recuperer le minimum.");
				System.out.println("[5] Recuperer le maximum.");
				System.out.println("[0] Quitter.");
				System.out.println("Quel est votre choix: ");
				selection = puts();
				switch(Integer.parseInt(selection)){
					case 1:
						System.out.println("Quel est le nombre: ");
						nbre = Integer.parseInt(puts());
						ejbCalculRemote.addNombre(nbre);
					break;
					case 2:
						System.out.println("Somme:");
						System.out.println(ejbCalculRemote.getSomme());
					break;
					
					case 3:
						System.out.println("Moyenne:");
						System.out.println(ejbCalculRemote.getMoyenne());
					break;
					
					case 4:
						System.out.println("Minimum:");
						System.out.println(ejbCalculRemote.getMinimum());
					break;
					
					case 5:
						System.out.println("maximum:");
						System.out.println(ejbCalculRemote.getMaximum());
					break;
					
					default:
						System.out.println("Commande incorrecte.");
					break;
				}
				if(selection == "b") System.out.println(ejbCalculRemote.getSomme());
			}while(!selection.equals("0"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String puts(){
		String Value = "";
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		try{
		Value = console.readLine();
		}catch(Exception e){
			e.printStackTrace();
		}
		return Value;
	}
}