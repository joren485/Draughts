import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by Joren on 20-Apr-16.
 */
public class Board {

    private final Tuple[] KINGMOVES;

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

        this.KINGMOVES = new Tuple[this.board_size * this.board_size];
        int index = 0;
        for(int x = -1*(this.board_size - 1); x < this.board_size; x++){
            for(int y = -1*(this.board_size - 1); y < this.board_size; y++){
                this.KINGMOVES[index] = new Tuple(x, y);
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

    public List<Tile> getPossibleCaptures(Piece piece){
        List<Tile> captures = new ArrayList<>();

        Tuple[] moves;

        if (piece.isKing()){
            moves = this.KINGMOVES;
        }

        else{
            moves = new Tuple[4];
            moves[0] = new Tuple(1,1);
            moves[1] = new Tuple(-1,1);
            moves[2] = new Tuple(1,-1);
            moves[3] = new Tuple(-1,-1);
        }

        for (Tuple move_dir: moves) {

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

        Tuple[] move_dirs;
        if (piece.isKing()){
            move_dirs = this.KINGMOVES;
        }
        else{
            move_dirs = new Tuple[2];
            if (piece.getColor() == Piece.PieceColor.Black){
                move_dirs[0] = new Tuple(1,1);
                move_dirs[1] = new Tuple(-1,1);
            }

            else{
                move_dirs[0] = new Tuple(1,-1);
                move_dirs[1] = new Tuple(-1,-1);
            }
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

    public Move getLegalMoves(Piece piece, Move node){

        List<Tile> captures = getPossibleCaptures(piece);

        if (captures.size() == 0){

            for(Tile t: getPossibleMoves(piece)){
                node.addMove(new Move(t.getPosition()));
            }
        }

        ArrayList<Move> moves = new ArrayList<>();

        for(Tile dest: captures){

            Tuple src = piece.getPosition();
            this.movePiece(src, dest.getPosition());

            moves.add(getLegalMoves(piece, node));
            this.movePiece(dest.getPosition(), src);
        }

        int maxium_height = 0;

        for (Move m : moves){
            int height = m.getHeight();
            if (height > maxium_height){
                maxium_height = height;
            }
        }

        for (Move m : moves){
            if(m.getHeight() == maxium_height){
                node.addMove(m);
            }
        }

        return node;
    }

    public Move getLegalMoves(Piece piece){
        return getLegalMoves(piece, new Move(piece.getPosition()));
    }

    /**
     * Move a piece to a location. It is assumed that the move is a valid one,
     * there are methods for all valid moves and captures.
     */
    public void movePiece(Tile srcTile, Tile destTile){

        Piece p = srcTile.getPiece();
        destTile.setPiece(p);
        p.setPosition(destTile.getPosition());
        srcTile.setEmpty();


        //TODO This code needs to be implemented after a capture chain
        //For removing pieces
        /*
            if (abs(destTile.getX() - srcTile.getX()) > 1 && abs(destTile.getY() - srcTile.getY()) > 1){
                Tuple dir = Tuple.getDirection(srcTile.getPosition(), destTile.getPosition());

                Tile new_empty_tile = this.getTile(new Tuple(destTile.getX() + dir.x, destTile.getY() + dir.y));
                new_empty_tile.setEmpty();
            }
         */


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
