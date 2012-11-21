package EJBCalcul;
import javax.ejb.Stateful;
import java.util.Vector;
import java.util.Iterator;

@Stateful
public class ejbCalculBean implements ejbCalculRemote
{
	Vector<Integer> vec = new Vector();
	public ejbCalculBean(){
	}
	public void addNombre(int nbre){
		vec.add(nbre);
	}
	public int getSomme(){
		int somme = 0;
		if(vec.size() == 0) return 0;
		
		for(int value: vec){
			somme += value;
		}
		
		return somme;
	}
	public float getMoyenne(){
		float moyenne = 0;
		if(vec.size() == 0) return 0;
		int taille = vec.size();
		
		for(int value: vec){
			moyenne += value;
		}
		moyenne /= taille;
		return moyenne;
	}
	public int getMinimum(){
		int min = 0;
		if(vec.size() == 0) return 0;
		min = vec.get(0);
		for(int value: vec){
			if(value<min) min = value;
		}
		return min;
	}
	public int getMaximum(){
		int max = 0;
		if(vec.size() == 0) return 0;
		max = vec.get(0);
		for(int value: vec){
			if(value>max) max = value;
		}
		return max;
	}
}