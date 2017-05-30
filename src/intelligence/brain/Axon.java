package intelligence.brain;

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
}
