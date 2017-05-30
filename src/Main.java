import intelligence.brain.Brain;

/**
 * Created by Michael on 30.05.2017.
 * <p>
 * This class should be used to start the game.
 */
public class Main {

	public static void main(String[] args) {

		Brain brain = new Brain();

		for (int i=0; i<7; i++) {
			brain.generateRandomInputs();
			brain.process();

			brain.print();
		}

	}

}
