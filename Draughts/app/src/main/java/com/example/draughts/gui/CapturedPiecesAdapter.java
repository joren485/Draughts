package com.example.draughts.gui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * An adapter that displays the pieces captured by each player.
 */
public class CapturedPiecesAdapter extends BaseAdapter{
    private final Context context;

    private final List<Integer> images;

    private final int normalImage;
    private final int kingImage;

    public CapturedPiecesAdapter(Context c, int normalImage, int kingImage) {
        context = c;
        this.normalImage = normalImage;
        this.kingImage = kingImage;
        images = new ArrayList<>();
    }

    public void addPiece() {
        images.add(normalImage);
    }

    public void addKing() {
        images.add(kingImage);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return null; //images.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        SquareImageView mView;
        if (convertView == null) {
            mView = new SquareImageView(context);

        } else {
            mView = (SquareImageView) convertView;
        }
        mView.setImageResource(images.get(position));
        return mView;
    }

    /**
     * An ImageView that is automatically squared.
     */
    private class SquareImageView extends ImageView {
        public SquareImageView(Context c) {
            super(c);
        }

        public void onMeasure(int widthSpecs, int heightSpecs) {
            super.onMeasure(widthSpecs, widthSpecs);
        }
    }
}
