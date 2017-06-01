package creature.intelligence.input;

import creature.Creature;
import creature.intelligence.brain.Brain;
import world.Surface;

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
			if (brain != null) {
				if ((brain.getNode(0,0) != null) && (surface.getTile(x-1,y) != null)) {
					brain.getNode(0,0).setExcitement(surface.getTile(x-1,y).getHeight());
				}
				if ((brain.getNode(0,1) != null) && (surface.getTile(x,y-1) != null)) {
					brain.getNode(0,1).setExcitement(surface.getTile(x,y-1).getHeight());
				}
				if ((brain.getNode(0,2) != null) && (surface.getTile(x+1,y) != null)) {
					brain.getNode(0,2).setExcitement(surface.getTile(x+1,y).getHeight());
				}
				if ((brain.getNode(0,3) != null) && (surface.getTile(x,y+1) != null)) {
					brain.getNode(0,3).setExcitement(surface.getTile(x,y+1).getHeight());
				}
			}
		}
	}

}
