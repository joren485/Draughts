package com.example.draughts.game;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class MoveChain {

    private final Position srcCor;
    private final List<MoveChain> nextMoveChains = new LinkedList<>();

    private int height = 0;

    public boolean capture;

    public MoveChain(Position src, boolean isCapture){
        this.srcCor = src;
        this.capture = isCapture;
    }

    public MoveChain(Position src){
        this(src, true);
    }

    public MoveChain getnextMovebySrc(Position src){
        for(MoveChain m : nextMoveChains){
            if(m.getSrc().equals(src)){
                return m;
            }
        }

        return null;
    }

    public boolean isCapture() {
        return capture;
    }

    public void addMove(MoveChain m){

        ListIterator<MoveChain> iterator = nextMoveChains.listIterator();

        while(iterator.hasNext()){
            MoveChain next = iterator.next();

            if (next.getHeight() > m.getHeight()){
                return;
            } else if (next.getHeight() < m.getHeight()){
                iterator.remove();
            }
        }

        this.nextMoveChains.add(m);
    }

    public boolean isLeaf(){
        return nextMoveChains.size() == 0;
    }

    public Position getSrc(){
        return srcCor;
    }

    public int getHeight(){
        if (height == 0){
            if (this.isLeaf()){
                return 1;
            }

            int max_height = 0;

            for (MoveChain nextmove : nextMoveChains){
                int height = nextmove.getHeight();
                if (height > max_height){
                    max_height = height;
                }
            }

            this.height = 1 + max_height;

        }
        return this.height;
    }

    public List<Position> getNextMoveChains(){
        List<Position> pos_nextmoves = new LinkedList<>();

        for(MoveChain m : this.nextMoveChains){
            pos_nextmoves.add(m.getSrc());
        }

        return pos_nextmoves;
    }

    public String toString(){
        return "Move from: " + srcCor;
    }

}
