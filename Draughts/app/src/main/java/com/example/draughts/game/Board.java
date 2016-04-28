package com.example.draughts.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board {

    private static final Position[] WHITE_PAWN_MOVES
            = {new Position(1,-1), new Position(-1,-1)};
    private static final Position[] BLACK_PAWN_MOVES
            = {new Position(1,1), new Position(-1,1)};
    private static final Position[] CAPTURE_MOVES
            = {new Position(1,1), new Position(1,-1), new Position(-1,1), new Position(-1,-1)};
    private static final Position[] KING_DIRECTIONS = CAPTURE_MOVES;


    private final int board_size;
    private final Piece[][] board;

    private int nr_white_pieces;
    private int nr_black_pieces;

    private boolean gameOver = false;
    private boolean gameDraw = false;

    private Piece.Color currentPlayer = Piece.Color.White;

    private MoveChain currentMoveChain;

    private Piece capturedPiece;

    public Board(int size){
        board_size = size;
        board = new Piece[board_size][board_size];

        nr_white_pieces = nr_black_pieces = (board_size / 2 ) * ((board_size / 2 ) - 1);

        for (int y = 0; y < board_size; y++){
            for (int x = 0; x < board_size; x++){

                if ((x + y) % 2 == 0) {
                    board[x][y] = null;
                }
                else{
                    if (y < (board_size/2) - 1){
                        board[x][y] = new Piece(Piece.Color.Black);
                    }
                    else if (y >= this.board_size - ((board_size/2) - 1)){
                        board[x][y] = new Piece(Piece.Color.White);
                    }
                    else {
                        board[x][y] = null;
                    }
                }
            }
        }

        setAllMoves();
    }

    /**
     * Move a piece from one position to another, possibly capturing a piece along the way,
     * and possibly making the piece into a king.
     * @param from the current position of the piece.
     * @param destination the position the piece will move to.
     */
    public void makeMove(Position from, Position destination){
        if(!isLegalMove(from, destination) || gameOver) {
            return;
        }

        if (currentMoveChain == null) {
            currentMoveChain = getPiece(from).getMoveChain();
        }

        Piece capturedPiece = movePiece(from, destination);
        currentMoveChain = currentMoveChain.getnextMovebySrc(destination);

        if (currentMoveChain.isLeaf()) {
            if (currentPlayer == Piece.Color.White && destination.y == 0
                    || currentPlayer == Piece.Color.Black && destination.y == (board_size - 1)) {
                getPiece(destination).crown();
            }

            currentMoveChain = null;
        }

        this.capturedPiece = capturedPiece;
        if (capturedPiece != null) {
            if (currentPlayer == Piece.Color.Black){
                nr_white_pieces--;
            }
            else{
                nr_black_pieces--;
            }
        }

        if (currentMoveChain == null) {
            killCapturedPieces();

            currentPlayer = (currentPlayer == Piece.Color.White)
                    ? Piece.Color.Black : Piece.Color.White;

            gameOver = setAllMoves();

            if(gameOver) {
                gameDraw = currentPlayer == Piece.Color.Black ? nr_black_pieces != 0 : nr_white_pieces != 0;
            }
        }
    }

    /**
     * Find all possible moves that can be made from a position.
     * @param pos the position of the piece.
     * @return A list of all the positions that can be moved to from this position.
     */
    public List<Position> getAllLegalMovesFrom(Position pos){
        if (this.isEmpty(pos) || this.getPiece(pos).getColor() != this.currentPlayer){
            return new LinkedList<>();
        }

        if (currentMoveChain != null) {
            if (!currentMoveChain.getSrc().equals(pos)) {
                return new LinkedList<>();
            }

            return currentMoveChain.getNextMoveChains();
        }

        return getPiece(pos).getMoveChain().getNextMoveChains();
    }

    /**
     * Get the size of the board
     * @return the number of columns (and rows) of this board.
     */
    public int getBoardDimensions() {
        return board_size;
    }

    /**
     * Check whether the game is over.
     * @return true if the game has stopped, for whatever reason (draw, whitewin, blackwin).
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Check if the game ended on a draw.
     * @return true if the game was a draw.
     */
    public boolean isDraw() {
        return gameDraw;
    }

    /**
     * Check for the color of a piece.
     * @param pos the position of the piece.
     * @return the color of the piece at pos, and null, if pos holds no piece.
     */
    public Piece.Color getColorOf(Position pos) {
        Piece p = getPiece(pos);
        return p != null ? getPiece(pos).getColor() : null;
    }

    /**
     * Check whose turn it currently is to make a move.
     * @return the Piece.Color of the player whose move it it.
     */
    public Piece.Color getTurnColor() {
        return currentPlayer;
    }

    /**
     * Get the color of the player that won the game, and null if the game isn't over yet.
     * @return Piece.Color.White if white won, and Piece.Color.Black otherwise.
     */
    public Piece.Color getWinnerColor() {
        if(!gameOver) {
            return null;
        }

        return currentPlayer == Piece.Color.White ? Piece.Color.Black : Piece.Color.White;
    }

    /**
     * Check if a square is empty.
     * @param pos the position of the piece.
     * @return Whether or not there is a piece currently on pos.
     */
    public boolean isEmpty(Position pos){
        return getPiece(pos) == null;
    }

    /**
     * Check whether a move (possibly a capture) is allowed in the current board state.
     * @param from the position of the piece to move.
     * @param to the position to move the piece to.
     * @return true if this move is legal.
     */
    public boolean isLegalMove(Position from, Position to) {
        return isOnBoard(from) && isOnBoard(to) && !isEmpty(from) && getAllLegalMovesFrom(from).contains(to);
    }

    /**
     * Check if a square contains a piece that has been captured (i.e. it will die after the
     * current capture chain has been finished, but it's still currently treated as an obstacle)
     * @param pos the position of the piece.
     * @return true if the position contains a piece and that piece is captured.
     */
    public boolean isCapturedPosition(Position pos) {
        return !isEmpty(pos) && getPiece(pos).isCaptured();
    }

    /**
     * Check if the piece on a position holds a king.
     * @param pos position on the board
     * @return true if the position contains a king
     */
    public boolean holdsKing(Position pos) {
        return getPiece(pos).isKing();
    }

    /**
     * Check if the position is on the board.
     * @param position position on the board
     * @return true if the position is on the board
     */
    private boolean isOnBoard(Position position) {
        return position.x < this.board_size && position.y < this.board_size && position.x >= 0 && position.y >= 0;
    }


    /*
     * PRIVATE METHODS FOR CALCULATING WHICH MOVES ARE VIABLE AND WHEN, AND THE LENGTH OF
     * CAPTURE CHAINS.
     */
    private Piece getPiece(Position pos){
        return board[pos.x][pos.y];
    }

    private void setPiece(Piece p, Position cor){
        board[cor.x][cor.y] = p;
    }

    /**
     * MoveChain a piece to a location. It is assumed that the move is a valid one,
     * there are methods for all valid moves and captures.
     */
    private Piece movePiece(Position src, Position dest) {
        Piece p = getPiece(src);
        setPiece(p, dest);
        board[src.x][src.y] = null;

        Position move_direction = Position.getDirection(src, dest);
        Position possible_piece = Position.add(src, move_direction);

        while (this.isOnBoard(possible_piece) && this.isEmpty(possible_piece)){
            possible_piece = Position.add(possible_piece, move_direction);
        }

        if (isOnBoard(possible_piece) && !isEmpty(possible_piece) && !possible_piece.equals(dest)){
            Piece capture_piece = this.getPiece(possible_piece);

            if (capture_piece.getColor() != p.getColor()) {
                capture_piece.switchCaptured();
            }

            return capture_piece;
        }

        return null;
    }

    private List<Position> getAllMoves(Position from) {

        if(this.isEmpty(from)) {
            return new LinkedList<>();
        }

        Piece piece = this.getPiece(from);

        if(piece.isKing()) {
            return this.getAllMovesKing(from);
        }

        else {

            return this.getAllMovesMan(from, piece.getColor());
        }
    }

    private List<Position> getAllMovesMan(Position from, Piece.Color color){

        Position[] move_directions = (color == Piece.Color.White) ? WHITE_PAWN_MOVES : BLACK_PAWN_MOVES;

        List<Position> moves = new LinkedList<>();

        for(Position move_direction : move_directions) {
            final Position dest = Position.add(from, move_direction);

            if(this.isOnBoard(dest) && this.isEmpty(dest)) {
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
    private List<Position> getAllMovesKing(Position from) {
        List<Position> moves = new LinkedList<>();

        for(Position direction : KING_DIRECTIONS) {
            Position dest = Position.add(from, direction);

            while(this.isOnBoard(dest) && this.isEmpty(dest)) {
                moves.add(dest);
                dest = Position.add(dest, direction);
            }
        }

        return moves;
    }

    /**
     * Finds all the places a piece can move to while capturing another piece.
     * @param from the position of the piece to move from.
     * @return A list containing all moves towards all position positions from here.
     */
    private List<Position> getAllCaptures(Position from){

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

    private List<Position> getAllCapturesMan(Position from, Piece.Color color){

        List<Position> moves = new LinkedList<>();

        for(Position move_direction : CAPTURE_MOVES) {
            Position capture_position = Position.add(from, move_direction);

            if(isOnBoard(capture_position) && !isEmpty(capture_position)){

                Piece capture_piece = getPiece(capture_position);
                Position dest_position = Position.add(capture_position, move_direction);

                if(isOnBoard(dest_position) && isEmpty(dest_position)
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
    private List<Position> getAllCapturesKing(Position from, Piece.Color color) {

        List<Position> possibleCaptures = new ArrayList<>();

        for (Position move_direction : KING_DIRECTIONS) {
            Position capture_position = Position.add(from, move_direction);

            while(this.isOnBoard(capture_position)
                    && this.isEmpty(capture_position)) {

                capture_position = Position.add(capture_position, move_direction);
            }

            if(this.isOnBoard(capture_position)) {

                Piece capture_piece = this.getPiece(capture_position);

                if (capture_piece.getColor() != color
                        && !capture_piece.isCaptured()){

                    Position dest_position = Position.add(capture_position, move_direction);

                    while(this.isOnBoard(dest_position) && this.isEmpty(dest_position)) {
                        possibleCaptures.add(dest_position);
                        dest_position = Position.add(dest_position, move_direction);
                    }
                }
            }
        }
        return possibleCaptures;
    }

    private void getCaptureChain(Position src, MoveChain node){

        List<Position> captures = getAllCaptures(src);

        for (Position dest : captures){
            movePiece(src, dest);

            MoveChain branch = new MoveChain(dest);
            getCaptureChain(dest, branch);

            node.addMove(branch);
            movePiece(dest, src);
        }
    }

    private MoveChain getPossibleMoves(Position src){
        List<Position> captures = getAllCaptures(src);
        MoveChain root;

        if (captures.size() == 0) {
            root = new MoveChain(src, false);
            for(Position position : getAllMoves(src)){
                root.addMove(new MoveChain(position));
            }
            return root;
        }

        root = new MoveChain(src, true);
        getCaptureChain(src, root);

        return root;
    }

    /**
     *
     * @return
     */
    private boolean setAllMoves() {

        int max_height = 0;
        boolean found_capture = false;

        for (int y = 0; y < board_size; y++) {
            for (int x = 0; x < board_size; x++) {
                Position pos = new Position(x, y);

                if (!this.isEmpty(pos)) {
                    Piece p = getPiece(pos);

                    if (p.getColor() == currentPlayer) {
                        p.setMoveChain(getPossibleMoves(pos));

                        found_capture = found_capture || p.getMoveChain().isCapture();
                        max_height = Math.max(max_height, p.getMoveChain().getHeight());
                    }
                }
            }
        }

        if (found_capture) {
            for (int y = 0; y < board_size; y++) {
                for (int x = 0; x < board_size; x++) {
                    Position pos = new Position(x, y);

                    if (!this.isEmpty(pos)) {
                        Piece p = getPiece(pos);

                        if (p.getColor() == currentPlayer) {
                            if ((p.getMoveChain().getHeight() != max_height) || ! p.getMoveChain().isCapture()) {
                                p.setMoveChain(new MoveChain(pos));
                            }
                        }
                    }
                }
            }
        }

        return max_height <= 1;
    }

    private void killCapturedPieces(){
        for (int y = 0; y < this.board_size; y++){
            for (int x = 0; x < this.board_size; x++) {
                Position pos = new Position(x, y);

                if (!isEmpty(pos)){
                    Piece p = getPiece(pos);

                    if(p.isCaptured()){
                        setPiece(null, pos);
                    }
                }
            }
        }
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
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

                else if (temp.getColor() == Piece.Color.White){
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
