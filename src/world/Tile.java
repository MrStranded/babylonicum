package world;

/**
 * Created by Michael on 01.06.2017.
 *
 * A tile is a piece of a surface and holds further information.
 */
public class Tile {

	/**
	 * position of the Tile on it's Surface
	 */
	private int x = 0, y = 0;
	/**
	 * the height of the tile
	 */
	private int height = 0;

	/**
	 * Go-to constructor for Tiles.
	 */
	public Tile (int x,int y) {
		this.x = x;
		this.y = y;
	}

	//######################################################################################################
	//######################################### Getters & Settesr ##########################################
	//######################################################################################################

	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
