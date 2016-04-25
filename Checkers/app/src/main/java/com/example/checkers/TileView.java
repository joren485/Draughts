package com.example.checkers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

public class TileView extends ImageView {

    private static final int BLACK_TILE = R.drawable.black;
    private static final int WHITE_TILE = R.drawable.white;
    private final Paint center = new Paint();
    private final Paint border = new Paint();
    private boolean selected;

    public enum Occupation {
        BLACK, WHITE, EMPTY;
    }

    private Occupation currentOccupation;

    public TileView(Context context, int background) {
        super(context);
        currentOccupation = Occupation.EMPTY;
        setPadding(5, 5, 5, 5);
        selected = false;
        setBackgroundResource(background);
        center.setColor(getResources().getColor(R.color.centerSelected));
        border.setColor(getResources().getColor(R.color.borderSelected));
        border.setStrokeWidth(3);
        System.out.println(GameSettings.getInstance().getBoardSize());
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

    public void select(boolean b) {
        this.selected = b;
        invalidate();
    }

    public boolean selected() {
        return selected;
    }

    protected void onDraw(Canvas c) {
        super.onDraw(c);
        if (selected) {
            c.drawRect(0, 0, getWidth(), getHeight(), center);
            c.drawLine(1, 1, 1, getHeight() - 1, border);
            c.drawLine(1, 1, getWidth() - 1, 1, border);
            c.drawLine(1, getHeight() - 1, getWidth() - 1, getHeight() - 1, border);
            c.drawLine(getWidth() - 1, 1, getWidth() - 1, getHeight() - 1, border);
        }
    }

    public Occupation getOccupation() {
        return currentOccupation;
    }
}
