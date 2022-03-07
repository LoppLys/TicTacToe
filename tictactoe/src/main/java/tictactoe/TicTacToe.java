package tictactoe;

import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {
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
        createBoard(15);
        Scanner scanner = new Scanner(System.in);
        while(!gameOver) {
            printGameBoard();
            System.out.println("Where do you want to put a marker? x y");
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;
            printHumanChoice(x, y);
            printComputerChoice();
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
                gameBoard[y][x] = 'X';
                checkIfPlayerWon(x, y);
            }
            else{
                System.out.println("That spot is already taken you crazy human!");
            }
        }
        catch(Exception e){
            System.out.println("Please try to keep your markers INSIDE the board.");
        }
    }

    private void printComputerChoice(){
        // Do minimax stuff to place computer player marker.

        // Do check to see if computer player has won.
    }

    private void checkIfPlayerWon(int x, int y){
        if(checkRow(y, 'X') || checkColumn(x, 'X') || checkDiagonal(x, y, 'X')){
            printGameBoard();
            System.out.println("The human has won, i cant believe it.");
            gameOver = true;
        }
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
        else if(x+y == 4){
            for(int i = 0; i < gameBoard.length; i++){
                if(gameBoard[4-i][i] != marker){
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    

    public static void main(String[] args){
        TicTacToe game = new TicTacToe();
    }


    public class MoveInfo {
        private static final int COMP_LOSS = -1; 
        private static final int DRAW = 0;
        private static final int COMP_WIN = 1; 
        private int move; 
        private int value; 
    
        public MoveInfo(int move, int value){
            this.move = move;
            this.value = value;
        }
        //finds best move for computer
        public MoveInfo findCompMove(){
            int i;
            int responseValue;
            int value;
            int bestValue;
            MoveInfo quickWinInfo;
        }
    
        
        public MoveInfo findHumanMove(){
    
        }
    
    
        private boolean fullboard(){
            
        }
    
    
    }




}



