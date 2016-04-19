package com.example.checkers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
        return 144;
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
        SquareView mView;
        if (convertView == null) {
            mView = new SquareView(context);

        } else {
            mView = (SquareView) convertView;
        }
        mView.setBackgroundResource((position + position/12) % 2 == 0 ? BEIGE : BROWN);
        mView.setPadding(5, 5, 5, 5);
        if ((position + position/12) % 2 == 0) {
            if (position < 48)
                mView.setImageResource(R.drawable.black);
            else if (position >= 96)
                mView.setImageResource(R.drawable.white);
        }

        return mView;
    }
}
