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

    public Piece(PieceColor color, boolean King){
        this.color = color;
        this.King = King;
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
        return this.King;
    }

    public void makeKing(){
        this.King = true;
    }

    public PieceColor getColor(){
        return this.color;
    }

}
