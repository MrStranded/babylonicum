package creature.body;

import creature.intelligence.brain.Brain;

/**
 * Created by Michael on 01.06.2017.
 *
 * Holds information about the organs of a creatue.
 */
public class Body {

	/**
	 * the brain of the creature
	 */
	private Brain brain;

	/**
	 * Go-to constructor for the body a creature.
	 */
	public Body() {
		this.brain = new Brain();
	}

	//######################################################################################################
	//######################################### Getters & Settesr ##########################################
	//######################################################################################################


	public Brain getBrain() {
		return brain;
	}
	public void setBrain(Brain brain) {
		this.brain = brain;
	}
}
