package org.johngmarshall.Pieces;

import org.johngmarshall.BoardSquare;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Pawn extends Piece{

    public Pawn(String name, BoardSquare square,boolean isFriendly) throws IOException {
        super(name,square, "art/"+name+"-pawn.png",2,isFriendly,new Direction[]{Direction.VERTICAL});
    }


    @Override
    public boolean isValidMove(BoardSquare square,BoardSquare[][] board) {
        System.out.println(this.getMoveLimit());
        if(
                this.getSquare().getY()-square.getY() <= this.getMoveLimit() && !square.isOccupied()  && getSquare().getX()==square.getX()||
                Math.abs(this.getSquare().getX()-square.getX()) == 1 && this.getSquare().getY()-square.getY() == 1 &&
                         square.isOccupied() && !square.getPiece().isFriendly()) {
            this.setMoveLimit(1);
            return true;
        } else {
            return false;
        }
    }
 /*   public boolean isValidMove(BoardSquare square, BoardSquare[][] board) {
        if(
                this.getSquare().getY()-square.getY() <= getMoveLimit() && !square.isOccupied()  && getSquare().getX()==square.getX()||
                        Math.abs(this.getSquare().getX()-square.getX()) == getMoveLimit() && this.getSquare().getY()-square.getY() == getMoveLimit() &&
                                square.isOccupied() && !square.getPiece().isFriendly()) {
            this.setMoveLimit(1);
            return true;
        } else {
            return false;
        }
    }*/
}
