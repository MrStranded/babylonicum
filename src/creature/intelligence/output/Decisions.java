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
			for (int w=0; w<width; w++) {
				Node node = brain.getNode(length-1,w);
				if (node!=null) {
					if (node.getExcitement()>50d) {
						switch (dir) {
							case 0:
								direction[0]++;
								break;
							case 1:
								direction[1]++;
								break;
							case 2:
								direction[0]--;
								break;
							case 3:
								direction[1]--;
								break;
						}
					}
					dir = (dir+1)%4;
				}
			}
			if (direction[0] > 1) direction[0] = 1;
			if (direction[0] < -1) direction[0] = -1;
			if (direction[1] > 1) direction[1] = 1;
			if (direction[1] < -1) direction[1] = -1;
		}

		return direction;
	}

}
