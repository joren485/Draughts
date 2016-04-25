package com.example.joren.checkers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView board = (GridView)findViewById(R.id.board);
        board.setAdapter(new BoardAdapter(this));

    }
}
