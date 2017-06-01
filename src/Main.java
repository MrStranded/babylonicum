import creature.intelligence.brain.Brain;
import output.visual.Window;

/**
 * Created by Michael on 30.05.2017.
 * <p>
 * This class should be used to start the game.
 */
public class Main {

	public static void main(String[] args) {

		Window window = new Window(1200,600);

		Brain brain = new Brain();

		new Thread() {
			public void run() {
				while (true) {
					brain.generateRandomInputs();
					brain.process();

					window.drawBrain(brain,0,0,600,600);

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
