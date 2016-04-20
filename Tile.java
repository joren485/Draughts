/**
 * Created by Joren on 20-Apr-16.
 */
public class Tile {

    enum TileColor{
        Brown,
        Beige;
    }

    private final TileColor color;
    private Piece piece;

    private final Tuple position;

    public Tile(TileColor color, Piece p, Tuple pos){
        this.piece = p;
        this.color = color;
        this.position = pos;
    }

    public TileColor getColor(){
        return this.color;
    }

    public Piece getPiece(){
        return this.piece;
    }

    public void setPiece(Piece p){
        this.piece = new Piece(p, this.position);
    }

    public void setEmpty(){
        this.piece = null;
    }

    public boolean isEmpty(){
        return this.piece == null;
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

    @Override
    public String toString(){
        return "( " + this.position.x + ", " + this.position.y + " )";
    }
}
