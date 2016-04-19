package com.example.checkers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author Marco Hernandez
 * An adapter that fills a gridview with a checkered beige-brown pattern.
 */
public class CheckerBoardAdapter extends BaseAdapter{
    private final Context context;
    private final int columnAmount;
    private final Tile[][] tiles;

    public CheckerBoardAdapter(Context c, Tile[][] tiles) {
        context = c;
        this.tiles = tiles;
        this.columnAmount = tiles.length;
    }

    @Override
    public int getCount() {
        return columnAmount * columnAmount;
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
        Tile mView;
        if (convertView == null) {
            mView = tiles[position / columnAmount][position % columnAmount];

        } else {
            mView = (Tile) convertView;
        }

        return mView;
    }
}
