package creature.intelligence.brain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Michael on 30.05.2017.
 * <p>
 * The creature.intelligence.brain holds the nodes and connections. Takes in input and provides output.
 */
public class Brain {

	/**
	 * the length of the creature.intelligence.brain cell array
	 */
	private int length = 10;
	/**
	 * the width of the creature.intelligence.brain cell array
	 */
	private int width = 10;
	/**
	 * number of maximum Axon connections
	 */
	private int connections = 7;
	/**
	 * the maximum distance that a axon may refer backwards
	 */
	private int maxBackwardDistance = 1;
	/**
	 * the maximum distance that a axon may refer forwards
	 */
	private int maxForwardDistance = 2;
	/**
	 * the creature.intelligence.brain cells
	 */
	Node[][] nodes;
	/**
	 * the hormones that are active at the time
	 */
	List<Hormone> hormones = new ArrayList<Hormone>();

	/**
	 * Creates a standard creature.intelligence.brain.
	 */
	public Brain() {
		createNodes();
	}

	//######################################################################################################
	//######################################### Processing #################################################
	//######################################################################################################

	int exPos = 0;
	/**
	 * Fills randomly valued excitements into the input layer of the creature.intelligence.brain.
	 */
	public void generateRandomInputs() {
//		for (int w = 0; w < width; w++) {
//			if (nodes[0][w] != null) {
//				nodes[0][w].setExcitement(Math.random()*100);
//			}
//		}
		if (inBounds(0,exPos)) {
			nodes[0][exPos].setExcitement(100d);
		}
		exPos = (exPos+1)%width;
		// temporary!
		if (Math.random()>0.7d) {
			hormones.add(new Hormone());
		}
	}

	/**
	 * Sends a ripple through the creature.intelligence.brain, originating in the input layer and then affecting all the Nodes sequentially.
	 */
	public void process() {
		for (int l = 0; l < length; l++) {
			for (int w = 0; w < width; w++) {
				if (nodes[l][w] != null) {
					nodes[l][w].damp();
				}
			}
		}
		for (int l = 0; l < length; l++) {
			for (int w = 0; w < width; w++) {
				if (nodes[l][w] != null) {
					nodes[l][w].propagateExcitement(hormones);
					//modifyWeights(nodes[l][w]);
				}
			}
		}
		decreaseHormones();
	}

	/**
	 * Modifies the weigths of the axons going out of this node according to the existing hormones.
	 */
	private void modifyWeights(Node node) {
		if ((hormones != null) && (node.getAxons() != null)) {
			for (Axon axon : node.getAxons()) {
				for (Hormone hormone : hormones) {
					axon.modifyWeight(hormone);
				}
			}
		}
	}

	/**
	 * Decreases the duration of the Hormones and deletes them when necessary.
	 */
	private void decreaseHormones() {
		for (ListIterator<Hormone> hormoneListIterator = hormones.listIterator(); hormoneListIterator.hasNext();) {
			Hormone hormone = hormoneListIterator.next();
			hormone.decreaseDuration();
			if (hormone.isExpired()) hormoneListIterator.remove();
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
				nodes[l][w] = new Node(l,w);
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
			// Add the target Node when it is inside the creature.intelligence.brain and not in the same layer as the origin Node.
			if (newX != x) {
				newY = (int) (Math.random() * width);

				if (inBounds(newX, newY) && !targetNodes.contains(nodes[newX][newY])) {
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
	 * This method checks whether a coordinate is inside the creature.intelligence.brain's scope.
	 */
	private boolean inBounds(int x, int y) {
		return (x >= 0) && (y >= 0) && (x < length) && (y < width);
	}

	//######################################################################################################
	//######################################### Getters & Setters ##########################################
	//######################################################################################################

	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

	public Node[][] getNodes() {
		return nodes;
	}
	public void setNodes(Node[][] nodes) {
		this.nodes = nodes;
	}

	public List<Hormone> getHormones() {
		return hormones;
	}
	public void setHormones(List<Hormone> hormones) {
		this.hormones = hormones;
	}

	//######################################################################################################
	//######################################### Debugging ##################################################
	//######################################################################################################

	/**
	 * Gives a simple representation of the nodes at the current moment.
	 */
	public void print() {
		System.out.println(System.currentTimeMillis());
		for (int w=0; w<width; w++) {
			for (int l=0; l<length; l++) {
				System.out.print("N("+String.valueOf(nodes[l][w].getExcitement()).substring(0,3)+") ");
			}
			System.out.println("<");
		}
	}

}
