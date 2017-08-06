package trainer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import trainer.model.Cube;

/**
 * Main file, runs the application
 *
 * @author Alex
 */
public class Main extends Application {

    private final BorderPane pane = new BorderPane();

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Cube cube = new Cube();

        final Scene scene = new Scene(pane, 880, 680, true);
        pane.setCenter(cube.getSubScene());

        scene.setFill(Color.BLACK);

        primaryStage.setTitle("Rubik's Cube Trainer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
