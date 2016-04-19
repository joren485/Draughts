package com.example.checkers;

import android.content.Context;
import android.graphics.Paint;
import android.widget.ImageView;

public class SquareView extends ImageView {

    public SquareView(Context context) {
        super(context);
    }

    @Override
    public void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
