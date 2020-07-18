package org.johngmarshall.Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class Server {
    static ServerSocket serverSocket;
    static Socket server;
    static ObjectInputStream in;
    static ObjectOutputStream out;
    static ArrayList<ConnectionHandler> arr = new ArrayList<>();

    public static void main(String[] args) {
        boolean firstClient = true;
        try {
            serverSocket = new ServerSocket(8057);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ConnectionHandler handler = null;

        while (true) {
            try {
                server = serverSocket.accept();
                System.out.println("System waiting on new client" + server);
                in = new ObjectInputStream(server.getInputStream());
                out = new ObjectOutputStream(server.getOutputStream());

                System.out.println("Creating new client");


                if(firstClient) {
                    handler = new ConnectionHandler(server, "Client-1", in, out);
                    firstClient = false;
                } else {
                    handler = new ConnectionHandler(server, "Client-2", in, out);
                }

                arr.add(handler);
                Thread t = new Thread(handler);
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
