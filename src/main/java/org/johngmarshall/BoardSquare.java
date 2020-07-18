package org.johngmarshall;

import org.johngmarshall.Pieces.*;

import java.awt.*;
import java.io.Serializable;

public class BoardSquare implements Serializable {
    private final String spaceName;
    private int x;
    private int y;
    private Piece piece;
    private Color color;
    private Rectangle boundingBox;
    public BoardSquare (int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.spaceName = spaceNameConverter();
        this.boundingBox = new Rectangle(this.x*50,this.y*50,50,50);
    }

    public boolean isOccupied() {
        if(piece != null) {
            return true;
        } else {
            return false;
        }
    }

    public String spaceNameConverter() {
        String ret = "";
        switch(x) {
            case 0:
                ret = "A";
                break;
            case 1:
                ret ="B";
                break;
            case 2:
                ret ="C";
                break;
            case 3:
                ret ="D";
                break;
            case 4:
                ret ="E";
                break;
            case 5:
                ret ="F";
                break;
            case 6:
                ret ="G";
                break;
            case 7:
                ret ="H";
                break;
        }
        return ret+(getY()+1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
