import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

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

    public List<Tile> Moves(Piece piece){
        List<Tile> moves = new ArrayList<>();

        if (piece.getColor() == Piece.PieceColor.Black){
            Tuple[] move_dirs = {new Tuple(1,1), new Tuple(-1,1)};
        }

        else{
            Tuple[] move_dirs = {new Tuple(1,-1), new Tuple(-1,-1)};
        }

        Tile t;
        Tuple cor;
        for (Tuple move_dir: move_dirs){

            cor = new Tuple(piece.getX() + move_dir.x, piece.getY() + move_dir.y);

            if (cor.x < this.board_size && cor.x >= 0 && cor.y < this.board_size && cor.y >= 0){
                t = this.board[cor.x][cor.y];

                if(t.isEmpty())
                    moves.add(t);
            }
        }

        return moves;
    }

    public void movePiece(Piece p, Tile destTile){


        Tile from_tile = this.board[p.getX()][p.getY()];

        destTile.setPiece(p, new Tuple(p.getX(), p.getY()));
        from_tile.setEmpty();
    }


    @Override
    public String toString(){

        Tile temp;
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < this.board_size; y++){
            for (int x = 0; x < this.board_size; x++){
                temp = this.board[x][y];

                if (temp.isEmpty()) {
                    sb.append("   ");
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
