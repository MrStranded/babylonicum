import creature.Creature;
import creature.intelligence.brain.Brain;
import output.visual.Window;
import world.Surface;

/**
 * Created by Michael on 30.05.2017.
 * <p>
 * This class should be used to start the game.
 */
public class Main {

	public static void main(String[] args) {

		Window window = new Window(1200,600);

		Creature creature = new Creature();
		Surface surface = new Surface(32);

		creature.setX((int) (Math.random()*surface.getSize()));
		creature.setY((int) (Math.random()*surface.getSize()));

		new Thread() {
			public void run() {
				while (true) {
					creature.getBody().getBrain().generateRandomInputs();
					creature.getBody().getBrain().process();

					creature.move(surface);

					window.drawSurface(surface,creature,600,0,600,600);
					window.drawBrain(creature.getBody().getBrain(),0,0,600,600);
					window.flip();

					try {
						sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();

	}

}
