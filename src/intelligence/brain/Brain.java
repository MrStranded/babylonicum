package intelligence.brain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 30.05.2017.
 * <p>
 * The brain holds the nodes and connections. Takes in input and provides output.
 */
public class Brain {

	/**
	 * the length of the brain cell array
	 */
	private int length = 3;
	/**
	 * the width of the brain cell array
	 */
	private int width = 1;
	/**
	 * number of maximum Axon connections
	 */
	private int connections = 2;
	/**
	 * the maximum distance that a axon may refer backwards
	 */
	private int maxBackwardDistance = 1;
	/**
	 * the maximum distance that a axon may refer forwards
	 */
	private int maxForwardDistance = 2;
	/**
	 * the brain cells
	 */
	Node[][] nodes;

	/**
	 * Creates a standard brain.
	 */
	public Brain() {
		createNodes();
	}

	//######################################################################################################
	//######################################### Processing #################################################
	//######################################################################################################

	/**
	 * Fills randomly valued excitements into the input layer of the brain.
	 */
	public void generateRandomInputs() {
		for (int w = 0; w < width; w++) {
			if (nodes[0][w] != null) {
				nodes[0][w].setExcitement(Math.random());
			}
		}
	}

	/**
	 * Sends a ripple through the brain, originating in the input layer and then affecting all the Nodes sequentially.
	 */
	public void process() {
		for (int l = 0; l < length; l++) {
			for (int w = 0; w < width; w++) {
				if (nodes[l][w] != null) {
					nodes[l][w].propagateExcitement();
				}
			}
		}
	}

	//######################################################################################################
	//######################################### Construction ###############################################
	//######################################################################################################

	/**
	 * The go-to method to create a new net of nodes and axons.
	 */
	private void createNodes() {
		ensureMinimalSize();
		nodes = new Node[length][width];

		// create nodes
		for (int l = 0; l < length; l++) {
			for (int w = 0; w < width; w++) {
				nodes[l][w] = new Node();
			}
		}

		// connect nodes
		for (int l = 0; l < length; l++) {
			for (int w = 0; w < width; w++) {
				List<Node> targetNodes = selectTargetNodes(l, w);
				nodes[l][w].createAxons(targetNodes);
			}
		}
	}

	/**
	 * This method selects a List of nodes as target Nodes based on the predefined parameters.
	 *
	 * @param x xPos of the Node in the Brain.
	 * @param y yPos of the Node in the Brain.
	 */
	private List<Node> selectTargetNodes(int x, int y) {
		List<Node> targetNodes = new ArrayList<Node>();
		for (int c = 0; c < connections; c++) {
			// This is the case when the probability falls into the forward range
			int newX = x, newY = y;
			if (Math.random() * (maxBackwardDistance + maxForwardDistance) >= maxBackwardDistance) {
				newX = x + (int) (Math.random() * maxForwardDistance) + 1;
				if (newX >= length) newX = length;

				// This is the case when the probability falls into the backward range
			} else {
				newX = x - (int) (Math.random() * maxBackwardDistance) - 1;
				if (newX < 1) newX = 1; // input layer is forbidden

			}
			// Add the target Node when it is inside the brain and not in the same layer as the origin Node.
			if (newX != x) {
				newY = (int) (Math.random() * width);

				if (inBounds(newX, newY)) {
					targetNodes.add(nodes[newX][newY]);
				}
			}
		}
		return targetNodes;
	}

	//######################################################################################################
	//######################################### Technicalities #############################################
	//######################################################################################################

	/**
	 * This method ensures, that length is at least 1 and width is at least 1.
	 */
	private void ensureMinimalSize() {
		if (length < 1) length = 1;
		if (width < 1) width = 1;
	}

	/**
	 * This method checks whether a coordinate is inside the brain's scope.
	 */
	private boolean inBounds(int x, int y) {
		return (x >= 0) && (y >= 0) && (x < length) && (y < width);
	}

}
