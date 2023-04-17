package fifa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class App extends Application {

    public static boolean DISPLAYFULLSCREEN = true;

    public static double WIDTH = 1280;
    public static double HEIGHT = 720;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent base = FXMLLoader.load(getClass().getResource("/menu/start.fxml"));

        Scene sc = new Scene(base);
        sc.getStylesheets().add(getClass().getResource("/styling/style.css").toExternalForm());
        prepareGameWindow(stage, sc, "/icon/icon.png");

        stage.setScene(sc);
        stage.show();
    }

    private void prepareGameWindow(Stage stage, Scene scene, String pathToIcon) {
        if (App.DISPLAYFULLSCREEN) {
            App.WIDTH = javafx.stage.Screen.getPrimary().getBounds().getWidth();
            App.HEIGHT = javafx.stage.Screen.getPrimary().getBounds().getHeight();
        }

        Image icon = new Image(getClass().getResource(pathToIcon).toString());

        stage.getIcons().add(icon);
        stage.setTitle("FIFA 2023");

        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);

        stage.setFullScreen(DISPLAYFULLSCREEN);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.setMinWidth(1280);
        stage.setMinHeight(720);

        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));
        stage.setFullScreenExitHint("To exit full screen, press Q.");
    }

}