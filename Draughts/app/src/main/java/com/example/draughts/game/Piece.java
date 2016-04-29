package com.example.draughts.game;

public class Piece {

    public enum Color {
        Black,
        White;
    }

    private boolean captured = false;
    private boolean king = false;
    private MoveTree moveTree;

    private final Color color;

    public Piece(Color color, boolean king){
        this.color = color;
        this.king = king;
    }

    public Piece(Color color){
        this(color, false);
    }

    /**
     * Copies a piece p.
     * @param p the piece to be copied.
     */
    public Piece(Piece p){

        this.color = p.getColor();
        this.king = p.isKing();
        this.captured = p.captured;
        this.moveTree = p.getMoveTree();
    }

    public boolean isKing(){
        return this.king;
    }

    public void crown(){
        this.king = true;
    }

    public Color getColor(){
        return this.color;
    }

    public void switchCaptured(){
        this.captured = !this.captured;
    }

    public boolean isCaptured(){
        return this.captured;
    }

    public void setMoveTree(MoveTree moveTree) {
        this.moveTree = moveTree;
    }

    public MoveTree getMoveTree() {
        return this.moveTree;
    }


}
