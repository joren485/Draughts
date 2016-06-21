package com.example.draughts.gui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import com.example.draughts.game.*;
import com.example.draughts.R;

/**
 * View that displays a tile in the board.
 */
public class TileView extends ImageView {
    private static final int BLACK_PIECE = R.drawable.black_piece_transparent;
    private static final int WHITE_PIECE = R.drawable.white_piece_transparent;
    private static final int WHITE_KING = R.drawable.white_king_transparent;
    private static final int BLACK_KING = R.drawable.black_king_transparent;

    private Paint centerSelected = new Paint();
    private Paint borderSelected = new Paint();

    private Paint centerCaptured = new Paint();
    private Paint borderCaptured = new Paint();

    private boolean selected;
    private boolean captured;

    private final Position position;
    private final Board board;


    public TileView(Context context, int background, Position position, Board board) {
        super(context);

        setPadding(5, 5, 5, 5);
        selected = false;
        this.setBackgroundResource(background);

        centerSelected.setColor(ContextCompat.getColor(context, R.color.centerSelected));
        borderSelected.setColor(ContextCompat.getColor(context, R.color.borderSelected));

        centerCaptured.setColor(ContextCompat.getColor(context, R.color.centerCaptured));
        borderCaptured.setColor(ContextCompat.getColor(context, R.color.borderCaptured));


        borderSelected.setStrokeWidth(3);

        this.position = position;
        this.board = board;
        this.updatePiece();
    }

    /**
     * Check if the chessboard has changed at this position. Update the
     * image resource accordingly.
     */
    public void updatePiece() {
        if(board.isCapturedPosition(position)) {
            setCaptured(true);
        }
        else {
            setCaptured(false);
        }

        if (board.isEmpty(position)) {
            setImageResource(0);
            return;
        }

        if(board.getColorOf(position) == Piece.Color.White) {
            setImageResource(board.holdsKing(position) ? WHITE_KING : WHITE_PIECE);
        }
        else {
            setImageResource(board.holdsKing(position) ? BLACK_KING : BLACK_PIECE);
        }
    }

    @Override
    public void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    /**
     * Sets the selection state of this tile and forces it to redraw
     * @param b new selection state
     */
    public void select(boolean b) {
        this.selected = b;
        invalidate();
    }

    /**
     * Sets the capture state of this tile and forces it to redraw
     * @param b new capture state
     */
    public void setCaptured(boolean b) {
        this.captured = b;
        invalidate();
    }

    /**
     * Draws a blue square on top of a tile if it is selected, and a red tile if it is captured
     * @param c canvas on which to draw
     */
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        if (selected) {
            c.drawRect(0, 0, getWidth(), getHeight(), centerSelected);
            c.drawLine(1, 1, 1, getHeight() - 1, borderSelected);
            c.drawLine(1, 1, getWidth() - 1, 1, borderSelected);
            c.drawLine(1, getHeight() - 1, getWidth() - 1, getHeight() - 1, borderSelected);
            c.drawLine(getWidth() - 1, 1, getWidth() - 1, getHeight() - 1, borderSelected);
        }

        else if (captured){
            c.drawRect(0, 0, getWidth(), getHeight(), centerCaptured);
            c.drawLine(1, 1, 1, getHeight() - 1, borderCaptured);
            c.drawLine(1, 1, getWidth() - 1, 1, borderCaptured);
            c.drawLine(1, getHeight() - 1, getWidth() - 1, getHeight() - 1, borderCaptured);
            c.drawLine(getWidth() - 1, 1, getWidth() - 1, getHeight() - 1, borderCaptured);
        }
    }
}
