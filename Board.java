import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Joren on 20-Apr-16.
 */
public class Board {

    private static final Tuple[] WHITE_PAWN_MOVES
            = {new Tuple(1,-1), new Tuple(-1,-1)};
    private static final Tuple[] BLACK_PAWN_MOVES
            = {new Tuple(1,1), new Tuple(-1,1)};
    private static final Tuple[] CAPTURE_MOVES
            = {new Tuple(1,1), new Tuple(1,-1), new Tuple(-1,1), new Tuple(-1,-1)};
    private static final Tuple[] KING_DIRECTIONS = CAPTURE_MOVES;


    private final int board_size;
    private final Piece[][] board;

    public Board(int size){
        this.board_size = size;
        this.board = new Piece[this.board_size][this.board_size];

        for (int y = 0; y < this.board_size; y++){
            for (int x = 0; x < this.board_size; x++){

                if ((x + y) % 2 == 0) {
                    this.board[x][y] = null;
                }
                else{
                    if (y < (this.board_size/2) - 1){
                        this.board[x][y] = new Piece(Piece.PieceColor.Black);
                    }
                    else if (y >= this.board_size - ((this.board_size/2) - 1)){
                        this.board[x][y] = new Piece(Piece.PieceColor.White);
                    }
                    else {
                        this.board[x][y] = null;
                    }
                }
            }
        }
    }

    public Board(){

        // Default size is 10
        this(10);
    }
    public Piece getPiece(Tuple pos){
        return this.board[pos.x][pos.y];
    }

    public void setPiece(Piece p, Tuple cor){
        this.board[cor.x][cor.y] = new Piece(p);
    }

    public boolean isEmpty(Tuple cor){
        return this.board[cor.x][cor.y] == null;
    }

    public boolean getPosOnBoard(Tuple position) {
        return position.x < this.board_size && position.y < this.board_size && position.x >= 0 && position.y >= 0;
    }

    /**
     * Move a piece to a location. It is assumed that the move is a valid one,
     * there are methods for all valid moves and captures.
     */
    public void movePiece(Tuple src, Tuple dest){
        Piece p = this.getPiece(src);
        this.setPiece(p, dest);
        this.board[src.x][src.y] = null;

        Tuple move_direction = Tuple.getDirection(src, dest);
        Tuple possible_piece = Tuple.add(src, move_direction);

        while (this.getPosOnBoard(possible_piece) && this.isEmpty(possible_piece)){
            possible_piece = Tuple.add(src, move_direction);
        }

        if (this.getPosOnBoard(possible_piece) && !this.isEmpty(possible_piece)){
            Piece capture_piece = this.getPiece(possible_piece);

            if (capture_piece.getColor() != p.getColor()){
                capture_piece.switchCaptured();
            }
        }
    }

    private List<Tuple> getAllMoves(Tuple from) {

        if(this.isEmpty(from)) {
            return new LinkedList<>();
        }

        Piece piece = this.getPiece(from);

        if(piece.isKing()) {
            return this.getAllMovesKing(from);
        }

        else{

            return this.getAllMovesMan(from, piece.getColor());
        }
    }

    private List<Tuple> getAllMovesMan(Tuple from, Piece.PieceColor color){

        Tuple[] move_directions = (color == Piece.PieceColor.White) ? WHITE_PAWN_MOVES : BLACK_PAWN_MOVES;

        List<Tuple> moves = new LinkedList<>();

        for(Tuple move_direction : move_directions) {
            final Tuple dest = Tuple.add(from, move_direction);
            if(this.getPosOnBoard(dest) && this.isEmpty(dest)) {
                moves.add(dest);
            }
        }

        return moves;
    }

    /**
     * Finds all the places a King can move without capturing another piece.
     * Has been bug tested.
     * @param from the position of the piece to move from.
     * @return A list containing all moves towards all position positions from here.
     */
    private List<Tuple> getAllMovesKing(Tuple from) {
        List<Tuple> moves = new LinkedList<>();

        for(Tuple direction : KING_DIRECTIONS) {
            Tuple dest = Tuple.add(from, direction);

            while(this.getPosOnBoard(dest) && this.isEmpty(dest)) {
                moves.add(dest);
                dest = Tuple.add(dest, direction);
            }
        }

        return moves;
    }

    /**
     * Finds all the places a piece can move to while capturing another piece.
     * @param from the position of the piece to move from.
     * @return A list containing all moves towards all position positions from here.
     */
    private List<Tuple> getAllCaptures(Tuple from){

        if(this.isEmpty(from)) {
            return new ArrayList<>();
        }

        Piece piece = this.getPiece(from);

        if(piece.isKing()) {
            return getAllCapturesKing(from, piece.getColor());
        }

        else{
            return getAllCapturesMan(from, piece.getColor());
        }
    }

    private List<Tuple> getAllCapturesMan(Tuple from, Piece.PieceColor color){

        List<Tuple> moves = new LinkedList<>();

        for(Tuple move_direction : CAPTURE_MOVES) {
            Tuple capture_position = Tuple.add(from, move_direction);

            if(this.getPosOnBoard(capture_position)){

                Piece capture_piece = this.getPiece(capture_position);
                Tuple dest_position = Tuple.add(capture_position, move_direction);

                if(this.getPosOnBoard(dest_position)
                        && this.isEmpty(dest_position)
                        && capture_piece.getColor() != color
                        && !capture_piece.isCaptured()){

                    moves.add(dest_position);
                }
            }
        }
        return moves;
    }

    /**
     * Finds all the places a King can move to while capturing another piece.
     * @param from the position of the piece to move from.
     * @return A list containing all moves towards all position positions from here.
     */
    private List<Tuple> getAllCapturesKing(Tuple from, Piece.PieceColor color) {

        List<Tuple> possibleCaptures = new ArrayList<>();

        for (Tuple move_direction : KING_DIRECTIONS) {
            Tuple capture_position = Tuple.add(from, move_direction);

            while(this.getPosOnBoard(capture_position)
                    && this.isEmpty(capture_position)) {

                capture_position = Tuple.add(capture_position, move_direction);
            }

            if(this.getPosOnBoard(capture_position)) {

                Piece capture_piece = this.getPiece(capture_position);

                if (capture_piece.getColor() != color
                        && !capture_piece.isCaptured()){

                    Tuple dest_position = Tuple.add(capture_position, move_direction);

                    while(this.getPosOnBoard(dest_position) && this.isEmpty(dest_position)) {
                        possibleCaptures.add(dest_position);
                        dest_position = Tuple.add(dest_position, move_direction);
                    }
                }
            }
        }
        return possibleCaptures;
    }

    private void getCaptureChain(Tuple src, Move node){

        List<Tuple> captures = getAllCaptures(src);

        for (Tuple dest : captures){
            this.movePiece(src, dest);

            Move branch = new Move(dest);
            getCaptureChain(dest, branch);

            node.addMove(branch);
            this.movePiece(dest, src);
        }
    }

    public Move getPossibleMoves(Tuple src){

        List<Tuple> captures = getAllCaptures(src);
        Move root = new Move(src);

        if (captures.size() == 0){
            for(Tuple position: getAllMoves(src)){
                root.addMove(new Move(position));
            }
            return root;
        }

        getCaptureChain(src, root);

        return root;
    }

    @Override
    public String toString(){

        Piece temp;
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < this.board_size; y++){
            for (int x = 0; x < this.board_size; x++){
                temp = this.board[x][y];

                if (temp == null) {
                    sb.append(" * ");
                }

                else if (temp.getColor() == Piece.PieceColor.White){
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
