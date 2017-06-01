package output.visual;

import creature.Creature;
import creature.intelligence.brain.Axon;
import creature.intelligence.brain.Brain;
import creature.intelligence.brain.Hormone;
import creature.intelligence.brain.Node;
import world.Surface;
import world.Tile;

import javax.accessibility.Accessible;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by michael1337 on 01/06/17.
 *
 * Window will bundle the access methods for building up a graphical representation of the game.
 */
public class Window extends Canvas implements Accessible{

	private Frame mainFrame;
	private Label headerLabel;
	private Label statusLabel;
	private Panel controlPanel;

	/**
	 * dimensions of window
	 */
	private int windowWidth=800,windowHeigth=600;

	/**
	 * Go-to constructor for a window. Initializes with default resolution.
	 */
	public Window(int windowWidth, int windowHeigth) {
		this.windowWidth = windowWidth;
		this.windowHeigth = windowHeigth;

		mainFrame = new Frame("Babylonicum");
		mainFrame.setSize(windowWidth,windowHeigth);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});

		controlPanel = new Panel();
		//controlPanel.setLayout(new FlowLayout());

		controlPanel.add(this);

		setBackground (Color.WHITE);
		setSize(windowWidth, windowHeigth);

		mainFrame.add(controlPanel);
		mainFrame.setVisible(true);

		createBufferStrategy(2);
	}

	public void paint (Graphics g) {
		//Graphics2D g2 = (Graphics2D) g;

		//g2.drawString ("It is a custom canvas area", 70, 70);
	}

	/**
	 * A small method to draw the connections of a brain into the window.
	 * @param brain that should be displayed.
	 */
	public void drawBrain(Brain brain, int xPos, int yPos, int width, int height) {
		Graphics2D g2 = (Graphics2D) getBufferStrategy().getDrawGraphics();

		g2.clearRect(xPos,yPos,width,height);

		int dx = width/(brain.getLength()+2);
		int dy = height/(brain.getWidth()+2);
		int sx = dx/2;
		int sy = dy/2;

		if (brain != null) {
			if (brain.getNodes() != null) {
				int bw = brain.getWidth();
				int bl = brain.getLength();
				Node[][] nodes = brain.getNodes();

				for (int w=0; w<bw; w++) {
					for (int l=0; l<bl; l++) {
						Node node = nodes[l][w];
						if (node != null) {
							int x = xPos + dx*(l+1);
							int y = yPos + dy*(w+1);
							g2.setColor(new Color((int) (2*node.getExcitement()),(int) (2*node.getExcitement()),(int) (2*node.getExcitement())));
							g2.fillOval(x, y,sx,sy);
							g2.setColor(Color.BLACK);
							g2.drawString(String.valueOf(node.getExcitement()).substring(0,3),x+sx,y+sy/2);

							if (node.getAxons() != null) {
								for (Axon axon : node.getAxons()) {
									if (axon != null) {
										if (axon.getWeight() > 0) {
											g2.setColor(new Color(0, (int) (255d * axon.getWeight() / 2d), 0));
										} else {
											g2.setColor(new Color((int) (-255d * axon.getWeight() / 2d), 0, 0));
										}
										if (axon.getTargetNode() != null) {
											int tx = xPos + (axon.getTargetNode().getX() + 1) * dx;
											int ty = yPos + (axon.getTargetNode().getY() + 1) * dy;

											g2.drawLine(x+dx/4, y+dy/4, tx+dx/4, ty+dy/4);
										}
									}
								}
							}
						}
					}
				}
			}

			if (brain.getHormones() != null) {
				int hy = 20;
				for (Hormone hormone : brain.getHormones()) {
					if (hormone.getGrowthFactor() > 1d) {
						g2.setColor(new Color(0,150,0));
					} else {
						g2.setColor(new Color(150,0,0));
					}
					g2.drawString("H: "+hormone.getGrowthFactor()+" / "+hormone.getDuration(),xPos + 30,yPos + hy);
					hy += 20;
				}
			}
		}
	}

	/**
	 * Flips the buffers.
	 */
	public void flip() {
		getBufferStrategy().show();
	}

	/**
	 * A small method to draw a given Surface into the specified area.
	 */
	public void drawSurface(Surface surface, Creature creature, int xPos, int yPos, int width, int height) {
		Graphics2D g2 = (Graphics2D) getBufferStrategy().getDrawGraphics();

		g2.clearRect(xPos,yPos,width,height);

		if (surface != null) {
			int s = surface.getSize();

			int dx = width/(s+2);
			int dy = height/(s+2);

			for (int x=0; x<s; x++) {
				for (int y=0; y<s; y++) {
					Tile tile = surface.getTile(x,y);
					if (tile != null) {
						int tx = xPos + dx*(x+1);
						int ty = yPos + dy*(y+1);
						int c = tile.getHeight()*255/100;
						g2.setColor(new Color(c,c,c));
						g2.fillRect(tx,ty,dx,dy);
						if ((creature != null) && (creature.getX() == x) && (creature.getY() == y)) {
							g2.setColor(Color.BLUE);
							g2.fillOval(tx,ty,dx,dy);
						}
					}
				}
			}
		}
	}

	//######################################################################################################
	//######################################### Getters & Settesr ##########################################
	//######################################################################################################


	public int getWindowWidth() {
		return windowWidth;
	}
	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

	public int getWindowHeigth() {
		return windowHeigth;
	}
	public void setWindowHeigth(int windowHeigth) {
		this.windowHeigth = windowHeigth;
	}
}
