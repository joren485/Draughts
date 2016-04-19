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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        final int columnAmount = 12;

        GridView gvChessBoard = (GridView) findViewById(R.id.gvChessBoard);
        ChessBoardAdapter gaSquares = new ChessBoardAdapter(this,columnAmount);

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
}
