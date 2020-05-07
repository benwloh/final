package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;


// I think this is just about as good as it can get, java swing is garbage

public class View {
    // Main layout, frame, panels, etc
    JFrame frame;
    CardLayout cardLayout;

    JPanel masterPanel;

    JPanel mainMenuPanel;
    JPanel joinPanel;
    JPanel hostPanel;
    JPanel gamePanel;
    JPanel gamePanelContent;
    JPanel errorPanel;
    JPanel completePanel;

    // Coordinates for colors
    JLabel[][] gameData;
    // Bottom info panel for game
    JButton gameQuit;
    JLabel gameMsg;

    // Main menu - Root
    JButton hostButton;
    JButton joinButton;

    // Join menu
    JLabel joinTitle;
    JTextField joinIP;
    JButton joinConnect;
    JButton joinBack;

    // Host menu
    JButton hostBack;
    JLabel hostStatus;

    // Error menu
    JLabel errMsg;
    JButton errOk;

    // Game over menu
    JLabel completeMsg;
    JButton completeOk;

    // Layouts for each menu
    BoxLayout mmBoxLayout;
    BoxLayout joinBoxLayout;
    BoxLayout hostBoxLayout;
    BoxLayout errorBoxLayout;
    BoxLayout completeBoxLayout;
    BorderLayout gameBorderLayout;
    GridLayout gamePanelContentGrid;

    public View(){ // Default constructor, build the view
        // Main panel, frame
        frame = new JFrame("Connect Four!");
        masterPanel = new JPanel();
        cardLayout = new CardLayout();
        masterPanel.setLayout(cardLayout);

        // Main menu panel setup
        mainMenuPanel = new JPanel();
        mmBoxLayout = new BoxLayout(mainMenuPanel, BoxLayout.Y_AXIS);
        mainMenuPanel.setLayout(mmBoxLayout);
        mainMenuPanel.setBorder(new EmptyBorder(new Insets(150, 200, 150, 200)));

        mainMenuPanel.add(Box.createGlue()); // top buffer space

        joinButton = new JButton("Join Game");
        joinButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        mainMenuPanel.add(joinButton);

        mainMenuPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between elements

        hostButton = new JButton("Host Game");
        hostButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        mainMenuPanel.add(hostButton);

        mainMenuPanel.add(Box.createGlue()); // bottom buffer space
        // /Main menu panel stuff

        // Join panel stuff
        joinPanel = new JPanel();
        joinBoxLayout = new BoxLayout(joinPanel, BoxLayout.Y_AXIS);
        joinPanel.setLayout(joinBoxLayout);
        joinPanel.setBorder(new EmptyBorder(new Insets(150, 200, 150, 200)));

        joinTitle = new JLabel("Enter Host IP");
        joinTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        joinPanel.add(joinTitle);

        joinPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        joinIP = new JTextField();
        joinIP.setPreferredSize(new Dimension(100,20));
        joinIP.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        joinIP.setMaximumSize(joinIP.getPreferredSize());
        joinPanel.add(joinIP);
        joinPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        joinConnect = new JButton("Join Game");
        joinConnect.setAlignmentX(JButton.CENTER_ALIGNMENT);
        joinPanel.add(joinConnect);
        joinPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        joinBack = new JButton("Back");
        joinPanel.add(joinBack);
        joinBack.setAlignmentX(JButton.CENTER_ALIGNMENT);
        // / Join panel stuff

        // Host panel stuff
        hostPanel = new JPanel();
        hostBoxLayout = new BoxLayout(hostPanel, BoxLayout.Y_AXIS);
        hostPanel.setLayout(hostBoxLayout);
        hostPanel.setBorder(new EmptyBorder(new Insets(150, 200, 150, 200)));

        hostStatus = new JLabel("Waiting for connection");
        hostStatus.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        hostPanel.add(hostStatus);

        hostPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        hostBack = new JButton("Back");
        hostPanel.add(hostBack);

        // Back button init in join menu block
        // /Host panel stuff

        // Game panel stuff
        gameData = new JLabel[6][7]; // 6 rows x 7 column game board

        gamePanel = new JPanel(); // use default borderlayout, game data in center
        gameBorderLayout = new BorderLayout();
        gamePanel.setLayout(gameBorderLayout);
        gamePanelContent = new JPanel();
        gamePanelContentGrid = new GridLayout(6, 7); // 6 rows x 7 columns
        gamePanelContent.setLayout(gamePanelContentGrid);

        gamePanel.setBackground(Color.WHITE);
        gamePanelContent.setBackground(Color.WHITE);

        for(int row = 0; row < gameData.length; row++) {
            for(int column = 0; column < gameData[row].length; column++){
                addIcon(row, column);
            }
        }

        JPanel bottomPanel = new JPanel(new BorderLayout());
        gameQuit = new JButton("Quit");
        gameQuit.setPreferredSize(new Dimension(100,20));
        gameQuit.setMaximumSize(joinIP.getPreferredSize());

        gameMsg = new JLabel("Message");

        gamePanelContent.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
        gamePanel.add(BorderLayout.CENTER, gamePanelContent);
        bottomPanel.add(BorderLayout.WEST, gameQuit);
        bottomPanel.add(BorderLayout.CENTER, gameMsg);
        gamePanel.add(BorderLayout.SOUTH, bottomPanel);
        // /Game stuff

        // Error stuff
        errorPanel = new JPanel();
        errorBoxLayout = new BoxLayout(errorPanel, BoxLayout.Y_AXIS);
        errorPanel.setLayout(errorBoxLayout);
        errorPanel.setBorder(new EmptyBorder(new Insets(150, 200, 150, 200)));


        errMsg = new JLabel("Error: ");
        errMsg.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        errOk = new JButton("OK");
        errOk.setAlignmentX(JButton.CENTER_ALIGNMENT);

        errorPanel.add(errMsg);
        errorPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        errorPanel.add(errOk);
        // / Error stuff

        // Complete stuff
        completePanel = new JPanel();
        completeBoxLayout = new BoxLayout(completePanel, BoxLayout.Y_AXIS);
        completePanel.setLayout(completeBoxLayout);
        completePanel.setBorder(new EmptyBorder(new Insets(150, 200, 150, 200)));


        completeMsg = new JLabel("X wins");
        completeMsg.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        completeOk = new JButton("OK");
        completeOk.setAlignmentX(JButton.CENTER_ALIGNMENT);

        completePanel.add(completeMsg);
        completePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        completePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        completePanel.add(completeOk);
        // / Complete stuff

        // Start, add all panels to the master panel, add master panel to frame
        masterPanel.add(mainMenuPanel, "MAINMENU");
        masterPanel.add(joinPanel, "JOIN");
        masterPanel.add(hostPanel, "HOST");
        masterPanel.add(gamePanel, "GAME");
        masterPanel.add(errorPanel, "ERR");
        masterPanel.add(completePanel, "COMPLETE");
        frame.add(masterPanel);
        frame.setSize(960, 540);
        frame.setVisible(true);

    }

    public void setStateMM(){
        cardLayout.show(masterPanel, "MAINMENU");
    }

    public void setStateJoin(){
        cardLayout.show(masterPanel, "JOIN");
    }

    public void setStateHost(){
        cardLayout.show(masterPanel, "HOST");
    }

    public void setStateGame(){
        cardLayout.show(masterPanel, "GAME");
    }

    public void setStateError(String error){
        errMsg.setText("Error: " + error);
        cardLayout.show(masterPanel, "ERR");
    }

    public void setStateComplete(boolean player){
        if(player){
            completeMsg.setText("You win!");
        }
        else{
            completeMsg.setText("You lose!");
        }
        cardLayout.show(masterPanel, "COMPLETE");
    }

    private void addIcon(int row, int column){
        gameData[row][column] = new JLabel(new ImageIcon("icons/white.png"), JLabel.CENTER);
        gameData[row][column].setText(Integer.toString(column));
        gameData[row][column].setForeground(Color.WHITE);
        gamePanelContent.add(gameData[row][column]);
    }

    public void updateGameState(boolean player, int x, int y){
        //x = 6-x;
        y = 5-y;
        if(player){
            gameData[y][x].setIcon(new ImageIcon("icons/red.png"));
        }
        else{
            gameData[y][x].setIcon(new ImageIcon("icons/blue.png"));
        }
    }

    public void setGTurn(boolean player){
        if(player){
            gameMsg.setText("Your turn");
        }
        else{
            gameMsg.setText("Their turn");
        }
    }

    public String getJIp(){
        return joinIP.getText();
    }

    public void setMMHostButtonActionListener(ActionListener al){
        hostButton.addActionListener(al);
    }
    public void setMMJoinButtonActionListener(ActionListener al){
        joinButton.addActionListener(al);
    }
    public void setCancelButtonButtonActionListener(ActionListener al){ // JCancel and HCancel are the same, use one AL here
        joinBack.addActionListener(al);
        hostBack.addActionListener(al);
        gameQuit.addActionListener(al);
    }
    public void setJJoinButtonButtonActionListener(ActionListener al){
        joinConnect.addActionListener(al);
    }
    public void setCMainMenuButtonActionListener(ActionListener al){
        completeOk.addActionListener(al);
    }
    public void setGameActionListener(MouseListener ml){
        for(int i=0; i <7; i++){
            for(int j=0; j < 6; j++){
                gameData[j][i].addMouseListener(ml);
            }
        }
    }
    public void Reset(){
        for(int i=0; i <7; i++){
            for(int j=0; j < 6; j++){
                gameData[j][i].setIcon(new ImageIcon("icons/white.png"));
            }
        }
    }
}
