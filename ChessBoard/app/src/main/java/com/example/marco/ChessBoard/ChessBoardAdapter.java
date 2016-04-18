package com.example.marco.ChessBoard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * @author Marco Hernandez
 * An adapter that fills a gridview with a checkered beige-brown pattern.
 */
public class ChessBoardAdapter extends BaseAdapter{
    private final Context context;
    private static final int BROWN = R.drawable.brown;
    private static final int BEIGE = R.drawable.beige;

    public ChessBoardAdapter(Context c) {
        context = c;
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ImageView square;

        if (convertView == null) {
            square = new ImageView(context);
            square.setLayoutParams(new GridView.LayoutParams(60, 60));
            square.setScaleType(ImageView.ScaleType.FIT_XY);
            square.setPadding(0,0,0,0);
        }
        else {
            square = (ImageView) convertView;
        }

        square.setImageResource((position + position/10) % 2 == 0 ? BEIGE : BROWN);
        return square;
    }
}
