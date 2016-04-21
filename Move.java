import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Joren on 21-Apr-16.
 */
public class Move {

    private final Tuple srcCor;
    private final List<Move> nextMoves;


    public Move(Tuple src){
        this.srcCor = src;
        this.nextMoves = new LinkedList<>();
    }


    public void addMove(Move m){
        this.nextMoves.add(m);

    }

}
