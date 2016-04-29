package com.example.draughts.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.draughts.game.*;

import com.example.draughts.R;

public class GameActivity extends AppCompatActivity {
//    private static final int BROWN = R.drawable.brown;
//    private static final int BEIGE = R.drawable.beige;

    private int boardSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        boardSize = intent.getIntExtra("boardSize", 10);

        final Board board = new Board(boardSize);

        final GridView gvChessBoard = (GridView) findViewById(R.id.gvChessBoard);
        final BoardAdapter gaSquares = new BoardAdapter(this, board);
        final TextView tvGameStatus = (TextView) findViewById(R.id.tvGameStatus);

        final GridView gvWhitePieces = (GridView) findViewById(R.id.whitePieces);
        final GridView gvBlackPieces = (GridView) findViewById(R.id.blackPieces);

        final CapturedPiecesAdapter whitePieces = new CapturedPiecesAdapter(this, R.drawable.white_piece_transparent, R.drawable.white_king_transparent);
        final CapturedPiecesAdapter blackPieces = new CapturedPiecesAdapter(this, R.drawable.black_piece_transparent, R.drawable.black_king_transparent);

        gvWhitePieces.setNumColumns(boardSize);
        gvBlackPieces.setNumColumns(boardSize);

        gvWhitePieces.setAdapter(whitePieces);
        gvBlackPieces.setAdapter(blackPieces);

        final BoardClickHandler cbObserver = new BoardClickHandler(board, gaSquares, tvGameStatus, whitePieces, blackPieces);

        gvChessBoard.setNumColumns(boardSize);
        gvChessBoard.setAdapter(gaSquares);

        gvChessBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cbObserver.click(new Position(position % boardSize, position / boardSize));
            }
        });
    }
}
