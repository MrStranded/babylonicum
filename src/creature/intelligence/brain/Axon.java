package creature.intelligence.brain;

/**
 * Created by Michael on 30.05.2017.
 * <p>
 * Axons relay excitement from a Node to a target Node, modifying the excitement on the way depending on it's properties.
 */
public class Axon {

	/**
	 * the weight modifies the incoming excitement
	 */
	private double weight = 1;
	/**
	 * the target Node that the excitement is relayed to
	 */
	private Node targetNode;

	/**
	 * Creates an Axon with the given target Node with randomized weight;
	 */
	public Axon(Node targetNode) {
		this.targetNode = targetNode;
		weight = Math.random()*2d-1d;
	}

	//######################################################################################################
	//######################################### Processing #################################################
	//######################################################################################################

	/**
	 * Modifies the given excitement and transmits it to the recieving Node.
	 */
	public void propagateExcitement(double excitement) {
		if (targetNode != null) {
			excitement *= weight;
			targetNode.addExcitement(excitement);
		}
	}

	/**
	 * Changes the weigth according to the specifications of the given hormone.
	 * The maximal axon |weight| is capped to 3.
	 */
	public void modifyWeight(Hormone hormone) {
		weight *= hormone.getGrowthFactor();
		if (weight > 3d) weight = 3d;
		if (weight > 0d && weight < 0.05d) weight = -0.5d;
		if (weight < -3d) weight = -3d;
		if (weight < 0d && weight > -0.05d) weight = 0.5d;
	}

	//######################################################################################################
	//######################################### Getters & Settesr ##########################################
	//######################################################################################################


	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Node getTargetNode() {
		return targetNode;
	}
	public void setTargetNode(Node targetNode) {
		this.targetNode = targetNode;
	}
}