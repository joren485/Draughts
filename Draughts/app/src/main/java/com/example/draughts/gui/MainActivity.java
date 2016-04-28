package com.example.draughts.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import com.example.draughts.R;

public class MainActivity extends AppCompatActivity {
    Spinner spSelectSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        spSelectSize = (Spinner) findViewById(R.id.spSelectSize);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.board_sizes, R.layout.board_size_text);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Button btStartGame = (Button) findViewById(R.id.btStartGame);

        /**
         * The default board size is 10, which is on the 1st place in the values array.
         */
        spSelectSize.setSelection(1);

        if (spSelectSize != null) {
            spSelectSize.setAdapter(adapter);

            if (btStartGame != null) {
                btStartGame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = spSelectSize.getSelectedItemPosition();
                        if (index != Spinner.INVALID_POSITION) {
                            /**
                             * Determine which board size the user has selected. Save in in GameSettings
                             */
                            int boardSize = getResources().getIntArray(R.array.board_sizes_nrs)[index];
                            GameSettings.getInstance().setBoardSize(boardSize);
                            /**
                             * Start a game of checkers with these settings.
                             */
                            Intent checkersIntent = new Intent(v.getContext(), GameActivity.class);
                            startActivity(checkersIntent);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        // Set the selected size to default.
        spSelectSize.setSelection(1);
    }

}
