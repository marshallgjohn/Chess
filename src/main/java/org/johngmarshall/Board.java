package org.johngmarshall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


import org.johngmarshall.Pieces.*;

public class Board extends JPanel {

    BoardSquare[][] boardArr = new BoardSquare[8][8];
    private Piece selectedPiece;
    private Object[] orgBoard = new Object[3];
    private String rows = "12345678";
    private boolean turn = true;
    private String clientID;
    private boolean gameOver = false;
    Socket client;
    ObjectInputStream reader;
    ObjectOutputStream writer;
    Piece[] kings = new Piece[2];
    //TODO network chess


    public Board() {
        createBoard();
        initPieces();
        connect();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);


            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                selectedPiece = null;
                for (int y = 7; y > -1; y--) {
                    for (int x = 0; x < 8; x++) {
                        if (boardArr[x][y].getBoundingBox().contains(e.getPoint())) {
                            if (boardArr[x][y].isOccupied() && boardArr[x][y].getPiece().isFriendly()) {
                                selectedPiece = boardArr[x][y].getPiece();
                                //boardArr[x][y].setColor(Color.GREEN);
                                boardArr[x][y].setPiece(null);


                            }
                        }
                    }
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (selectedPiece != null && turn) {
                    for (int y = 7; y > -1; y--) {
                        for (int x = 0; x < 8; x++) {
                            if (boardArr[x][y].getBoundingBox().contains(e.getPoint()) && pieceMoved(boardArr[x][y], selectedPiece)) {
                                //System.out.println(selectedPiece.isValidMove(boardArr[x][y],boardArr));
                                if (selectedPiece.isValidMove(boardArr[x][y], boardArr)) {
                                    selectedPiece.getSquare().setPiece(null);
                                    selectedPiece.setSquare(boardArr[x][y]);

                                  if(boardArr[x][y].getPiece() != null) {
                                        boardArr[x][y].getPiece().setAlive(false);
                                    }

                                    boardArr[x][y].setPiece(selectedPiece);

                                    repaint();
                                    try {
                                        writer.writeObject(boardArr);
                                        turn = false;
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                    //repaint();
                                    return;
                                }
                            }
                        }

                    }

                    boardArr[selectedPiece.getSquare().getX()][selectedPiece.getSquare().getY()].setPiece(selectedPiece);

                }
            }

            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                System.out.println(e.getPoint());
                System.out.println("Test");
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                System.out.println(e.getPoint());
            }
        });
    }

    private void connect() {
        try {
            client = new Socket("localhost",8057);
            writer = new ObjectOutputStream(client.getOutputStream());
            reader = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread readBoard = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    clientID = reader.readUTF();
                    if(clientID.equalsIgnoreCase("Client-2")) {
                        flipTable();
                        turn = false;
                        rows = "87654321";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                while(true) {
                    try {
                        boardArr = (BoardSquare[][])reader.readObject();
                        turn = true;
                        repaint();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        readBoard.start();
    }

    private void createBoard() {
        boolean color = true;
        for (int y = 7; y > -1; y--) {
            for (int x = 0; x < 8; x++) {
                if (color) {
                    this.boardArr[x][y] = new BoardSquare(x, y, Color.BLACK);
                } else {
                    this.boardArr[x][y] = new BoardSquare(x, y, Color.WHITE);
                }
                color = !color;
            }
            color = !color;
        }
    }

    private void flipTable() {
        for (int x = 0; x < boardArr.length; x++) {
            for (int y = 0; y < boardArr.length / 2; y++) {
                //boardArr[x][y].setY(boardArr.length-y-1);
                if (boardArr[x][y].isOccupied()) {
                    Piece temp = boardArr[x][y].getPiece();
                    //boardArr[x][y].setPiece(null);
                    temp.setFriendly(!temp.isFriendly());
                    if (boardArr[x][boardArr.length - y - 1].isOccupied()) {
                        Piece temp2 = boardArr[x][boardArr.length - y - 1].getPiece();
                        temp2.setFriendly(!temp2.isFriendly());
                        temp.setSquare(boardArr[x][boardArr.length - y - 1]);
                        temp2.setSquare(boardArr[x][y]);
                        boardArr[x][y].setPiece(temp2);
                        boardArr[x][boardArr.length - y - 1].setPiece(temp);
                    } else {
                        temp.setSquare(boardArr[x][boardArr.length - y - 1]);
                        boardArr[x][boardArr.length - y - 1].setPiece(temp);
                        boardArr[x][y].setPiece(null);
                    }
                } else if (boardArr[x][boardArr.length - y - 1].isOccupied()) {

                    boardArr[x][y].setPiece(boardArr[x][boardArr.length - y - 1].getPiece());
                    boardArr[x][y].getPiece().setSquare(boardArr[x][y]);
                    boardArr[x][y].getPiece().setFriendly(!boardArr[x][y].getPiece().isFriendly());
                    boardArr[x][boardArr.length - y - 1].setPiece(null);
                }
            }
        }
    }

    private void initPieces() {
        try {

            //Rooks
            boardArr[0][7].setPiece(new Rook("white", boardArr[0][7], true));
            boardArr[7][7].setPiece(new Rook("white", boardArr[7][7], true));
            boardArr[0][0].setPiece(new Rook("black", boardArr[0][0], false));
            boardArr[7][0].setPiece(new Rook("black", boardArr[7][0], false));

            boardArr[1][7].setPiece(new Knight("white", boardArr[1][7], true));
            boardArr[6][7].setPiece(new Knight("white", boardArr[6][7], true));
            boardArr[1][0].setPiece(new Knight("black", boardArr[1][0], false));
            boardArr[6][0].setPiece(new Knight("black", boardArr[6][0], false));

            boardArr[2][7].setPiece(new Bishop("white", boardArr[2][7], true));
            boardArr[5][7].setPiece(new Bishop("white", boardArr[5][7], true));
            boardArr[2][0].setPiece(new Bishop("black", boardArr[2][0], false));
            boardArr[5][0].setPiece(new Bishop("black", boardArr[5][0], false));

            boardArr[3][7].setPiece(new Queen("white", boardArr[3][7], true));
            boardArr[3][0].setPiece(new Queen("black", boardArr[3][0], false));

            kings[0] = new King("white", boardArr[4][7], true);
            kings[1] = new King("black", boardArr[4][0], false);
            boardArr[4][7].setPiece(kings[0]);
            boardArr[4][0].setPiece(kings[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int x = 0; x < 8; x++) {
            try {
                this.boardArr[x][6].setPiece(new Pawn("white", boardArr[x][6], true));
                this.boardArr[x][1].setPiece(new Pawn("black", boardArr[x][1], false));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void paintComponent(Graphics g) {

            //g.drawRect(0,100,1000,1000);
            for (int y = 7; y > -1; y--) {
                for (int x = 0; x < 8; x++) {
                    g.setColor(boardArr[x][y].getColor());
                    g.fillRect(x * 50, y * 50, 50, 50);
                    if (this.boardArr[x][y].getPiece() != null) {
                        if (this.boardArr[x][y].isOccupied()) {
                            g.drawImage(this.boardArr[x][y].getPiece().getIcon(), x * 50, y * 50, 50, 50, null);
                        }
                    }

                }
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(rows.charAt(y)), 410, ((1 + y) * 50) - 25);
                g.drawString(String.valueOf("ABCDEFGH".charAt(y)), (((y + 1) * 50)) - 25, 410);
                g.setColor(Color.RED);
                g.fillRect(225, 425, 100, 50);
                g.setColor(Color.BLACK);
                if ((turn) && clientID.equalsIgnoreCase("client-2") || (!turn) && clientID.equalsIgnoreCase("client-1")) {
                    g.drawString("Black's turn", 225, 450);
                } else {
                    g.drawString("White's turn", 225, 450);
                }

                for(Piece king : kings) {
                    System.out.println(king.isAlive());
                    if(!king.isAlive()) {
                        gameOver = true;
                        System.out.println("GAMEOVER");
                    }
                }

                if(gameOver) {
                    g.setColor(Color.orange);
                    g.fillRect(100, 150, 100, 50);
                    g.setColor(Color.black);
                    g.drawString("GAME OVER ", 150,200);
                }

            }

    }

    private boolean pieceMoved(BoardSquare square, Piece selectedPiece) {
        if (selectedPiece.getSquare().getX() != square.getX() || selectedPiece.getSquare().getY() != square.getY()) {
            return true;
        } else {
            return false;
        }
    }

}
