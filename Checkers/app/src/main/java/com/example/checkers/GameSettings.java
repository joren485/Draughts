package com.example.checkers;

public class GameSettings {
    /**
     * The number of rows (and columns) on the checkerboard.
     */
    private int boardSize = -2;

    private static GameSettings ourInstance = new GameSettings();

    public void setBoardSize(int size) {
        boardSize = size;
    }

    public int getBoardSize() {
        return boardSize;
    }



    public static GameSettings getInstance() {
        return ourInstance;
    }

    /**
     * Reset the game settings.
     */
    public static void reset() {
        ourInstance = new GameSettings();
    }
}
