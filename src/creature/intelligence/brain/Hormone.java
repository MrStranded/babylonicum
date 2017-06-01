package creature.intelligence.brain;

/**
 * Created by michael1337 on 01/06/17.
 *
 * Hormones steer the development of the brain.
 */
public class Hormone {

	/**
	 * the growth factor describes the change in the growth of a weigth of an axon
	 */
	private double growthFactor = 1d;
	/**
	 * the duration that this hormone has before expiring
	 */
	private int duration = 0;

	/**
	 * Go-to constructor for Hormones.
	 */
	public Hormone() {
		growthFactor = Math.random()*0.1d+0.975d;
		duration = (int) (Math.random()*5d) + 1;
	}

	//######################################################################################################
	//######################################### Processing #################################################
	//######################################################################################################

	/**
	 * This method decreases the hormone's remaining duration.
	 */
	public void decreaseDuration() {
		duration--;
	}

	/**
	 * Returns wheter this hormone has expired.
	 */
	public boolean isExpired() {
		return duration<=0;
	}

	//######################################################################################################
	//######################################### Getters & Settesr ##########################################
	//######################################################################################################

	public double getGrowthFactor() {
		return growthFactor;
	}
	public void setGrowthFactor(double growthFactor) {
		this.growthFactor = growthFactor;
	}

	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
}
