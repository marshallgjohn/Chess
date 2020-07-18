package org.johngmarshall.Pieces;

import org.johngmarshall.BoardSquare;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Queen extends Piece{

    public Queen(String name, BoardSquare square, boolean isFriendly) throws IOException {
        super(name,square, "art/"+name+"-queen.png",7,isFriendly,new Direction[]{Direction.DIAGONAL,Direction.HORIZONTAL,Direction.VERTICAL});
    }
}
