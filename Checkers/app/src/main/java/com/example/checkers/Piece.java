package com.example.checkers;

/**
 * Created by Joren on 20-Apr-16.
 */
public class Piece {

    enum PieceColor{
        Black,
        White;
    }

    private boolean captured = false;
    private boolean king = false;

    private final PieceColor color;

    public Piece(PieceColor color, boolean king){
        this.color = color;
        this.king = king;
    }

    public Piece(PieceColor color){
        this(color, false);
    }

    /**
     * Copies a piece p.
     * @param p the piece to be copied.
     */
    public Piece(Piece p){
        this(p.getColor(), p.isKing());
    }

    public boolean isKing(){
        return this.king;
    }

    public void crown(){
        this.king = true;
    }

    public PieceColor getColor(){
        return this.color;
    }

    public void setCaptured(boolean captured){
        this.captured = captured;
    }

    public void switchCaptured(){
        this.captured = !this.captured;
    }

    public boolean isCaptured(){
        return this.captured;
    }

}
