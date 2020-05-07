package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerConnection extends ServerSocket implements conn{
    static Scanner reader;
    static PrintWriter writer;
    Socket sock;
    public ServerConnection() throws IOException {
        super(5000);
        this.setSoTimeout(100);
    }
    public void caccept() throws IOException {
        while(active && (sock == null || sock.isClosed())){
            sock = this.accept();
        }
        if(!this.isClosed()) {
            reader = new Scanner(new InputStreamReader(sock.getInputStream()));
            writer = new PrintWriter(sock.getOutputStream(), true);
        }
        this.close();
    }

    public void send(int i){
        writer.print(i);
        writer.flush();
    }
    public String getResp(){
        String resp = "";
        resp = reader.nextLine();
        return resp;
    }

    @Override
    public void connclose() throws IOException {
        this.close();
        if(sock != null){
            sock.close();
        }
    }
}
