package creature;

import creature.body.Body;
import creature.intelligence.output.Decisions;
import world.Surface;

/**
 * Created by Michael on 01.06.2017.
 *
 * The top-most class of a Creature which holds all the sub classes.
 */
public class Creature {

	/**
	 * the body of the creature
	 */
	private Body body;
	/**
	 * position of the creature on the map
	 */
	private int x=0,y=0;

	/**
	 * Go-to constructor for a Creature.
	 */
	public Creature() {
		this.body = new Body();
	}

	//######################################################################################################
	//######################################### Thought Processes ##########################################
	//######################################################################################################

	/**
	 * Decides on a direction and walks.
	 */
	public void move(Surface surface) {
		int[] direction = Decisions.decideWalkDirection(body.getBrain());

		x += direction[0];
		y += direction[1];

		int[] pos = surface.getPosition(x,y);
		x = pos[0];
		y = pos[1];
	}

	//######################################################################################################
	//######################################### Getters & Settesr ##########################################
	//######################################################################################################

	public Body getBody() {
		return body;
	}
	public void setBody(Body body) {
		this.body = body;
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
