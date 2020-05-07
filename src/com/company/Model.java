package com.company;

import javax.swing.text.StyledEditorKit;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Model extends Socket {
    protected int[][] Board;
    protected int[] lastMove;


    public Model(){
        Board = new int[7][6];
        lastMove = new int[3];
    }
    public int addDisk(int x, int player){
        for(int i = 0; i < 6; i ++){
            if(Board[x][i] == 0) {
                Board[x][i] = player;
                lastMove[0] = x;
                lastMove[1] = i;
                lastMove[2] = player;
                return i;
            }
        }
        return -1;
    }
    boolean[] checkWin() {
        boolean[] win = {false,false};
        if(Board[lastMove[0]][lastMove[1]] == 0){
            return win;
        }
        int x = 1;
        int y = 1;
        int player = lastMove[2];
        if(lastMove[0]/4 == 0){
            x = -1;
        }
        if(lastMove[1]/3 == 0){
            y = -1;
        }
        for(int i = lastMove[0] -x; i >= 0 && i < 7 && Math.abs(lastMove[0] - i) <=1; i += x)
        {
            for(int j = lastMove[1] -y; j >=0 && j < 6 && Math.abs(lastMove[1] - j) <=1; j += y){
                if(Board[i][j] == lastMove[2] && !(i == lastMove[0] && j == lastMove[1])){
                    int c = 2;
                    int ix = i - lastMove[0];
                    int jy = j - lastMove[1];
                    for(int q = i + ix, p = j + jy; q >=0 && p >=0 && q < 7 &&  p < 6 && Board[q][p] == player; q += ix, p += jy, c++);
                    for(int q = i - (2*ix), p = j - (2*jy); q >=0 && p >=0 && q < 7 &&  p < 6 && Board[q][p] == player; q -= ix, p -= jy, c++);
                    if(c == 4){
                        return new boolean[]{true, player == 1};
                    }
                }
            }
        }
        return win;
    }

    public void reset(){
        Board = new int[7][6];
        lastMove = new int[3];
    }
}
