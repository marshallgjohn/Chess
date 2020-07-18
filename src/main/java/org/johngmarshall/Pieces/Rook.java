package org.johngmarshall.Pieces;

import org.johngmarshall.BoardSquare;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Rook extends Piece{

    public Rook(String name, BoardSquare square, boolean isFriendly) throws IOException {
        super(name,square, "art/"+name+"-rook.png",7,isFriendly,new Direction[]{Direction.HORIZONTAL,Direction.VERTICAL});
    }


/*    @Override
    public boolean isValidMove(BoardSquare board) {
        if(!board.isOccupied() && getSquare().getX() != board.getX() && getSquare().getY()== board.getY() ||
                !board.isOccupied() && getSquare().getX() == board.getX() && getSquare().getY()!= board.getY() ||
                board.isOccupied() && getSquare().getX() == board.getX() && getSquare().getY()!= board.getY() && !board.getPiece().isFriendly() ||
                board.isOccupied() && getSquare().getX() != board.getX() && getSquare().getY()== board.getY() && !board.getPiece().isFriendly()) {
            System.out.printf("NEW Y:%d, OLD Y:%d, MOVE LIMIT:%d",board.getY(),this.getSquare().getY(),getMoveLimit());
            return true;
        } else {
            return false;
        }
    }*/
/*    public boolean isValidMove(BoardSquare boardSquare, BoardSquare[][] board) {
        if (boardSquare.isOccupied()) {
            if (Math.abs(getSquare().getX() - boardSquare.getX()) == Math.abs(getSquare().getY() - boardSquare.getY()) && !boardSquare.getPiece().isFriendly()) {
                for(int i = 0; i < Math.abs(getSquare().getX() - boardSquare.getX())-1;i++) {
                    if(board[getSquare().getX()+i][getSquare().getY()+i].isOccupied()) {
                        return false;
                    }
                }

                return true;
            }
        } else {
            if (Math.abs(getSquare().getX() - boardSquare.getX()) == Math.abs(getSquare().getY() - boardSquare.getY())) {
                for(int i = 0; i < Math.abs(getSquare().getX() - boardSquare.getX())-1;i++) {
                    if(board[getSquare().getX()+i-1][getSquare().getY()+i-1].isOccupied()) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }*/
}
