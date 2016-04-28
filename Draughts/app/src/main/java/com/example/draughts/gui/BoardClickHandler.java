package com.example.draughts.gui;

import android.graphics.Color;
import android.widget.TextView;

import com.example.draughts.game.Board;
import com.example.draughts.game.Piece;
import com.example.draughts.game.Position;

import java.util.List;

public class BoardClickHandler {
    private final Board board;
    private final BoardAdapter boardAdapter;
    private final TextView tvShowStatus;
    private final CapturedPiecesAdapter whiteCapturedPieces;
    private final CapturedPiecesAdapter blackCapturedPieces;

    private Position selectedPosition;

    public BoardClickHandler(Board b, BoardAdapter c, TextView tv, CapturedPiecesAdapter whitePieces, CapturedPiecesAdapter blackPieces) {
        board = b;
        boardAdapter = c;
        tvShowStatus = tv;
        whiteCapturedPieces = whitePieces;
        blackCapturedPieces = blackPieces;
    }

    public void click(Position pos) {
        // For usability, clicks on white squares are ignored.
        // After all, only black squares can ever hold pieces
        if (isWhitePos(pos)) {
            return;
        }

        // User first selects a position, and then moves the residing piece with the second click.
        if (selectedPosition == null || !board.isLegalMove(selectedPosition, pos)) {
            boardAdapter.deselectAll();
            if(board.isEmpty(pos) || board.getColorOf(pos) != board.getTurnColor()) {
                return;
            }
            selectedPosition = pos;
            boardAdapter.selectTile(pos);
            List<Position> moves = board.getAllLegalMovesFrom(pos);
            for (Position move : moves) {
                boardAdapter.selectTile(move);
            }
        }
        else {
            if (board.isLegalMove(selectedPosition, pos)) {
                board.makeMove(selectedPosition, pos);
                Piece captured = board.getCapturedPiece();
                if (captured != null) {
                    if (captured.getColor() == Piece.Color.White) {
                        if (captured.isKing()) {
                            whiteCapturedPieces.addKing();
                        } else {
                            whiteCapturedPieces.addPiece();
                        }
                        whiteCapturedPieces.notifyDataSetChanged();
                    } else {
                        if (captured.isKing()) {
                            blackCapturedPieces.addKing();
                        } else {
                            blackCapturedPieces.addPiece();
                        }
                        blackCapturedPieces.notifyDataSetChanged();
                    }
                }
                boardAdapter.updateAllTiles();
                //boardAdapter.deselectTile(selectedPosition);
                boardAdapter.deselectAll();
                selectedPosition = null;
            } else
            {
                //boardAdapter.deselectTile(selectedPosition);
                boardAdapter.updateAllTiles();
                boardAdapter.deselectAll();
                selectedPosition = null;
            }

            updateStatusText();
        }
    }

    private void updateStatusText() {
        Piece.Color playerColor = board.getTurnColor();

        if(board.isGameOver()) {
            if(board.isDraw()) {
                tvShowStatus.setTextColor(Color.BLUE);
                tvShowStatus.setText("DRAW");
            }
            else if (board.getWinnerColor() == Piece.Color.Black) {
                tvShowStatus.setTextColor(Color.BLACK);
                tvShowStatus.setText("BLACK WINS");
            }
            else {
                tvShowStatus.setTextColor(Color.WHITE);
                tvShowStatus.setText("WHITE WINS");
            }
        }
        else if(playerColor == Piece.Color.Black) {
            tvShowStatus.setTextColor(Color.BLACK);
            tvShowStatus.setText("BLACK");
        }
        else {
            tvShowStatus.setTextColor(Color.WHITE);
            tvShowStatus.setText("WHITE");
        }
    }

    private boolean isWhitePos(Position pos) {
        return (pos.x + pos.y) % 2 == 0;
    }
}
