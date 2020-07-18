package org.johngmarshall.Network;

import org.johngmarshall.Board;
import org.johngmarshall.BoardSquare;
import org.johngmarshall.Pieces.Piece;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private Socket server;
    private String name;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ConnectionHandler(Socket server, String name, ObjectInputStream in, ObjectOutputStream out) {
        this.server = server;
        this.name = name;
        this.in = in;
        this.out = out;

        try {
            out.writeUTF(name);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        BoardSquare[][] board;

        while (true) {
            try {
                board = (BoardSquare[][]) in.readObject();
                board = flipTable(board);
                for(ConnectionHandler c: Server.arr) {
                    if(!c.name.equalsIgnoreCase(this.name)) {
                        c.out.writeObject(board);
                        c.out.flush();
                    }
                }


            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private BoardSquare[][] flipTable(BoardSquare[][] boardArr) {
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
        return boardArr;
    }


    public Socket getServer() {
        return server;
    }

    public void setServer(Socket server) {
        this.server = server;
    }

}
