/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Others;

import ant.Piece;


public class EchiquierE {
    Piece piece;

    public EchiquierE(){
        setPiece(null);
    }

    public EchiquierE(Piece piece){
        setPiece(piece);
    }
    public void setPiece(Piece piece){
        this.piece = piece;
    }
    public Piece getPiece(){
        return this.piece;
    }
}
