package com.example.checkers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

public class Tile extends ImageView {

    private static final int BLACK_TILE = R.drawable.black;
    private static final int WHITE_TILE = R.drawable.white;

    public enum Occupation {
        BLACK, WHITE, EMPTY;
    }

    private Occupation currentOccupation;

    public Tile(Context context, int background) {
        super(context);
        currentOccupation = Occupation.EMPTY;
        setPadding(5, 5, 5, 5);
        setBackgroundResource(background);
    }

    @Override
    public void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public void setOccupation(Occupation occ) {
        this.currentOccupation = occ;
        if (occ == Occupation.BLACK)
            this.setImageResource(BLACK_TILE);
        else if (occ == Occupation.WHITE)
            this.setImageResource(WHITE_TILE);
        else
            this.setImageResource(0);
    }

    public Occupation getOccupation() {
        return currentOccupation;
    }
}
