package trainer.model;

import com.javafx.experiments.importers.obj.ObjImporter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads the models for the Rubik's cube
 *
 * @author Alex
 */


public class ModelLoader {

    private static final Logger LOGGER = Logger.getLogger(ModelLoader.class.getName());
    private static final String OBJPATH = "/Cube.obj";

    public ModelLoader() {
        importObj();
    }

    private void importObj() {

		try {

            ObjImporter reader = new ObjImporter(getClass().getResource(OBJPATH).toExternalForm());

            LOGGER.log(Level.INFO, "Loaded file: " + OBJPATH);

        } catch (IOException ex) {

            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
	}
}
