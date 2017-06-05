package creature;

import creature.body.Body;
import creature.intelligence.brain.Brain;
import creature.intelligence.output.Decisions;
import world.Surface;

import java.awt.*;

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
	 * color of the Creature in the Window
	 */
	private Color color = Color.BLUE;

	/**
	 * Go-to constructor for a Creature.
	 */
	public Creature() {
		this.body = new Body();
		body.setBrain(new Brain((int) (Math.random()*5d+5d),(int) (Math.random()*3d+6d),(int) (Math.random()*4d+4d)));
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

		if (body.getBrain() != null) body.getBrain().addMovement(Math.abs(direction[0])+Math.abs(direction[1]));

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

	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
