package com.example.checkers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gvChessBoard = (GridView) findViewById(R.id.gvChessBoard);
        ChessBoardAdapter gaSquares = new ChessBoardAdapter(this);

        gvChessBoard.setAdapter(gaSquares);

        final TextView tvPosition = (TextView) findViewById(R.id.tvPosition);

        gvChessBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvPosition.setText("Position: (" + (position/10 + 1) + "," + (position%10 + 1) + ")");
                tvPosition.setVisibility(View.VISIBLE);
            }
        });
    }
}
