package com.example.checkers;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Joren on 21-Apr-16.
 */
public class Move {

    private final Tuple srcCor;
    private final List<Move> nextMoves = new LinkedList<>();

    private int height = 0;

    public Move(Tuple src){
        this.srcCor = src;
    }

    public void addMove(Move m){

        ListIterator<Move> iter = this.nextMoves.listIterator();

        while(iter.hasNext()){
            Move next = iter.next();

            if (next.getHeight() > this.getHeight()){
                return;
            }else if (next.getHeight() < this.getHeight()){
                iter.remove();
            }
        }
        this.nextMoves.add(m);
    }

    public boolean isLeaf(){
        return this.nextMoves.size() == 0;
    }

    public Tuple getSrc(){
        return this.srcCor;
    }


    public int getHeight(){
        if (this.height == 0){
            if (this.isLeaf()){
                return 1;
            }

            int max_height = 0;

            for (Move nextmove : this.nextMoves){
                int height = nextmove.getHeight();
                if (height > max_height){
                    max_height = height;
                }
            }

            this.height = 1 + max_height;

        }
        return this.height;
    }

    public String toString(){
        return this.srcCor.toString();
    }

}
