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

    Tile[][] tiles;
    private static final int BROWN = R.drawable.brown;
    private static final int BEIGE = R.drawable.beige;

    private final int columnAmount = 10;
    private final int rowsWithPiecesAmount = 3;

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
            }
        });
    }

    private void initTiles() {
        tiles = new Tile[columnAmount][columnAmount];
        for (int i = 0; i < columnAmount; i++) {
            for (int j = 0; j < columnAmount; j++) {
                Tile tile = new Tile(this, (i + j) % 2 == 0 ? BEIGE : BROWN);
                tiles[i][j] = tile;
            }
        }
    }

    private void initPieces() {
        for (int i = 0; i < rowsWithPiecesAmount; i++) {
            for (int j = i % 2; j < columnAmount; j += 2) {
                tiles[i][columnAmount - j - 1].setOccupation(Tile.Occupation.BLACK);
                tiles[columnAmount - i - 1][j].setOccupation(Tile.Occupation.WHITE);
            }
        }
    }
}
