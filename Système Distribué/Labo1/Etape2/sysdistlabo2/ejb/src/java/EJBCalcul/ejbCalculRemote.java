package EJBCalcul;
import javax.ejb.Remote;


@Remote
public interface ejbCalculRemote
{
	public void addNombre(int nbre);
	public int getSomme();
	public float getMoyenne();
	public int getMinimum();
	public int getMaximum();
}