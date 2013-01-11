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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "joueur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Joueur.findAll", query = "SELECT j FROM Joueur j"),
    @NamedQuery(name = "Joueur.findById", query = "SELECT j FROM Joueur j WHERE j.id = :id")})
public class Joueur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Lob
    @Column(name = "COLOR")
    private byte[] color;
    @OneToMany(mappedBy = "joueur2Id")
    private Collection<Plateau> plateauCollection;
    @OneToMany(mappedBy = "joueur1Id")
    private Collection<Plateau> plateauCollection1;
    @JoinColumn(name = "PLATEAU_NOM", referencedColumnName = "NOM")
    @ManyToOne
    private Plateau plateauNom;

    public Joueur() {
    }

    public Joueur(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getColor() {
        return color;
    }

    public void setColor(byte[] color) {
        this.color = color;
    }

    @XmlTransient
    public Collection<Plateau> getPlateauCollection() {
        return plateauCollection;
    }

    public void setPlateauCollection(Collection<Plateau> plateauCollection) {
        this.plateauCollection = plateauCollection;
    }

    @XmlTransient
    public Collection<Plateau> getPlateauCollection1() {
        return plateauCollection1;
    }

    public void setPlateauCollection1(Collection<Plateau> plateauCollection1) {
        this.plateauCollection1 = plateauCollection1;
    }

    public Plateau getPlateauNom() {
        return plateauNom;
    }

    public void setPlateauNom(Plateau plateauNom) {
        this.plateauNom = plateauNom;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Joueur)) {
            return false;
        }
        Joueur other = (Joueur) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ant.Joueur[ id=" + id + " ]";
    }

}
