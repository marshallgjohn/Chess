package org.johngmarshall.Pieces;

import org.johngmarshall.BoardSquare;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

public abstract class Piece implements Serializable {
    private String color;
    private boolean alive = true;
    private BoardSquare square;
    private String icon;
    private int moveLimit;
    private boolean isFriendly;
    private Direction[] directions;

    public Piece(String color, BoardSquare square, String icon, int moveLimit, boolean isFriendly, Direction[] directions) {
        this.color = color;
        this.square = square;
        this.icon = icon;
        this.moveLimit = moveLimit;
        this.isFriendly = isFriendly;
        this.directions = directions;
    }

    public boolean isValidMove(BoardSquare square, BoardSquare[][] board) {
        Direction direction = getDirection(square);
        if(Arrays.asList(directions).contains(direction)) {
            switch (getDirection(square)) {
                case DIAGONAL:
                        return diagonalClearPath(square, board);
                case HORIZONTAL:
                        return horizontalClearPath(square, board);
                case VERTICAL:
                        return verticalClearPath(square, board);
                case NONE:
                    return false;
                    }

            }
        return false;
    }

    private boolean diagonalClearPath(BoardSquare boardSquare, BoardSquare[][] board) {
        if (square.getX() > boardSquare.getX() && square.getY() > boardSquare.getY()) {
            for (int i = 0; square.getX() + i != boardSquare.getX() && square.getY() + i != boardSquare.getY(); i--) {
                int x = square.getX() + i;
                int y = square.getY() + i;
                if (board[x][y].isOccupied()) {
                    if(lastPieceIsEnemy(boardSquare,board,x,y)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else if(square.getX() < boardSquare.getX() && square.getY() < boardSquare.getY()) {
            for (int i = 0; square.getX()+ i != boardSquare.getX() && square.getY()+ i != boardSquare.getY(); i++) {
                int x = square.getX() + i;
                int y = square.getY() + i;
                if (board[x][y].isOccupied()) {
                    if (lastPieceIsEnemy(boardSquare, board, x, y)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else if(square.getX() > boardSquare.getX() && square.getY() < boardSquare.getY()) {
            for (int i = 0; square.getX()+ i != boardSquare.getX() && square.getY()+ i != boardSquare.getY(); i++) {
                int x = square.getX() - i;
                int y = square.getY() + i;
                if (board[x][y].isOccupied()) {
                    if (lastPieceIsEnemy(boardSquare, board, x, y)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else{
            for (int i = 0; square.getX()+ i != boardSquare.getX() && square.getY()- i != boardSquare.getY(); i++) {
                int x = square.getX() + i;
                int y = square.getY() - i;
                if (board[x][y].isOccupied()) {
                    if(lastPieceIsEnemy(boardSquare,board,x,y)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        if (board[boardSquare.getX()][boardSquare.getY()].isOccupied()) {
            if(lastPieceIsEnemy(boardSquare,board,boardSquare.getX(),boardSquare.getY())) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean horizontalClearPath(BoardSquare boardSquare, BoardSquare[][] board) {
        if(square.getX() < boardSquare.getX()) {
            for (int i = 0; i <= Math.abs(square.getX() - boardSquare.getX()); i++) {
                int x = square.getX() + i;
                int y = square.getY();
                if (board[x][y].isOccupied()) {
                    if (lastPieceIsEnemy(boardSquare, board, x, y)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else {
            System.out.println(-Math.abs(square.getX() - boardSquare.getX()));
            for (int i = 0; i >= -Math.abs(square.getX() - boardSquare.getX()); i--) {
                int x = square.getX() + i;
                int y = square.getY();
                if (board[x][y].isOccupied()) {
                    if (lastPieceIsEnemy(boardSquare, board, x, y)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean verticalClearPath(BoardSquare boardSquare, BoardSquare[][] board) {
        if(square.getY() < boardSquare.getY()) {
            for (int i = 1; i <= Math.abs(square.getY() - boardSquare.getY()); i++) {
                int x = square.getX();
                int y = square.getY()+ i;
                if (board[x][y].isOccupied()) {
                    if (lastPieceIsEnemy(boardSquare, board, x, y)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else {
            for (int i = -1; i >= -Math.abs(square.getY() - boardSquare.getY()); i--) {
                int x = square.getX();
                int y = square.getY() + i;
                if (board[x][y].isOccupied()) {
                    if (lastPieceIsEnemy(boardSquare, board, x, y)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private Direction getDirection(BoardSquare square) {
        if(Math.abs(getSquare().getX() -square.getX()) == Math.abs(getSquare().getY() -square.getY()))
        {
            return Direction.DIAGONAL;
        } else if (getSquare().getX() != square.getX() && getSquare().getY()== square.getY())
        {
            return Direction.HORIZONTAL;
        } else if (getSquare().getX() == square.getX() && getSquare().getY()!= square.getY()) {
            return Direction.VERTICAL;
        } else {
            return Direction.NONE;
        }
    }

    public enum Direction {
        VERTICAL,
        HORIZONTAL,
        DIAGONAL,
        NONE
    }


    private boolean lastPieceIsEnemy(BoardSquare boardSquare, BoardSquare[][] board, int x, int y) {
        if(!board[x][y].getPiece().isFriendly() &&  boardSquare.getX() == x && boardSquare.getY() == y) {
            board[x][y].getPiece().setAlive(false);
            return true;
        } else {
            return false;
        }
    }



    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public BoardSquare getSquare() {
        return square;
    }

    public void setSquare(BoardSquare square) {
        this.square = square;
    }

    public Image getIcon() {
        try {
            return ImageIO.read(new File(this.icon));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getMoveLimit() {
        return moveLimit;
    }

    public void setMoveLimit(int moveLimit) {
        this.moveLimit = moveLimit;
    }

    public boolean isFriendly() {
        return isFriendly;
    }

    public void setFriendly(boolean friendly) {
        isFriendly = friendly;
    }

}
