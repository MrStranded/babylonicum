package output.visual;

import creature.intelligence.brain.Axon;
import creature.intelligence.brain.Brain;
import creature.intelligence.brain.Hormone;
import creature.intelligence.brain.Node;

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
	public Window() {

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
		setSize(800, 600);

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
	public void drawBrain(Brain brain) {
		Graphics2D g2 = (Graphics2D) getBufferStrategy().getDrawGraphics();

		g2.clearRect(0,0,windowWidth,windowHeigth);

		int dx = windowWidth/(brain.getLength()+2);
		int dy = windowHeigth/(brain.getWidth()+2);
		int sx = dx/2;
		int sy = dy/2;

		if (brain != null) {
			if (brain.getNodes() != null) {
				int width = brain.getWidth();
				int length = brain.getLength();
				Node[][] nodes = brain.getNodes();

				for (int w=0; w<width; w++) {
					for (int l=0; l<length; l++) {
						Node node = nodes[l][w];
						if (node != null) {
							int x = dx*(l+1);
							int y = dy*(w+1);
							g2.setColor(Color.DARK_GRAY);
							g2.fillOval(x, y,sx,sy);
							g2.setColor(Color.BLACK);
							g2.drawString(String.valueOf(node.getExcitement()).substring(0,3),x+sx,y+sy/2);

							if (node.getAxons() != null) {
								for (Axon axon : node.getAxons()) {
									if (axon.getWeight() > 0) {
										g2.setColor(new Color(0,(int) (255d*axon.getWeight()/3d),0));
									} else {
										g2.setColor(new Color((int) (-255d*axon.getWeight()/3d),0,0));
									}
									if (axon.getTargetNode() != null) {
										int tx = (axon.getTargetNode().getX()+1)*dx;
										int ty = (axon.getTargetNode().getY()+1)*dy;

										g2.drawLine(x,y,tx,ty);
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
					g2.drawString("H: "+hormone.getGrowthFactor()+" / "+hormone.getDuration(),30,hy);
					hy += 20;
				}
			}

			getBufferStrategy().show();
		}
	}

}
