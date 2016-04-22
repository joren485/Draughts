/**
 * Created by Joren on 20-Apr-16.
 */
public class Piece {

    enum PieceColor{
        Black,
        White;
    }

    private boolean captured = false;
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

    public void crown(){
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

    public Tuple getPosition(){
        return this.position;
    }

    public void setCaptured(boolean captured){
        this.captured = captured;
    }

    public void switchCaptured(){
        this.captured = !this.captured;
    }

    public boolean isCaptured(){
        return this.captured;
    }

}
