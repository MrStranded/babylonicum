package creature.intelligence.brain;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by Michael on 30.05.2017.
 * <p>
 * Nodes are the creature.intelligence.brain cells in a creature.intelligence.brain. They know about their excitement and their connections.
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
	/**
	 * coordinates of the Node. x belongs to length, y to width
	 */
	private int x=0,y=0;

	/**
	 * Necessary Constructor. Do not create Nodes in another way than this.
	 * It is necessary for the coordinates of the node.
	 */
	public Node (int x, int y) {
		this.x = x;
		this.y = y;
	}

	//######################################################################################################
	//######################################### Processing #################################################
	//######################################################################################################

	/**
	 * Damps the excitement of the node.
	 */
	public void damp() {
		if (excitement > 1d) {
			excitement *= 0.5d;
		} else {
			excitement = 0;
		}
		if (axons != null) {
			for (int i=0; i<axons.length; i++) {
				if (axons[i] != null) {
					axons[i].damp();
					if (axons[i].isMarginal()) axons[i] = null;
				}
			}
		}
	}

	/**
	 * If the current excitement exceeds the threshold, sends it through each Axon to the target Nodes.
	 */
	public void propagateExcitement(List<Hormone> hormones) {
		if ((axons != null)&&(axons.length > 0)) {
			if (excitement >= threshold) {
				for (Axon axon : axons) {
					if (axon != null) {
						axon.propagateExcitement(excitement);
						if (hormones != null) {
							for (Hormone hormone : hormones) {
								axon.modifyWeight(hormone);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Adds the given excitement to the current one. Excitement may not be negative and not above 100.
	 * @param excitement that is being added.
	 */
	public void addExcitement(double excitement) {
		this.excitement += excitement;
		if (this.excitement < 0) this.excitement = 0;
		if (this.excitement > 100) this.excitement = 100;
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

	/**
	 * Adds a new Axon targeting the given node if no Axon with that destination already exists.
	 */
	public void addAxon(Node target) {
		if (axons == null) {
			axons = new Axon[1];
			axons[1] = new Axon(target);
		} else {
			boolean exists = false;
			int size = 0;
			for (Axon axon : axons) {
				if (axon != null) {
					if (target == axon.getTargetNode()) {
						exists = true;
						break;
					}
					size++;
				}
			}
			if (!exists) {
				Axon[] newAxons = new Axon[size+1];
				int i=0;
				for (Axon axon : axons) {
					if (axon != null) {
						newAxons[i++] = axon;
					}
				}
				newAxons[i] = new Axon(target);
				axons = newAxons;
			}
		}
	}

	/**
	 * Removes a random Axon.
	 */
	public void removeAxon() {
		if (axons != null) {
			axons[(int) (Math.random()*axons.length)] = null;
		}
	}

	/**
	 * Changes the weight of a random Axon.
	 */
	public void changeAxon() {
		if (axons != null) {
			Axon axon = axons[(int) (Math.random()*axons.length)];
			if (axon != null) axon.setWeight(-axon.getWeight());
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

	public Axon[] getAxons() {
		return axons;
	}
	public void setAxons(Axon[] axons) {
		this.axons = axons;
	}
}
