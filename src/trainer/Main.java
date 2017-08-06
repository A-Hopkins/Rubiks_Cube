package trainer;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Alex
 */
public class Main extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Group sceneRoot = new Group();

        primaryStage.setTitle("Rubik's Cube Trainer");

        Scene scene = new Scene(sceneRoot, 600, 600, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.BLACK);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
