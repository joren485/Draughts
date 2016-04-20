/**
 * Created by Joren on 20-Apr-16.
 */
public class Main {
    public static void main(String[] args) {

        Board b = new Board(10);

        b.movePiece(new Tuple(0, 3), new Tuple(1, 4));

        System.out.println(b);
    }
}
