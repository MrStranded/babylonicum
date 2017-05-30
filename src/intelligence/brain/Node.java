package intelligence.brain;

import java.util.List;

/**
 * Created by Michael on 30.05.2017.
 * <p>
 * Nodes are the brain cells in a brain. They know about their excitement and their connections.
 */
public class Node {

	/**
	 * the excitement of this node
	 */
	private double excitement = 0;
	/**
	 * threshold for excitement to trigger Axons
	 */
	private double threshold = 0;
	/**
	 * ongoing Axons
	 */
	private Axon[] axons;

	//######################################################################################################
	//######################################### Processing #################################################
	//######################################################################################################

	/**
	 * If the current excitement exceeds the threshold, sends it through each Axon to the target Nodes.
	 */
	public void propagateExcitement() {

	}

	//######################################################################################################
	//######################################### Construction ###############################################
	//######################################################################################################

	/**
	 * Creates standard axons with given target nodes.
	 *
	 * @param targetNodes A list with the target nodes.
	 */
	public void createAxons(List<Node> targetNodes) {
		if (targetNodes != null) {
			int n = targetNodes.size();
			if (n > 0) {
				axons = new Axon[n];
				int c = 0;
				for (Node node : targetNodes) {
					axons[c++] = new Axon(node);
				}
			}
		}
	}

	//######################################################################################################
	//######################################### Getters & Setters ##########################################
	//######################################################################################################

	public double getExcitement() {
		return excitement;
	}

	public void setExcitement(double excitement) {
		this.excitement = excitement;
	}
}
