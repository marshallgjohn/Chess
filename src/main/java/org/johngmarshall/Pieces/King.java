package org.johngmarshall.Pieces;

import org.johngmarshall.BoardSquare;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class King extends Piece {

    public King(String name, BoardSquare square, boolean isFriendly) throws IOException {
        super(name, square, "art/" + name + "-king.png", 1, isFriendly,new Direction[]{Direction.DIAGONAL,Direction.VERTICAL,Direction.HORIZONTAL});
    }


    @Override
    public boolean isValidMove(BoardSquare boardSquare, BoardSquare[][] board) {
        if (boardSquare.isOccupied()) {
            if (Math.abs(getSquare().getX() - boardSquare.getX()) == getMoveLimit() &&
                    Math.abs(getSquare().getY() - boardSquare.getY()) == getMoveLimit() && !boardSquare.getPiece().isFriendly()||
                    Math.abs(getSquare().getX() - boardSquare.getX()) == 0 &&
                            Math.abs(getSquare().getY() - boardSquare.getY()) == getMoveLimit()  && !boardSquare.getPiece().isFriendly()||
                    Math.abs(getSquare().getX() - boardSquare.getX()) == getMoveLimit() &&
                            Math.abs(getSquare().getY() - boardSquare.getY()) == 0  && !boardSquare.getPiece().isFriendly()) {
                return true;
            }
        } else {
            if (Math.abs(getSquare().getX() - boardSquare.getX()) == getMoveLimit() &&
                    Math.abs(getSquare().getY() - boardSquare.getY()) == getMoveLimit() ||
                    Math.abs(getSquare().getX() - boardSquare.getX()) == 0 &&
                            Math.abs(getSquare().getY() - boardSquare.getY()) == getMoveLimit() ||
                    Math.abs(getSquare().getX() - boardSquare.getX()) == getMoveLimit() &&
                            Math.abs(getSquare().getY() - boardSquare.getY()) == 0) {
                return true;
            }
        }
        return false;
    }
}
