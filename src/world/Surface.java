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
				tiles[x][y].setHeight((int) (Math.sin(x*2d*Math.PI/size)*Math.cos(y*2d*Math.PI/size)*50d+50d));
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
		int[] pos = getPosition(x,y);
		if (tiles[pos[0]][pos[1]] != null) tileList.add(tiles[pos[0]][pos[1]]);
	}

	/**
	 * Caps the given coordinate to fit the bounds of the array.
	 */
	public int[] getPosition(int x, int y) {
		while (x<0) x = x + size;
		while (y<0) y = y + size;
		while (x>=size) x = x - size;
		while (y>=size) y = y - size;

		int[] pos = new int[2];
		pos[0] = x;
		pos[1] = y;
		return pos;
	}

	/**
	 * Returns the tile on the given position.
	 */
	public Tile getTile(int x,int y) {
		if (inBounds(x,y)) {
			return tiles[x][y];
		} else {
			int[] pos = getPosition(x,y);
			return tiles[pos[0]][pos[1]];
		}
	}

	/**
	 * Returns whether a position is inside the bounds of the Tile array.
	 */
	private boolean inBounds(int x,int y) {
		return ((x>=0)&&(y>=0)&&(x<size)&&(y<size));
	}

	//######################################################################################################
	//######################################### Getters & Settesr ##########################################
	//######################################################################################################

	public Tile[][] getTiles() {
		return tiles;
	}
	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
