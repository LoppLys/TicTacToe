package tictactoe;

import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {
    private static final char HUMAN = 'X';
    private static final char COMP = 'O';
    private static final int COMP_LOSS = -1; 
    private static final int DRAW = 0;
    private static final int COMP_WIN = 1; 
    private static final int FULL = 9;
    private int movesMade = 0;
    private char[][] gameBoard;
    private boolean gameOver = false;

    public TicTacToe(){
        run();
    }

    private void createBoard(int size){
        gameBoard = new char[size][size];

        for (char[] chars : gameBoard) {
            Arrays.fill(chars, ' ');
        }
    }

    private void run(){
        createBoard(3);
        Scanner scanner = new Scanner(System.in);
        while(!gameOver) {
            printGameBoard();
            System.out.println("Where do you want to put a marker? x y");
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;
            printHumanChoice(x, y);
            printComputerChoice(findCompMove().move);
        }
        scanner.close();
    }

    private void printGameBoard(){
        for(int i = 0; i < gameBoard.length; i++){
            for(int j = 0; j < gameBoard[i].length; j++){
                System.out.printf("| %c ", gameBoard[i][j]);
            }
            System.out.println("|");
            if(i != gameBoard.length-1) {
                for(int k = 0; k < gameBoard[i].length; k++){
                    System.out.print("+---");
                }
                System.out.println("+");

            }
        }
    }

    private void printHumanChoice(int x, int y){
        try {
            if (gameBoard[y][x] == ' ') {
                gameBoard[y][x] = HUMAN;
                movesMade++;
                if(checkIfHumanWon(x, y)){
                    printGameBoard();
                    System.out.println("The human has won, i cant believe it.");
                    gameOver = true;
                }
            }
            else{
                System.out.println("That spot is already taken you crazy human!");
            }
        }
        catch(Exception e){
            System.out.println("Please try to keep your markers INSIDE the board.");
        }
    }

    private void printComputerChoice(int i){
        if(fullboard()){
            printGameBoard();
            System.out.println("You were a worthy opponent, human. It is a draw!");
            gameOver = true;
            return;
        }
        int y = i/3;
        int x = i%3;
        gameBoard[y][x] = COMP;
        movesMade++;
        if(checkIfCompWon(x, y)){
            printGameBoard();
            System.out.println("Hahaha I win, lousy human.");
            gameOver = true;
        }
    }

    private boolean checkIfHumanWon(int x, int y){
        return checkRow(y, HUMAN) || checkColumn(x, HUMAN) || checkDiagonal(x, y, HUMAN);
    }

    private boolean checkIfCompWon(int x, int y){
        return checkRow(y, COMP) || checkColumn(x, COMP) || checkDiagonal(x, y, COMP);
    }

    private boolean checkRow(int y, char marker){
        for(char c : gameBoard[y]){
            if(c != marker){
                return false;
            }
        }

        return true;
    }

    private boolean checkColumn(int x, char marker){
        for(char[] c : gameBoard){
            if(c[x] != marker){
                return false;
            }
        }

        return true;
    }

    private boolean checkDiagonal(int x, int y, char marker){
        if(x == y){
            for(int i = 0; i < gameBoard.length; i++){
                if(gameBoard[i][i] != marker){
                    return false;
                }
            }

            return true;
        }
        else if(x+y == 2){
            for(int i = 0; i < gameBoard.length; i++){
                if(gameBoard[2-i][i] != marker){
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    //finds best move for computer
    public MoveInfo findCompMove(){
        int responseValue;
        int value;
        int bestMove = 0; //matrix starts at 0
        MoveInfo quickWinInfo;
        if(fullboard()){
            value = DRAW;
        }else if((quickWinInfo = immediateCompWin()) != null){
            return quickWinInfo;
        }else{
            //gå igenom alla moves
            value = COMP_LOSS;
            for(int i = 0; i < FULL; i++){
                if(gameBoard[i/3][i%3] == ' '){
                    place(i, COMP);
                    responseValue = findHumanMove().value;
                    unplace(i);
                    if(responseValue > value){
                        value = responseValue;
                        bestMove = i;
                    }
                }
            }
        }

        return new MoveInfo(bestMove, value);
    }
    
    public MoveInfo findHumanMove(){
        int responseValue;
        int value;
        int bestMove = 0; //matrix starts at 0
        MoveInfo quickWinInfo;
        if(fullboard()){
            value = DRAW;
        }else if((quickWinInfo = immediateHumanWin()) != null){
            return quickWinInfo;
        }else{
            //gå igenom alla moves
            value = COMP_WIN;
            for(int i = 0; i < FULL; i++){
                if(gameBoard[i/3][i%3] == ' '){
                    place(i, HUMAN);
                    responseValue = findCompMove().value;
                    unplace(i);
                    if(responseValue < value){
                        value = responseValue;
                        bestMove = i;
                    }
                }
            }
        }

        return new MoveInfo(bestMove, value);
    }

    private void place(int i, char player){
        gameBoard[i/3][i%3] = player;
        movesMade++;
    }

    private void unplace(int i){
        gameBoard[i/3][i%3] = ' ';
        movesMade--;
    }

    private MoveInfo immediateCompWin(){
        for(int y = 0; y < gameBoard.length; y++){
            for(int x = 0; x < gameBoard[y].length; x++){
                if(gameBoard[y][x] == ' '){
                    place(y*3 + x, COMP);
                    if(checkIfCompWon(x, y)){
                        unplace(y*3 + x);
                        return new MoveInfo(y*3 + x, 1);
                    }
                    unplace(y*3 + x);

                }
            }
        }

        return null;
    }

    private MoveInfo immediateHumanWin(){
        for(int y = 0; y < gameBoard.length; y++){
            for(int x = 0; x < gameBoard[y].length; x++){
                if(gameBoard[y][x] == ' '){
                    place(y*3 + x, HUMAN);
                    if(checkIfHumanWon(x, y)){
                        unplace(y*3 + x);
                        return new MoveInfo(y*3 + x, -1);
                    }
                    unplace(y*3 + x);

                }
            }
        }

        return null;
    }


    private boolean fullboard(){
        return movesMade == FULL;  
    }

    public static void main(String[] args){
        TicTacToe game = new TicTacToe();
    }

    public class MoveInfo {
        private int move; 
        private int value; 
    
        public MoveInfo(int move, int value){
            this.move = move;
            this.value = value;
        }
    }
}



