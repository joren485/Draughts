/**
 * Created by Joren on 20-Apr-16.
 */
public class Piece {

    enum PieceColor{
        Black,
        White;
    }

    private boolean King = false;
    private final PieceColor color;

    private Tuple position;

    public Piece(PieceColor color, Tuple pos, boolean King){
        this.color = color;
        this.King = King;
        this.position = pos;
    }

    public Piece(PieceColor color, Tuple pos){
        this(color, pos, false);
    }

    /**
     * Copies a piece p.
     * @param p the piece to be copied.
     */
    public Piece(Piece p, Tuple pos){
        this(p.getColor(), pos, p.isKing());
    }

    public boolean isKing(){
        return this.King;
    }

    public void Crown(){
        this.King = true;
    }

    public PieceColor getColor(){
        return this.color;
    }

    public int getX(){
        return this.position.x;
    }

    public int getY(){
        return this.position.y;
    }

}
