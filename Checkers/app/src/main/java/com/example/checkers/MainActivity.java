package com.example.checkers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TileView[][] tiles;
    private static final int BROWN = R.drawable.brown;
    private static final int BEIGE = R.drawable.beige;

    private final int columnAmount = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        initTiles();
        initPieces();

        GridView gvChessBoard = (GridView) findViewById(R.id.gvChessBoard);
        CheckerBoardAdapter gaSquares = new CheckerBoardAdapter(this, tiles);

        gvChessBoard.setNumColumns(columnAmount);
        gvChessBoard.setAdapter(gaSquares);
        final TextView tvPosition = (TextView) findViewById(R.id.tvPosition);

        gvChessBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvPosition.setText("Position: (" + (position/columnAmount + 1) + "," + (position%columnAmount + 1) + ")");
                tvPosition.setVisibility(View.VISIBLE);
                select(position / columnAmount, position % columnAmount);
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

    private void initPieces() {
        int rowsWithPiecesAmount = (columnAmount - 2) / 2;
        for (int i = 0; i < rowsWithPiecesAmount; i++) {
            for (int j = i % 2; j < columnAmount; j += 2) {
                tiles[i][columnAmount - j - 1].setOccupation(TileView.Occupation.BLACK);
                tiles[columnAmount - i - 1][j].setOccupation(TileView.Occupation.WHITE);
            }
        }
    }

    private void select(int x, int y) {
        tiles[x][y].select(!tiles[x][y].selected());
    }
}
