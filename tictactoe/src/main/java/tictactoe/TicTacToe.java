package tictactoe;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TicTacToe {
    private static final char HUMAN = 'X';
    private static final char COMP = 'O';
    private static final int COMP_LOSS = -1; 
    private static final int DRAW = 0;
    private static final int COMP_WIN = 1;
    private static final int SIZE = 5;
    private static final int MAX_DEPTH = 10;
    private int movesMade = 0;
    private char[][] gameBoard;
    private boolean gameOver = false;
    private boolean isPlayerTurn = true;

    public TicTacToe(){
        run();
    }

    private void createBoard(){
        gameBoard = new char[SIZE][SIZE];

        for (char[] chars : gameBoard) {
            Arrays.fill(chars, ' ');
        }
    }

    private void run(){
        createBoard();
        Scanner scanner = new Scanner(System.in);

        while(!gameOver) {

            if(isPlayerTurn){
                printGameBoard();
                System.out.println("Where do you want to put a marker? x y");
                int x = scanner.nextInt() - 1;
                int y = scanner.nextInt() - 1;
                printHumanChoice(x, y);
            } else {
                printComputerChoice(findCompMove(COMP_LOSS, COMP_WIN, 0).move);
            }
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
            if (isEmpty(x, y)) {
                place(y*SIZE + x, HUMAN);
                isPlayerTurn = !isPlayerTurn;
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
        place(i, COMP);
        isPlayerTurn = !isPlayerTurn;
        if(checkIfCompWon(i%SIZE, i/SIZE)){
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
        else if(x+y == SIZE - 1){
            for(int i = 0; i < gameBoard.length; i++){
                if(gameBoard[SIZE-1-i][i] != marker){
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    //finds best move for computer
    public MoveInfo findCompMove(int alpha, int beta, int depth){
        if(isEmpty(2, 2)){
            return new MoveInfo(2*SIZE + 2, 1);
        }else if(movesMade < 4){
            for(int i = 1; i < 3; i++){
                for(int j = 1; j < 3; j++){
                    if(isEmpty(j, i)){
                        return new MoveInfo(i*SIZE + j, 1);
                    }
                }
            }
        }
        int responseValue;
        int value;
        int bestMove = 0; //matrix starts at 0
        MoveInfo quickWinInfo;
        if(fullboard()){
            value = DRAW;
        }else if((quickWinInfo = immediateCompWin()) != null){
            return quickWinInfo;
        }else if (depth > MAX_DEPTH){
            value = 0;
        }else {
            //gå igenom alla moves
            value = alpha;
            for (int i = 0; i < SIZE * SIZE && value < beta; i++) { // Kör om de värden vi kan hitta är bättre för datorn än det vi har.
                if (isEmpty(i)) {
                    place(i, COMP);
                    responseValue = findHumanMove(value, beta, depth + 1).value;
                    unplace(i);
                    if (responseValue > value) {
                        value = responseValue;
                        bestMove = i;
                    }
                }
            }
        }

        return new MoveInfo(bestMove, value);
    }
    
    public MoveInfo findHumanMove(int alpha, int beta, int depth){
            int responseValue;
            int value;
            int bestMove = 0; //matrix starts at 0
            MoveInfo quickWinInfo;

            if (fullboard()) {
                value = DRAW;
            } else if ((quickWinInfo = immediateHumanWin()) != null) {
                return quickWinInfo;
            } else if (depth > MAX_DEPTH){
                value = 0;
            }
            else {
                //gå igenom alla moves
                value = beta;
                for (int i = 0; i < SIZE * SIZE && value > alpha; i++) { // Kör om de värden vi kan hitta är bättre för människan än det vi har.
                    if (isEmpty(i)) {
                        place(i, HUMAN);
                        responseValue = findCompMove(alpha, value, depth + 1).value;
                        unplace(i);
                        if (responseValue < value) {
                            value = responseValue;
                            bestMove = i;
                        }
                    }
                }
            }

        return new MoveInfo(bestMove, value);
    }

    private void place(int i, char player){
        gameBoard[i/SIZE][i%SIZE] = player;
        movesMade++;
    }

    private void unplace(int i){
        gameBoard[i/SIZE][i%SIZE] = ' ';
        movesMade--;
    }

    private MoveInfo immediateCompWin(){
        for(int y = 0; y < gameBoard.length; y++){
            for(int x = 0; x < gameBoard[y].length; x++){
                if(isEmpty(x, y)){
                    place(y*SIZE + x, COMP);
                    if(checkIfCompWon(x, y)){
                        unplace(y*SIZE + x);
                        return new MoveInfo(y*SIZE + x, 1);
                    }
                    unplace(y*SIZE + x);

                }
            }
        }

        return null;
    }

    private MoveInfo immediateHumanWin(){
        for(int y = 0; y < gameBoard.length; y++){
            for(int x = 0; x < gameBoard[y].length; x++){
                if(isEmpty(x, y)){
                    place(y*SIZE + x, HUMAN);
                    if(checkIfHumanWon(x, y)){
                        unplace(y*SIZE + x);
                        return new MoveInfo(y*SIZE + x, -1);
                    }
                    unplace(y*SIZE + x);
                }
            }
        }

        return null;
    }


    private boolean fullboard(){
        return movesMade == SIZE*SIZE;
    }

    private boolean isEmpty(int i){
        return gameBoard[i / SIZE][i % SIZE] == ' ';
    }

    private boolean isEmpty(int x, int y){
        return gameBoard[y][x] == ' ';
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



