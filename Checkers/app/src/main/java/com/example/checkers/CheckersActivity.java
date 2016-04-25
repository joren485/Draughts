package com.example.checkers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

public class CheckersActivity extends AppCompatActivity {
    TileView[][] tiles;
    private static final int BROWN = R.drawable.brown;
    private static final int BEIGE = R.drawable.beige;

    private int columnAmount = 10;

    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_checkers);

        columnAmount = GameSettings.getInstance().getBoardSize();

        initTiles();

        board = new Board(columnAmount);

        GridView gvChessBoard = (GridView) findViewById(R.id.gvChessBoard);
        CheckerBoardAdapter gaSquares = new CheckerBoardAdapter(this, tiles, board);

        gvChessBoard.setNumColumns(columnAmount);
        gvChessBoard.setAdapter(gaSquares);

        gvChessBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gaSquares.click(position / columnAmount, position % columnAmount);
            }
        });
    }



    private void initTiles() {
        tiles = new TileView[columnAmount][columnAmount];
        for (int i = 0; i < columnAmount; i++) {
            for (int j = 0; j < columnAmount; j++) {
                TileView tile = new TileView(this, (i + j) % 2 == 0 ? BEIGE : BROWN);
                tiles[i][j] = tile;
            }
        }
    }
}
