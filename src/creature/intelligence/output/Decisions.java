package creature.intelligence.output;

import creature.intelligence.brain.Brain;
import creature.intelligence.brain.Node;

/**
 * Created by Michael on 01.06.2017.
 */
public class Decisions {

	/**
	 * A static method to get a walk direction from a Brain.
	 */
	public static int[] decideWalkDirection(Brain brain) {
		int[] direction = new int[2];
		direction[0] = 0;
		direction[1] = 0;

		if (brain != null) {
			int dir = 0;
			int width = brain.getWidth();
			int length = brain.getLength();

			double velocity = 0,angle = 0;

			if (brain.getNode(length-1,0) != null) {
				velocity = brain.getNode(length-1,0).getExcitement();
			}
			if (brain.getNode(length-1,1) != null) {
				angle = brain.getNode(length-1,1).getExcitement();
			}
			angle = angle*2d*Math.PI/100d;
			direction[0] = (int) (Math.cos(angle)*velocity/30d);
			direction[1] = (int) (Math.sin(angle)*velocity/30d);
		}

		return direction;
	}

}
