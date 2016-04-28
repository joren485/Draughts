package com.example.draughts.game;

public class Position {

    public final int x;
    public final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position t) {
        this(t.x, t.y);
    }

    public static Position getDirection(Position src, Position dest){

        return new Position((src.x < dest.x) ? 1 : -1, (src.y < dest.y) ? 1 : -1);
    }

    public static Position add(Position p, Position q) {
        return new Position(p.x + q.x, p.y + q.y);
    }

    @Override
    public boolean equals(Object other){
        if (other == null || other.getClass() != Position.class){
            return false;
        }

        Position other_position = (Position) other;

        return other_position.x == this.x && other_position.y == this.y;

    }

    @Override
    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }
}