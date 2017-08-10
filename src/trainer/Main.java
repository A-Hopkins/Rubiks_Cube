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
        pane.setStyle("-fx-background-color: transparent");

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

        // UI
        // TODO: Add scrambler
        // TODO: Add button pressed tracker
        ToolBar toolBar = new ToolBar(
                new Button("L"),
                new Button("Li"),
                new Button("R"),
                new Button("Ri"),
                new Button("U"),
                new Button("Ui"),
                new Button("D"),
                new Button("Di"),
                new Button("F"),
                new Button("Fi"),
                new Button("B"),
                new Button("Bi"),
                leftSpacer,
                new Button("X"),
                new Button("Xi"),
                new Button("Y"),
                new Button("Yi"),
                new Button("Z"),
                new Button("Zi"),
                rightSpacer,
                new Button("Scramble"),
                new Button("Next Algorithm"),
                new Button("Solve")
        );

        toolBar.setStyle("-fx-background-color: transparent");
        pane.setBottom(toolBar);

        pane.setCenter(cube.getSubScene());

        pane.getChildren().stream()
                .filter(n -> (n instanceof ToolBar))
                .forEach(tb -> ((ToolBar) tb).getItems().stream()
                        .filter(n -> (n instanceof Button))
                        .forEach(n -> ((Button) n).setOnAction(e -> cube.rotateFace(((Button) n).getText()))));
        cube.isOnRotation().addListener((ov, b, b1) -> pane.getChildren().stream()
                .filter(n -> (n instanceof ToolBar))
                .forEach(tb -> tb.setDisable(b1)));

        final Scene scene = new Scene(pane, 880, 680, true);
        scene.setFill(Color.BLACK);
        primaryStage.setTitle("Rubik's Cube Trainer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
