package org.johngmarshall.Pieces;

import org.johngmarshall.BoardSquare;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Bishop extends Piece {

    public Bishop(String name, BoardSquare square, boolean isFriendly) throws IOException {
        super(name, square, "art/" + name + "-bishop.png", 7, isFriendly,new Direction[]{Direction.DIAGONAL});
    }
}
