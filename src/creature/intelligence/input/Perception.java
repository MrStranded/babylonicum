package creature.intelligence.input;

import creature.Creature;
import creature.intelligence.brain.Brain;
import world.Surface;
import world.Tile;

import java.util.List;

/**
 * Created by Michael on 01.06.2017.
 *
 * The perception class puts world data into the brain of a Creature.
 */
public class Perception {

	/**
	 * The given creature percieves information about the world visually.
	 */
	public static void see (Creature creature, Surface surface) {
		if ((creature != null) && (creature.getBody() != null)) {
			Brain brain = creature.getBody().getBrain();
			int x = creature.getX();
			int y = creature.getY();
			List<Tile> neighbours = surface.getNeighbours(surface.getTile(x,y));
			if (brain != null) {
				int i=0;
				// normal
				for (Tile tile : neighbours) {
					if ((brain.getNode(0,i) != null) && (tile != null)) {
						brain.getNode(0,i).setExcitement(tile.getHeight());
					}
					i++;
				}
				// inverted
				for (Tile tile : neighbours) {
					if ((brain.getNode(0,i) != null) && (tile != null)) {
						brain.getNode(0,i).setExcitement(100d-tile.getHeight());
					}
					i++;
				}
			}
		}
	}

}
