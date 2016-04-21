import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Joren on 21-Apr-16.
 */
public class Move {

    private final Tuple srcCor;
    private final List<Move> nextMoves = new LinkedList<>();


    public Move(Tuple src){
        this.srcCor = src;
    }

    public void addMove(Move m){
        this.nextMoves.add(m);

    }

    public List<Move> getNextMoves(){
        return this.nextMoves;
    }

    public boolean isLeaf(){
        return this.nextMoves.size() == 0;
    }

    public Tuple getSrc(){
        return this.srcCor;
    }

}
