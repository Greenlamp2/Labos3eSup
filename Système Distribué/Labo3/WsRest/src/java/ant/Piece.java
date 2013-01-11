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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "piece")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Piece.findAll", query = "SELECT p FROM Piece p"),
    @NamedQuery(name = "Piece.findById", query = "SELECT p FROM Piece p WHERE p.id = :id"),
    @NamedQuery(name = "Piece.findByDtype", query = "SELECT p FROM Piece p WHERE p.dtype = :dtype"),
    @NamedQuery(name = "Piece.findByX", query = "SELECT p FROM Piece p WHERE p.x = :x"),
    @NamedQuery(name = "Piece.findByY", query = "SELECT p FROM Piece p WHERE p.y = :y")})
public class Piece implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 31)
    @Column(name = "DTYPE")
    private String dtype;
    @Lob
    @Column(name = "COLOR")
    private byte[] color;
    @Column(name = "X")
    private Integer x;
    @Column(name = "Y")
    private Integer y;
    @JoinTable(name = "plateau_piece", joinColumns = {
        @JoinColumn(name = "listePiece_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "Plateau_NOM", referencedColumnName = "NOM")})
    @ManyToMany
    private Collection<Plateau> plateauCollection;
    @JoinColumn(name = "PLATEAU_NOM", referencedColumnName = "NOM")
    @ManyToOne
    private Plateau plateauNom;

    public Piece() {
    }

    public Piece(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public byte[] getColor() {
        return color;
    }

    public void setColor(byte[] color) {
        this.color = color;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @XmlTransient
    public Collection<Plateau> getPlateauCollection() {
        return plateauCollection;
    }

    public void setPlateauCollection(Collection<Plateau> plateauCollection) {
        this.plateauCollection = plateauCollection;
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
        if (!(object instanceof Piece)) {
            return false;
        }
        Piece other = (Piece) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ant.Piece[ id=" + id + " ]";
    }

}
