import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by Joren on 20-Apr-16.
 */
public class Board {

    private final int board_size;
    private final Tile[][] board;

    public Board(int size){
        this.board_size = size;
        this.board = new Tile[this.board_size][this.board_size];

        for (int y = 0; y < this.board_size; y++){
            for (int x = 0; x < this.board_size; x++){

                if ((x + y) % 2 == 0) {
                    this.board[x][y] = new Tile(Tile.TileColor.Beige, null, new Tuple(x,y));
                }
                else{
                    if (y < (this.board_size/2) - 1){
                        this.board[x][y] = new Tile(Tile.TileColor.Brown, new Piece(Piece.PieceColor.Black, new Tuple(x, y)), new Tuple(x,y));
                    }
                    else if (y >= this.board_size - ((this.board_size/2) - 1)){
                        this.board[x][y] = new Tile(Tile.TileColor.Brown, new Piece(Piece.PieceColor.White, new Tuple(x, y)), new Tuple(x,y));
                    }
                    else {
                        this.board[x][y] = new Tile(Tile.TileColor.Brown, null, new Tuple(x,y));
                    }
                }
            }
        }
    }

    public Board(){

        // Default size is 10
        this(10);
    }
    public Tile getTile(Tuple pos){
        return this.board[pos.x][pos.y];
    }

    private List<Tile> getPossibleCaptures(Piece piece){
        List<Tile> captures = new ArrayList<>();
        Tuple[] move_dirs = {new Tuple(1,1), new Tuple(-1,1), new Tuple(1,-1), new Tuple(-1,-1)};

        for (Tuple move_dir: move_dirs) {

            Tuple cor = new Tuple(piece.getX() + move_dir.x, piece.getY() + move_dir.y);
            Tuple cor_behind = new Tuple(piece.getX() + 2* move_dir.x, piece.getY() + 2* move_dir.y);

            if (cor.x < this.board_size && cor.x >= 0 && cor.y < this.board_size && cor.y >= 0 &&
                    cor_behind.x < this.board_size && cor_behind.x >= 0 && cor_behind.y < this.board_size && cor_behind.y >= 0){

                Tile t = this.getTile(cor);
                Tile t_behind = getTile(cor_behind);

                if (t.getPiece().getColor() != piece.getColor() && t_behind.isEmpty()){
                    captures.add(t_behind);
                }
            }
        }
            return captures;
    }

    private List<Tile> getPossibleMoves(Piece piece){
        List<Tile> moves = new ArrayList<>();

        Tuple[] move_dirs = new Tuple[2];
        if (piece.getColor() == Piece.PieceColor.Black){
            move_dirs[0] = new Tuple(1,1);
            move_dirs[1] = new Tuple(-1,1);
        }

        else{
            move_dirs[0] = new Tuple(1,-1);
            move_dirs[1] = new Tuple(-1,-1);
        }

        for (Tuple move_dir: move_dirs){

            Tuple cor = new Tuple(piece.getX() + move_dir.x, piece.getY() + move_dir.y);

            if (cor.x < this.board_size && cor.x >= 0 && cor.y < this.board_size && cor.y >= 0){
                Tile t = getTile(cor);

                if(t.isEmpty())
                    moves.add(t);
            }
        }

        return moves;
    }

    public Move getLegalMoves(Piece piece){
        List<Tile> caputeres = getPossibleCaptures(piece);

        // If no captures are possible, return a
        if (caputeres.size() == 0){
            Move end = new Move(piece.getPosition());
            for(Tile t: getPossibleMoves(piece)){
                end.addMove(new Move(t.getPosition()));
            }
            return end;
        }

    }

    /**
     * Move a piece to a location. It is assumed that the move is a valid one,
     * there are methods for all valid moves and captures.
     */
    public void movePiece(Tile srcTile, Tile destTile){

        Piece p = srcTile.getPiece();
        destTile.setPiece(p);
        srcTile.setEmpty();

        if (abs(destTile.getX() - srcTile.getX()) > 1 && abs(destTile.getY() - srcTile.getY()) > 1){
            Tuple dir = Tuple.getDirection(srcTile.getPosition(), destTile.getPosition());

            Tile new_empty_tile = this.getTile(new Tuple(destTile.getX() + dir.x, destTile.getY() + dir.y));
        }
    }

    public void movePiece(Tuple srcCor, Tuple destCor){
        this.movePiece(this.getTile(srcCor), this.getTile(destCor));
    }

    @Override
    public String toString(){

        Tile temp;
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < this.board_size; y++){
            for (int x = 0; x < this.board_size; x++){
                temp = this.board[x][y];

                if (temp.isEmpty()) {
                    sb.append(" * ");
                }

                else if (temp.getPiece().getColor() == Piece.PieceColor.White){
                    sb.append(" W ");
                }
                else {
                    sb.append(" B ");
                }

            }
            sb.append('\n');

        }
        return sb.toString();
    }
}
