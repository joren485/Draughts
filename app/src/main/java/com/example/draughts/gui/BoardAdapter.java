package com.example.draughts.gui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.draughts.game.*;

import com.example.draughts.R;

/**
 * An adapter that fills a gridview with a checkered beige-brown pattern.
 */
public class BoardAdapter extends BaseAdapter{

    private final int columnAmount;
    private final int numberOfTiles;

    private static final int BLACK_TILE = R.drawable.light_brown;
    private static final int WHITE_TILE = R.drawable.beige;

    private final TileView[][] tiles;

    public BoardAdapter(Context context, Board board) {
        columnAmount = board.getBoardDimensions();
        numberOfTiles = columnAmount * columnAmount;

        tiles = new TileView[columnAmount][columnAmount];
        for (int y = 0; y < columnAmount; y++) {
            for (int x = 0; x < columnAmount; x++) {
                tiles[y][x] = new TileView(context, (y + x) % 2 == 0 ? WHITE_TILE : BLACK_TILE,
                        new Position(x, y), board);
                tiles[y][x].updatePiece();
            }
        }
    }

    /**
     * Updates all tiles
     */
    public void updateAllTiles() {
        for(TileView[] row : tiles) {
            for(TileView tile : row) {
                tile.updatePiece();
            }
        }
    }

    /**
     * Selects a tile
     * @param pos the position of the tile to be selected
     */
    public void selectTile(Position pos) {
        tiles[pos.y][pos.x].select(true);
    }

    /**
     * Deselects all tiles
     */
    public void deselectAll() {
        for (TileView[] tileArray : tiles) {
            for (TileView tile : tileArray) {
                tile.select(false);
            }
        }
    }

    @Override
    public int getCount() {
        return numberOfTiles;
    }

    @Override
    public Object getItem(int i) {
        return tiles[i/columnAmount][i/columnAmount];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        TileView mView;
        if (convertView == null) {
            mView = tiles[position / columnAmount][position % columnAmount];

        } else {
            mView = (TileView) convertView;
        }
        return mView;
    }

}
