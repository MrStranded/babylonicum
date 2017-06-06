import creature.Creature;
import creature.intelligence.brain.Brain;
import creature.intelligence.input.Perception;
import output.visual.Window;
import world.Surface;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 30.05.2017.
 * <p>
 * This class should be used to start the game.
 */
public class Main {

	public static void main(String[] args) {

		int windowWidth = 1800;
		int windowHeight = 900;
		Window window = new Window(windowWidth,windowHeight);

		List<Creature> creatures = new ArrayList<Creature>();

		// Michi
		Creature c1 = new Creature();
		c1.setColor(Color.GREEN);
		creatures.add(c1);

		// Jasmin
		Creature c2 = new Creature();
		c2.setColor(new Color(255,0,255));
		creatures.add(c2);

		Surface surface = new Surface(32);

		for (Creature creature : creatures) {
			creature.setX((int) (Math.random() * surface.getSize()));
			creature.setY((int) (Math.random() * surface.getSize()));
		}

		new Thread() {
			public void run() {
				while (true) {
					for (Creature creature : creatures) {
						creature.getBody().getBrain().damp();
						creature.getBody().getBrain().mutate();
						creature.getBody().getBrain().generateHormones();
						creature.getBody().getBrain().modifyWeights();
						Perception.see(creature, surface);
						creature.getBody().getBrain().process();

						creature.move(surface);
					}

					window.drawSurface(surface,creatures,windowWidth/2,0,windowWidth/2,windowHeight);
					int i=0;
					int cy = windowHeight/creatures.size();
					for (Creature creature : creatures) {
						window.drawBrain(creature, 0, i*cy, windowWidth/2, cy);
						i++;
					}
					window.flip();

					try {
						sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();

	}

}
