package com.example.draughts.game;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Recursive data structure representing a tree, each node being a possible move of a piece.
 */
public class MoveTree {

    /**
     * The posit
     */
    private final Position srcPos;
    private final List<MoveTree> nextMoveTrees = new LinkedList<>();

    /**
     * Maximum depth of the tree from this point.
     */
    private int height = 0;

    /**
     * True if the current move is a capture.
     */
    public boolean capture;

    public MoveTree(Position src, boolean isCapture){
        srcPos = src;
        capture = isCapture;
    }

    public MoveTree(Position src){
        this(src, true);
    }

    /**
     * Finds a branch given position src.
     * @param src The src to search for.
     * @return The branch
     */
    public MoveTree getnextMovebySrc(Position src){
        for(MoveTree m : nextMoveTrees){
            if(m.getSrcPos().equals(src)){
                return m;
            }
        }

        return null;
    }

    public boolean isCapture() {
        return capture;
    }

    /**
     * Adds an moveTree to the current node as a branch.
     * Only keep the branch with the highest height.
     * @param m The move to be added
     */
    public void addMove(MoveTree m){

        ListIterator<MoveTree> iterator = nextMoveTrees.listIterator();

        while(iterator.hasNext()){
            MoveTree next = iterator.next();

            if (next.getHeight() > m.getHeight()){
                return;
            } else if (next.getHeight() < m.getHeight()){
                iterator.remove();
            }
        }

        this.nextMoveTrees.add(m);
    }

    public boolean isLeaf(){
        return nextMoveTrees.size() == 0;
    }

    public Position getSrcPos(){
        return srcPos;
    }

    /**
     * Caches the maximum depth of the tree. Calculates it once and caches it in an attribute afterwards.
     * @return The maximum depth of the tree starting from the current node.
     */
    public int getHeight(){
        if (height == 0){

            if (isLeaf()){
                return 1;
            }

            int max_depth = 0;

            for (MoveTree nextmove : nextMoveTrees){
                int height = nextmove.getHeight();
                if (height > max_depth){
                    max_depth = height;
                }
            }

            height = 1 + max_depth;

        }
        return height;
    }

    /**
     * @return List of positions of the branches of the current node.
     */
    public List<Position> getNextMoveTrees(){
        List<Position> pos_nextmoves = new LinkedList<>();

        for(MoveTree m : this.nextMoveTrees){
            pos_nextmoves.add(m.getSrcPos());
        }

        return pos_nextmoves;
    }

}
