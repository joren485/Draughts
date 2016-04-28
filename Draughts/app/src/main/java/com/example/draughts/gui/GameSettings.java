package com.example.draughts.gui;

import android.content.Context;

public class GameSettings {
    /**
     * The number of rows (and columns) on the checkerboard.
     */
    private int boardSize = -1;

    // for bugtesting:
    public Context context;

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
