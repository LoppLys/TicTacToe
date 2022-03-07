package tictactoe;

import java.util.Scanner;

public class TicTacToe {
    private char[][] gameBoard = {
    {'|' ,' ',' ',' ', '|', ' ',' ',' ', '|', ' ',' ',' ', '|', ' ',' ',' ', '|', ' ',' ',' ', '|'}, 
    {'-','-','-','-','+','-','-','-','+','-','-','-','+','-','-','-','+','-','-','-','-'},
    {'|' ,' ',' ',' ', '|', ' ',' ',' ', '|', ' ',' ',' ', '|', ' ',' ',' ', '|', ' ',' ',' ', '|'},
    {'-','-','-','-','+','-','-','-','+','-','-','-','+','-','-','-','+','-','-','-','-'},
    {'|' ,' ',' ',' ', '|', ' ',' ',' ', '|', ' ',' ',' ', '|', ' ',' ',' ', '|', ' ',' ',' ', '|'}, 
    {'-','-','-','-','+','-','-','-','+','-','-','-','+','-','-','-','+','-','-','-','-'},
    {'|' ,' ',' ',' ', '|', ' ',' ',' ', '|', ' ',' ',' ', '|', ' ',' ',' ', '|', ' ',' ',' ', '|'},
    {'-','-','-','-','+','-','-','-','+','-','-','-','+','-','-','-','+','-','-','-','-'},
    {'|' ,' ',' ',' ', '|', ' ',' ',' ', '|', ' ',' ',' ', '|', ' ',' ',' ', '|', ' ',' ',' ', '|'}};
    

    public TicTacToe(){
        run();
    }

    private void run(){
        printGameBoard();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter first placement (1-25)");
        int pos = scanner.nextInt();
        printHumanChoice(pos);
        scanner.close();
    }

    private void printGameBoard(){
        for(char[] row : gameBoard){
            for(char column : row){
                System.out.print(column);
            }
            System.out.println(); //new line
        }
    }

    private void printHumanChoice(int pos){
        switch(pos){
            case 1:
            gameBoard[0][2] = 'X';
            run();
                break;
            case 2:
            gameBoard[0][6] = 'X';
            run();
                break;
            case 3:
            gameBoard[0][10] = 'X';
            run();
                break;
            case 4:
            gameBoard[0][14] = 'X';
            run();
                break;
            case 5:
            gameBoard[0][18] = 'X';
            run();
                break;
            case 6:
            gameBoard[2][2] = 'X';
            run();
                break;
            case 7:
            gameBoard[2][6] = 'X';
            run();
                break;
            case 8:
            gameBoard[2][10] = 'X';
            run();
                break;
            case 9:
            gameBoard[2][14] = 'X';
            run();
                break;
            case 10:
            gameBoard[2][18] = 'X';
            run();
                break;
            case 11:
            gameBoard[4][2] = 'X';
            run();
                break;
            case 12:
            gameBoard[4][6] = 'X';
            run();
                break;
            case 13:
            gameBoard[4][10] = 'X';
            run();
                break;
            case 14:
            gameBoard[4][14] = 'X';
            run();
                break;
            case 15:
            gameBoard[4][18] = 'X';
            run();
                break;
            case 16:
            gameBoard[6][2] = 'X';
            run();
                break;
            case 17:
            gameBoard[6][6] = 'X';
            run();
                break;
            case 18:
            gameBoard[6][10] = 'X';
            run();
                break;
            case 19:
            gameBoard[6][14] = 'X';
            run();
                break;
            case 20:
            gameBoard[6][18] = 'X';
            run();
                break;
            case 21:
            gameBoard[8][2] = 'X';
            run();
                break;
            case 22:
            gameBoard[8][6] = 'X';
            run();
                break;
            case 23:
            gameBoard[8][10] = 'X';
            run();
                break;
            case 24:
            gameBoard[8][14] = 'X';
            run();
                break;
            case 25:
            gameBoard[8][18] = 'X';
            run();
                break;

        }
    }

    public static void main(String[] args){
        TicTacToe game = new TicTacToe();
        
    }
}

