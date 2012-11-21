/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ant;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "plateaux")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plateaux.findAll", query = "SELECT p FROM Plateaux p"),
    @NamedQuery(name = "Plateaux.findByNomPlateau", query = "SELECT p FROM Plateaux p WHERE p.nomPlateau = :nomPlateau"),
    @NamedQuery(name = "Plateaux.findByNombreDeJoueurs", query = "SELECT p FROM Plateaux p WHERE p.nombreDeJoueurs = :nombreDeJoueurs")})
public class Plateaux implements Serializable {
    private static final long serialVersionUID = 2L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nomPlateau")
    private String nomPlateau;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nombreDeJoueurs")
    private int nombreDeJoueurs;

    public Plateaux() {
    }

    public Plateaux(String nomPlateau) {
        this.nomPlateau = nomPlateau;
    }

    public Plateaux(String nomPlateau, int nombreDeJoueurs) {
        this.nomPlateau = nomPlateau;
        this.nombreDeJoueurs = nombreDeJoueurs;
    }

    public String getNomPlateau() {
        return nomPlateau;
    }

    public void setNomPlateau(String nomPlateau) {
        this.nomPlateau = nomPlateau;
    }

    public int getNombreDeJoueurs() {
        return nombreDeJoueurs;
    }

    public void setNombreDeJoueurs(int nombreDeJoueurs) {
        this.nombreDeJoueurs = nombreDeJoueurs;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomPlateau != null ? nomPlateau.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plateaux)) {
            return false;
        }
        Plateaux other = (Plateaux) object;
        if ((this.nomPlateau == null && other.nomPlateau != null) || (this.nomPlateau != null && !this.nomPlateau.equals(other.nomPlateau))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ant.Plateaux[ nomPlateau=" + nomPlateau + " ]";
    }

}
