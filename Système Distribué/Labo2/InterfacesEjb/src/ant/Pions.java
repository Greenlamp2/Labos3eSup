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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "pions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pions.findAll", query = "SELECT p FROM Pions p"),
    @NamedQuery(name = "Pions.findByIdPion", query = "SELECT p FROM Pions p WHERE p.idPion = :idPion"),
    @NamedQuery(name = "Pions.findByNomPion", query = "SELECT p FROM Pions p WHERE p.nomPion = :nomPion"),
    @NamedQuery(name = "Pions.findByPositionX", query = "SELECT p FROM Pions p WHERE p.positionX = :positionX"),
    @NamedQuery(name = "Pions.findByPositionY", query = "SELECT p FROM Pions p WHERE p.positionY = :positionY"),
    @NamedQuery(name = "Pions.findByPositionXY", query = "SELECT p FROM Pions p WHERE p.positionX = :positionX AND p.positionY = :positionY"),
    @NamedQuery(name = "Pions.findByNomPlateau", query = "SELECT p FROM Pions p WHERE p.nomPlateau = :nomPlateau"),
    @NamedQuery(name = "Pions.findByNomCouleurPlateauXY", query = "SELECT p FROM Pions p WHERE p.nomPion = :nomPion AND p.couleur = :couleur AND p.nomPlateau = :nomPlateau AND p.positionX = :positionX AND p.positionY = :positionY"),
    @NamedQuery(name = "Pions.findByCouleur", query = "SELECT p FROM Pions p WHERE p.couleur = :couleur")})
public class Pions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPion")
    private Integer idPion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nomPion")
    private String nomPion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "positionX")
    private int positionX;
    @Basic(optional = false)
    @NotNull
    @Column(name = "positionY")
    private int positionY;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nomPlateau")
    private String nomPlateau;
    @Basic(optional = false)
    @NotNull
    @Column(name = "couleur")
    private int couleur;

    public Pions() {
    }

    public Pions(Integer idPion) {
        this.idPion = idPion;
    }

    public Pions(Integer idPion, String nomPion, int positionX, int positionY, String nomPlateau, int couleur) {
        this.idPion = idPion;
        this.nomPion = nomPion;
        this.positionX = positionX;
        this.positionY = positionY;
        this.nomPlateau = nomPlateau;
        this.couleur = couleur;
    }

    public Integer getIdPion() {
        return idPion;
    }

    public void setIdPion(Integer idPion) {
        this.idPion = idPion;
    }

    public String getNomPion() {
        return nomPion;
    }

    public void setNomPion(String nomPion) {
        this.nomPion = nomPion;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getNomPlateau() {
        return nomPlateau;
    }

    public void setNomPlateau(String nomPlateau) {
        this.nomPlateau = nomPlateau;
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPion != null ? idPion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pions)) {
            return false;
        }
        Pions other = (Pions) object;
        if ((this.idPion == null && other.idPion != null) || (this.idPion != null && !this.idPion.equals(other.idPion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ant.Pions[ idPion=" + idPion + " ]";
    }

}
