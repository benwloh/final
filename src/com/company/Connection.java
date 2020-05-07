package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Connection {
    static Scanner reader;
    static PrintWriter writer;
    Socket connection;
    boolean active;

    public boolean serverConnect(int port) {
        ServerSocket connect;
        boolean connected = false;
        try {
            connect = new ServerSocket(5000);
            connect.setSoTimeout(10);
            while (active && !connected) {
                try {
                    connection = connect.accept();
                    connected = true;
                } catch (SocketTimeoutException e) {

                }
            }
            if( connected){
                reader = new Scanner(new InputStreamReader(connection.getInputStream()));
                writer = new PrintWriter(connection.getOutputStream(), true);
            }
            connect.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connected;
    }

    public void setActive(boolean state) {
        active = state;
    }

    public boolean connect(String ip, int port) {
        try {
            connection = new Socket(ip, port);
            reader = new Scanner(new InputStreamReader(connection.getInputStream()));
            writer = new PrintWriter(connection.getOutputStream(), true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void close(){
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(int i) {
        writer.println(i);
        writer.flush();
    }

    public int getResp() {
        return reader.nextInt();
    }
}
