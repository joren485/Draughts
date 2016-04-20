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
                    this.board[x][y] = new Tile(Tile.TileColor.Beige, null);
                }
                else{

                    if (y < (this.board_size/2) - 1){
                        this.board[x][y] = new Tile(Tile.TileColor.Brown, new Piece(Piece.PieceColor.Black));
                    }
                    else if (y >= this.board_size - ((this.board_size/2) - 1)){
                        this.board[x][y] = new Tile(Tile.TileColor.Brown, new Piece(Piece.PieceColor.White));
                    }
                    else {
                        this.board[x][y] = new Tile(Tile.TileColor.Brown, null);
                    }
                }
            }
        }
    }

    public Board(){

        // Default size is 10
        this(10);
    }

    public boolean legalmove(Tuple from, Tuple to){

        Tile to_tile = this.board[to.x][to.y];

        if(!to_tile.isEmpty()){
            return false;
        }

        Tile from_tile = this.board[from.x][from.y];


        if (to_tile.getPiece().isKing()){

        }

        else{

        }

        return true;
    }

    public List<Tuple> allLegalMoves(Tuple from){
        List<Tuple> moves = new ArrayList<>();




        return moves;
    }

    public void movePiece(Tuple from, Tuple to){

        Tile from_tile = this.board[from.x][from.y];
        Tile to_tile = this.board[to.x][to.y];

        to_tile.setPiece(from_tile.getPiece());
        from_tile.setEmpty();

        if ((to_tile.getPiece().getColor() == Piece.PieceColor.White && to.y == this.board_size - 1) || (to_tile.getPiece().getColor() == Piece.PieceColor.Black && to.y == 0)){
            to_tile.getPiece().makeKing();
        }
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
