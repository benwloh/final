package com.company;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Controller{
    View view;
    Model model;
    Connection connection;
    Thread ServerConnect;
    Thread Listener;
    boolean turn;

    public Controller() {
        view = new View();
        connection = new Connection();
        model = new Model();
        view.setStateMM();
        view.setMMHostButtonActionListener(new HostActionListener());
        view.setMMJoinButtonActionListener(new JoinActionListener());
        view.setCancelButtonButtonActionListener(new backMM());
        view.setJJoinButtonButtonActionListener(new JJoinActionListener());
        view.setGameActionListener(new GameButtonListener());
        view.setCMainMenuButtonActionListener(new CokActionListener());
    }
    public void startGame(){
        Random rand = new Random();
        int yourRoll;
        int theirRoll;
        do{
            yourRoll = rand.nextInt(100);
            connection.send(yourRoll);
            theirRoll = connection.getResp();
        }while(yourRoll == theirRoll);
        turn = yourRoll > theirRoll;
        Listener = new Thread(new socketListener());
        Listener.start();
        model.reset();
        view.Reset();
        view.setStateGame();
        view.setGTurn(turn); // Set turn message
    }

    class HostActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ServerConnect = new Thread(new Host());
            view.setStateHost();
            ServerConnect.start();
        }
    }
    class Host implements Runnable{
            @Override
            public void run() {
                connection.setActive(true);
                if(connection.serverConnect(5000)) {
                    startGame();
                }
            }
    }
    class socketListener implements Runnable{
        @Override
        public void run() {
            while(true){
                int x;
                try {
                    x = connection.getResp();
                }catch (java.util.NoSuchElementException e){
                    return;
                }
                turn = true;
                int y = model.addDisk(x, 2);
                view.updateGameState(false, x, y); // Add opponent disc to the view
                view.setGTurn(true); // Message: now your turn
                if(model.checkWin()[0]){
                    view.setStateComplete(model.checkWin()[1]);
                    break;
                }
            }
        }
    }


    class JJoinActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(connection.connect(view.getJIp(), 5000)){
                startGame();
            }
        }
    }
    class GameButtonListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            if(turn) {
                turn = false;
                int x = Integer.parseInt(((JLabel) mouseEvent.getSource()).getText());
                connection.send(x);
                int y = model.addDisk(x, 1);
                view.updateGameState(true,x,y); // Add your disc to the view
                view.setGTurn(false); // Message: now their turn
                if(model.checkWin()[0]){
                    view.setStateComplete(model.checkWin()[1]);
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    class backMM implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setStateMM();
            connection.setActive(false);
            connection.close();
        }
    }
    class JoinActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setStateJoin();
        }
    }
    class CokActionListener implements  ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            connection.send(0);
            connection.close();
            view.setStateMM();
        }
    }
}
