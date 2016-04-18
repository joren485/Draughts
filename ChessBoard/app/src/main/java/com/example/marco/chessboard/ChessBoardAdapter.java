package com.example.marco.chessboard;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author Marco Hernandez
 * fills a board with black and white clickable squares
 */
public class ChessBoardAdapter extends BaseAdapter {
    Context context;
    private static final int BROWN = R.drawable.brown;
    private static final int BEIGE = R.drawable.beige;
    public ChessBoardAdapter(Context c) {
        context = c;
    }

    @Override
    public int getCount() {
        return 64;
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
        ImageButton ibSquare;

        if(convertView == null) {
            ibSquare = new ImageButton(context);
            ibSquare.setLayoutParams(new GridView.LayoutParams(85, 85));
            ibSquare.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else {
            ibSquare = (ImageButton) convertView;
        }

        ibSquare.setBackgroundResource((position + position/8) % 2 == 0 ? BEIGE : BROWN);
        return ibSquare;
    }
}
