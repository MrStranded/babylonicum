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
	private int length = 5;
	/**
	 * the width of the creature.intelligence.brain cell array
	 */
	private int width = 8;
	/**
	 * number of maximum Axon connections
	 */
	private int connections = 5;
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
	private Node[][] nodes;
	/**
	 * remembers the movement over the last few iterations
	 */
	private int[] movement = new int[3];
	/**
	 * remembers the angle over the last few iterations
	 */
	private int[] angle = new int[3];

	/**
	 * Creates a standard creature.intelligence.brain.
	 */
	public Brain() {
		createNodes();
	}

	/**
	 * A Constructur where you can defince the dimensions of the brain.
	 */
	public Brain(int length,int width,int connections) {
		this.length = length;
		this.width = width;
		this.connections = connections;

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
	}

	/**
	 * Sometimes creates a random hormone.
	 */
	public void generateHormones() {
		// speed
		if (inBounds(length-1,0) && (nodes[length-1][0] != null)) {
			Node node = nodes[length-1][0];
			double speedFitness = getNormedSpeedFitness();
			Hormone hormone = new Hormone();
			if (speedFitness > 0d) {
				hormone.setGrowthFactor(1d - Math.random()*speedFitness/4d);
				node.getHormones().add(hormone);
			} else {
				hormone.setGrowthFactor(1d - Math.random()*speedFitness/2d);
				node.getHormones().add(hormone);
			}
		}
		// angle
		if (inBounds(length-1,1) && (nodes[length-1][1] != null)) {
			Node node = nodes[length-1][1];
			double angleFitness = getNormedAngleFitness();
			Hormone hormone = new Hormone();
			if (angleFitness > 0d) {
				hormone.setGrowthFactor(1d - Math.random()*angleFitness/10d);
				node.getHormones().add(hormone);
			} else {
				hormone.setGrowthFactor(1d - Math.random()*angleFitness/5d);
				node.getHormones().add(hormone);
			}
		}
	}

	/**
	 * Goes through all the Nodes and modifies it's Axons according to it's Hormones.
	 */
	public void modifyWeights() {
		for (int l = 0; l < length; l++) {
			for (int w = 0; w < width; w++) {
				Node node = nodes[l][w];
				if (node != null) {
					if (node.getHormones() != null && node.getAxons() != null) {
						for (Hormone hormone : node.getHormones()) {
							for (Axon axon : node.getAxons()) {
								if (axon != null && hormone != null) {
									axon.modifyWeight(hormone,node.getExcitement());
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Sends a ripple through the creature.intelligence.brain, originating in the input layer and then affecting all the Nodes sequentially.
	 */
	public void process() {
		for (int l = 0; l < length; l++) {
			for (int w = 0; w < width; w++) {
				if (nodes[l][w] != null) {
					nodes[l][w].propagateExcitement();
				}
			}
		}
		decreaseHormones();
	}

	/**
	 * Damps the Excitement of all Nodes.
	 */
	public void damp() {
		for (int l = 0; l < length; l++) {
			for (int w = 0; w < width; w++) {
				if (nodes[l][w] != null) {
					nodes[l][w].damp();
				}
			}
		}
	}

	/**
	 * Decreases the duration of the Hormones and deletes them when necessary.
	 */
	private void decreaseHormones() {
		for (int x=0; x<length; x++) {
			for (int y=0; y<width; y++) {
				Node node = nodes[x][y];
				if ((node != null) && (node.getHormones() != null)) {
					List<Hormone> hormones = node.getHormones();
					for (ListIterator<Hormone> hormoneListIterator = hormones.listIterator(); hormoneListIterator.hasNext(); ) {
						Hormone hormone = hormoneListIterator.next();
						hormone.decreaseDuration();
						if (hormone.isExpired()) hormoneListIterator.remove();
					}
				}
			}
		}
	}

	/**
	 * Adds the given distance to the movement memory array.
	 */
	public void addMovement(int distance) {
		if (movement.length>1) {
			for (int i=0; i<movement.length-1; i++) {
				movement[i+1] = movement[i];
			}
		}
		movement[0] = distance;
	}

	/**
	 * Returns a double between -1 and 1.
	 * Generally a value below zero means the creature is too lazy, whereas a value above zero means it is hyperactive.
	 */
	private double getNormedSpeedFitness() {
		double goal = 5d;
		double sum = 0d;
		for (int i=0; i<movement.length; i++) { sum+=movement[i]; }

		if (sum>goal) {
			return (sum-goal)/(9-goal);
		} else {
			return (-1)*((goal-sum)/goal);
		}
	}

	/**
	 * Returns a double between -1 and 1.
	 * Generally a value below zero means the creature is walking too straight, whereas a value above zero means it is turning like crazy.
	 */
	private double getNormedAngleFitness() {
		double goal = 30d;
		double sum = 0d;
		for (int i=0; i<angle.length-1; i++) { sum+=Math.abs(angle[i]-angle[i+1]); }
		if (inBounds(length-1,1) && (nodes[length-1][1] != null)) {
			sum+=Math.abs(angle[angle.length-1]-nodes[length-1][1].getExcitement());
		}

		if (sum>goal) {
			return (sum-goal)/(300-goal);
		} else {
			return (-1)*((goal-sum)/goal);
		}
	}

	/**
	 * Sometimes adds, removes or changes an Axon.
	 */
	public void mutate() {
		Node node = getNode((int) (Math.random()*(length-1)),(int) (Math.random()*width));
		//double fitness = getNormedFitness();
		if (node != null) {
			double p = Math.random();
			if (p > 0.7d) {
				Node target = selectTargetNode(node.getX(),node.getY());
				node.addAxon(target);
			} else if (p > 0.4d) {
				//node.removeAxon();
			} else if (p > 0.1d) {
				node.changeAxon();
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
			Node target = selectTargetNode(x,y);
			if ((target != null) && !targetNodes.contains(target)) {
				targetNodes.add(target);
			}
		}
		return targetNodes;
	}

	/**
	 * Selects a random Node as an Axon target for the given Node.
	 */
	private Node selectTargetNode(int x, int y) {
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

			if (inBounds(newX, newY)) return nodes[newX][newY];
		}
		return null;
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
	public boolean inBounds(int x, int y) {
		return (x >= 0) && (y >= 0) && (x < length) && (y < width);
	}

	/**
	 * Returns a specific node. If the position is outside of the array, returns null.
	 */
	public Node getNode(int x,int y) {
		if (inBounds(x,y)) {
			return nodes[x][y];
		} else {
			return null;
		}
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
