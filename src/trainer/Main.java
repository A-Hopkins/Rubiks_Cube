package trainer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import trainer.model.Cube;

/**
 * Main file, runs the application
 *
 * @author Alex
 */
public class Main extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        final BorderPane pane = new BorderPane();
        final Pane leftSpacer = new Pane();
        HBox.setHgrow(
                leftSpacer,
                Priority.SOMETIMES
        );
        final Pane rightSpacer = new Pane();
        HBox.setHgrow(
                rightSpacer,
                Priority.SOMETIMES
        );
        // Cube
        Cube cube = new Cube();
        pane.setCenter(cube.getSubScene());

        // UI
        pane.setStyle("-fx-background-color: transparent");

        // TODO: Add a rotation tracker display area

        ToolBar toolBar = new ToolBar(
                new Button("R"),
                new Button("U"),
                new Button("L"),
                new Button("F"),
                new Button("B"),
                new Button("D"),
                leftSpacer,
                new Button("next algorithm"),
                rightSpacer,
                new Button("Solve")
        );

        toolBar.setStyle("-fx-background-color: transparent;");
        pane.setBottom(toolBar);


        // Scene
        final Scene scene = new Scene(pane, 880, 680, true);
        scene.setFill(Color.BLACK);
        primaryStage.setTitle("Rubik's Cube Trainer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
