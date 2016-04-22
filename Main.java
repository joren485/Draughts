import java.util.List;

/**
 * Created by Joren on 20-Apr-16.
 */
public class Main {
    public static void main(String[] args) {

        Board b = new Board(10);

        b.movePiece(new Tuple(1, 6), new Tuple(2, 5));
        b.movePiece(new Tuple(3, 6), new Tuple(4, 5));

        b.movePiece(new Tuple(0, 3), new Tuple(1, 4));

        //b.movePiece(new Tuple(1, 4), new Tuple(3, 6));

        System.out.println(b);

        Move root = b.getLegalMoves(b.getPiece(new Tuple(1, 4)));

        System.out.println(root.getNextMoves());


    }
}
