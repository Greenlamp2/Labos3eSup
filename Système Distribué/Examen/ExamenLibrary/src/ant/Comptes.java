/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ant;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "comptes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comptes.findAll", query = "SELECT c FROM Comptes c"),
    @NamedQuery(name = "Comptes.findByIdCompte", query = "SELECT c FROM Comptes c WHERE c.idCompte = :idCompte"),
    @NamedQuery(name = "Comptes.findBySomme", query = "SELECT c FROM Comptes c WHERE c.somme = :somme"),
    @NamedQuery(name = "Comptes.findByIdClient", query = "SELECT c FROM Comptes c WHERE c.idClient = :idClient")})
public class Comptes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCompte")
    private Integer idCompte;
    @Column(name = "somme")
    private Integer somme;
    @Column(name = "idClient")
    private Integer idClient;

    public Comptes() {
    }

    public Comptes(Integer idCompte) {
        this.idCompte = idCompte;
    }

    public Integer getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(Integer idCompte) {
        this.idCompte = idCompte;
    }

    public Integer getSomme() {
        return somme;
    }

    public void setSomme(Integer somme) {
        this.somme = somme;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompte != null ? idCompte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comptes)) {
            return false;
        }
        Comptes other = (Comptes) object;
        if ((this.idCompte == null && other.idCompte != null) || (this.idCompte != null && !this.idCompte.equals(other.idCompte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ant.Comptes[ idCompte=" + idCompte + " ]";
    }

}
