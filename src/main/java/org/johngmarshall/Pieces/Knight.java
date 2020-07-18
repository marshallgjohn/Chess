package org.johngmarshall.Pieces;

import org.johngmarshall.BoardSquare;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Knight extends Piece{

    public Knight(String name, BoardSquare square, boolean isFriendly) throws IOException {
        super(name,square, "art/"+name+"-knight.png",7,isFriendly,new Direction[]{Direction.NONE});
    }


    public boolean isValidMove(BoardSquare boardSquare, BoardSquare[][] board) {
        if(boardSquare.isOccupied()) {
            if (Math.abs(getSquare().getX() - boardSquare.getX()) == 1 && Math.abs(getSquare().getY() - boardSquare.getY()) == 2 && !boardSquare.getPiece().isFriendly() ||
                    Math.abs(getSquare().getX() - boardSquare.getX()) == 2 && Math.abs(getSquare().getY() - boardSquare.getY()) == 1 && !boardSquare.getPiece().isFriendly()) {
                return true;
            } else {
                return false;
            }
        } else {
            if (Math.abs(getSquare().getX() - boardSquare.getX()) == 1 && Math.abs(getSquare().getY() - boardSquare.getY()) == 2 ||
                    Math.abs(getSquare().getX() - boardSquare.getX()) == 2 && Math.abs(getSquare().getY() - boardSquare.getY()) == 1) {
                return true;
            } else {
                return false;
            }
        }
    }
}
