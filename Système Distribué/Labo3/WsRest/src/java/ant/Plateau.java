/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ant;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "plateau")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plateau.findAll", query = "SELECT p FROM Plateau p"),
    @NamedQuery(name = "Plateau.findByNom", query = "SELECT p FROM Plateau p WHERE p.nom = :nom")})
public class Plateau implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NOM")
    private String nom;
    @ManyToMany(mappedBy = "plateauCollection")
    private Collection<Piece> pieceCollection;
    @JoinColumn(name = "JOUEUR2_ID", referencedColumnName = "ID")
    @ManyToOne
    private Joueur joueur2Id;
    @JoinColumn(name = "JOUEUR1_ID", referencedColumnName = "ID")
    @ManyToOne
    private Joueur joueur1Id;
    @OneToMany(mappedBy = "plateauNom")
    private Collection<Joueur> joueurCollection;
    @OneToMany(mappedBy = "plateauNom")
    private Collection<Piece> pieceCollection1;

    public Plateau() {
    }

    public Plateau(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @XmlTransient
    public Collection<Piece> getPieceCollection() {
        return pieceCollection;
    }

    public void setPieceCollection(Collection<Piece> pieceCollection) {
        this.pieceCollection = pieceCollection;
    }

    public Joueur getJoueur2Id() {
        return joueur2Id;
    }

    public void setJoueur2Id(Joueur joueur2Id) {
        this.joueur2Id = joueur2Id;
    }

    public Joueur getJoueur1Id() {
        return joueur1Id;
    }

    public void setJoueur1Id(Joueur joueur1Id) {
        this.joueur1Id = joueur1Id;
    }

    @XmlTransient
    public Collection<Joueur> getJoueurCollection() {
        return joueurCollection;
    }

    public void setJoueurCollection(Collection<Joueur> joueurCollection) {
        this.joueurCollection = joueurCollection;
    }

    @XmlTransient
    public Collection<Piece> getPieceCollection1() {
        return pieceCollection1;
    }

    public void setPieceCollection1(Collection<Piece> pieceCollection1) {
        this.pieceCollection1 = pieceCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nom != null ? nom.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plateau)) {
            return false;
        }
        Plateau other = (Plateau) object;
        if ((this.nom == null && other.nom != null) || (this.nom != null && !this.nom.equals(other.nom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ant.Plateau[ nom=" + nom + " ]";
    }

}
