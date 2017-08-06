package trainer.model;

import com.javafx.experiments.importers.obj.ObjImporter;

import java.io.IOException;

/**
 * Loads the models for the Rubik's cube
 *
 * @author Alex
 */


public class ModelLoader {

	public void importObj() {
		try {

			ObjImporter reader = new ObjImporter(getClass().getResource("/Cube.obj").toExternalForm());

		} catch (IOException ex) {
			System.out.println(ex);
		}
	}
}
