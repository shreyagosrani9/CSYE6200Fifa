package fifa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class App extends Application {

    public static boolean DISPLAY_FULL_SCREEN = true; 
    // Flag to determine if the window should be displayed in full screen
    public static double WIDTH = 1280; 
    // Default width of the window
    public static double HEIGHT = 720; 
    // Default height of the window

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent base = FXMLLoader.load(getClass().getResource("/menu/start.fxml")); 
        // Load the FXML file for the base UI

        Scene scene = new Scene(base); 
        // Create a new scene with the base UI
        scene.getStylesheets().add(getClass().getResource("/styling/style.css").toExternalForm()); 
        // Load the CSS styles for the scene
        prepareGameWindow(stage, scene, "/icon/icon.png"); 
        // Prepare the game window settings

        stage.setScene(scene); 
        // Set the scene to the stage
        stage.show(); 
        // Show the stage
    }

    private void prepareGameWindow(Stage stage, Scene scene, String pathToIcon) {
        if (DISPLAY_FULL_SCREEN) {
            WIDTH = javafx.stage.Screen.getPrimary().getBounds().getWidth(); 
            // Set window width to the width of the primary screen
            HEIGHT = javafx.stage.Screen.getPrimary().getBounds().getHeight(); 
            // Set window height to the height of the primary screen
        }

        Image icon = new Image(getClass().getResource(pathToIcon).toString()); 
        // Load the icon image

        stage.getIcons().add(icon); 
        // Set the icon for the stage
        stage.setTitle("FIFA 2023"); 
        // Set the title of the stage

        stage.setWidth(WIDTH); 
        // Set the width of the stage
        stage.setHeight(HEIGHT); 
        // Set the height of the stage

        stage.setFullScreen(DISPLAY_FULL_SCREEN); 
        // Set the stage to full screen mode
        stage.setResizable(false); 
        // Disable resizing of the stage
        stage.setAlwaysOnTop(true); 
        // Set the stage to always on top
        stage.setMinWidth(1280); 
        // Set the minimum width of the stage
        stage.setMinHeight(720); 
        // Set the minimum height of the stage

        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));
        // Set the key combination to exit full screen
        stage.setFullScreenExitHint("To exit full screen, press Q."); 
        // Set the hint text for exiting full screen
    }
}
