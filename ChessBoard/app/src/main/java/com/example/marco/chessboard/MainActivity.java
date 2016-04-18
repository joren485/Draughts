package com.example.marco.chessboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gvChessBoard = (GridView) findViewById(R.id.gvChessBoard);

        ChessBoardAdapter cbAdapter = new ChessBoardAdapter(this);
        gvChessBoard.setAdapter(cbAdapter);

        gvChessBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (view == null) {
                    return;
                }
                Toast.makeText(MainActivity.this, "(" + (position/8) + "," + (position%8) + ")", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
