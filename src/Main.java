import creature.intelligence.brain.Brain;
import output.visual.Window;

/**
 * Created by Michael on 30.05.2017.
 * <p>
 * This class should be used to start the game.
 */
public class Main {

	public static void main(String[] args) {

		Window window = new Window();

		Brain brain = new Brain();

		//for (int i=0; i<100; i++) {
		new Thread() {
			public void run() {
				while (true) {
					brain.generateRandomInputs();
					brain.process();

					//brain.print();
					window.drawBrain(brain);

					try {
						sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		//}

	}

}
