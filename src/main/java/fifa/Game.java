package fifa;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game {

	// Boolean flag to toggle hitbox visibility
	boolean SHOW_HITBOX = false;
	
	private Scene scene;
	private ArrayList<Player> players = new ArrayList<>(); // List to store players
	private Vector[] startingPositions = new Vector[3]; // Array to store starting positions of players
	private CollisionDetection CDsystem; // Collision detection system
	private Ball ball; // Ball object
	private KeyboardInput kbInput; // Keyboard input handler
	private Logic gameLogic; // Game logic handler

	public Game(Stage stage, String[] names, Color[] colors) {

		Group root = new Group();

		Elements elm = new Elements();
		PlayField field = new PlayField(elm, colors);

		final double PLAYER_DISTANCE_FROM_CENTER = App.HEIGHT / 3;

		Vector posDefault = new Vector(field.ground.getCenterX(), field.ground.getCenterY());

		startingPositions[0] = new Vector(0, PLAYER_DISTANCE_FROM_CENTER);
		startingPositions[1] = Utils.rotate(startingPositions[0], 2 * Math.PI / 3);
		startingPositions[2] = Utils.rotate(startingPositions[0], -2 * Math.PI / 3);

		// Create player objects and add them to players list
		for (int i = 0; i < 3; i++)
			players.add(new Player(elm, startingPositions[i], colors[i], field.ground, names[i]));

		ball = new Ball(elm, posDefault, field.ground);

		Hitboxes hitboxes = new Hitboxes(field);

		if (SHOW_HITBOX)
			hitboxes.showHitboxes(elm.getElements());

		gameLogic = new Logic(10, elm, players.get(0), players.get(1), players.get(2), ball);
		kbInput = new KeyboardInput(gameLogic, players.toArray(new Player[0]));

		CDsystem = new CollisionDetection(hitboxes.border, gameLogic, hitboxes);

		CDsystem.addStatic(hitboxes.getElementsCollection());

		CDsystem.addDynamic(ball);
		CDsystem.addDynamic(players.get(0));
		CDsystem.addDynamic(players.get(1));
		CDsystem.addDynamic(players.get(2));

		root.getChildren().addAll(elm.getElements());

		this.scene = stage.getScene();
		stage.getScene().setRoot(root);
		stage.show();

		setGameLoop();
	}

	// Method to set up the game loop
	private void setGameLoop() {
		scene.setOnKeyPressed(event -> kbInput.setInputOnKeyPressed(event.getCode()));

		scene.setOnKeyReleased(event -> kbInput.setInputOnKeyReleased(event.getCode()));

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				// Update players, collision detection, game logic, and ball
				for (Player p : players) {
					p.update();
				}

				CDsystem.collisionChecks();
				gameLogic.resolveLogic();
				ball.update();
			}
		};

		timer.start();
	}

	// Method to pause/reset the game
	public void pauseResetGame() {
		// Implementation for pausing/resetting the game
	}

	// Method to resume the game
	public void resumeGame() {
		// Implementation for resuming the game
	}
}
