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


    public Tile(TileColor color, Piece p){
        this.piece = p;
        this.color = color;
    }

    public TileColor getColor(){
        return this.color;
    }

    public Piece getPiece(){
        return this.piece;
    }

    public void setPiece(Piece p){
        this.piece = new Piece(p);
    }

    public void setEmpty(){
        this.piece = null;
    }

    public boolean isEmpty(){
        return this.piece == null;
    }
}
