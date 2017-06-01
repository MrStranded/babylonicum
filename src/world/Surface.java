package world;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 01.06.2017.
 *
 * A surface is a more or less 2 dimensional map with height information and more.
 */
public class Surface {

	/**
	 * tiles of the Surface
	 */
	private Tile[][] tiles;
	/**
	 * length of an edge of the Surface
	 */
	private int size = 12;

	/**
	 * Go-to constructor for a new Surface.
	 */
	public Surface (int size) {
		this.size = size;

		createTiles();
	}

	//######################################################################################################
	//######################################### Construction ###############################################
	//######################################################################################################

	/**
	 * Creates the (possibly) inter-connected array of tiles.
	 */
	private void createTiles() {
		tiles = new Tile[size][size];

		for (int x=0; x<size; x++) {
			for (int y=0; y<size; y++) {
				tiles[x][y] = new Tile(x,y);
			}
		}
	}

	//######################################################################################################
	//######################################### Information Recieval #######################################
	//######################################################################################################

	/**
	 * Returns a List of all the neighbour Tiles of the given Tile.
	 */
	public List<Tile> getNeighbours(Tile tile) {
		List<Tile> neighbours = new ArrayList<Tile>();
		if (tile != null) {
			int x = tile.getX();
			int y = tile.getY();

			tryToAdd(neighbours,x+1,y);
			tryToAdd(neighbours,x-1,y);
			tryToAdd(neighbours,x,y+1);
			tryToAdd(neighbours,x,y-1);
		}
		return neighbours;
	}

	/**
	 * Adds the tile of the given position to the List.
	 */
	private void tryToAdd(List<Tile> tileList, int x, int y) {
		if (x<0) x = size-1;
		if (y<0) y = size-1;
		if (x>=size) x = 0;
		if (y>=size) y = 0;
		if (tiles[x][y] != null) tileList.add(tiles[x][y]);
	}
}
