package trainer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		StackPane root = new StackPane();

		primaryStage.setTitle("Rubik's Cube Trainer");
		primaryStage.setScene(new Scene(root, 800, 700));
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
