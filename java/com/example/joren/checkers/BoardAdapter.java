package com.example.joren.checkers;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Joren on 18-Apr-16.
 */
public class BoardAdapter extends BaseAdapter {

    private final Context c;

    public BoardAdapter(Context c) {
        this.c = c;
    }

    @Override
    public int getCount(){
        return 100;
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SquareView board_square;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            board_square = new SquareView(this.c);
        } else {
            board_square = (SquareView) convertView;
        }

        if ((position + position/10) % 2 == 0){
            board_square.setBackgroundColor(Color.RED);
        }
        else{
            board_square.setBackgroundColor(Color.BLACK);
        }

        return board_square;
    }

}

