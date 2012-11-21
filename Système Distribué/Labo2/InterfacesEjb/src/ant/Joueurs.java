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
@Table(name = "joueurs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Joueurs.findAll", query = "SELECT j FROM Joueurs j"),
    @NamedQuery(name = "Joueurs.findByIdJoueur", query = "SELECT j FROM Joueurs j WHERE j.idJoueur = :idJoueur"),
    @NamedQuery(name = "Joueurs.findByCouleur", query = "SELECT j FROM Joueurs j WHERE j.couleur = :couleur"),
    @NamedQuery(name = "Joueurs.findByCouleurNomPlateau", query = "SELECT j FROM Joueurs j WHERE j.couleur = :couleur AND j.nomPlateau = :nomPlateau"),
    @NamedQuery(name = "Joueurs.findByNomPlateau", query = "SELECT j FROM Joueurs j WHERE j.nomPlateau = :nomPlateau")})
public class Joueurs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idJoueur")
    private Integer idJoueur;
    @Basic(optional = false)
    @NotNull
    @Column(name = "couleur")
    private int couleur;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nomPlateau")
    private String nomPlateau;

    public Joueurs() {
    }

    public Joueurs(Integer idJoueur) {
        this.idJoueur = idJoueur;
    }

    public Joueurs(Integer idJoueur, int couleur, String nomPlateau) {
        this.idJoueur = idJoueur;
        this.couleur = couleur;
        this.nomPlateau = nomPlateau;
    }

    public Integer getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(Integer idJoueur) {
        this.idJoueur = idJoueur;
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    public String getNomPlateau() {
        return nomPlateau;
    }

    public void setNomPlateau(String nomPlateau) {
        this.nomPlateau = nomPlateau;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idJoueur != null ? idJoueur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Joueurs)) {
            return false;
        }
        Joueurs other = (Joueurs) object;
        if ((this.idJoueur == null && other.idJoueur != null) || (this.idJoueur != null && !this.idJoueur.equals(other.idJoueur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ant.Joueurs[ idJoueur=" + idJoueur + " ]";
    }

}
