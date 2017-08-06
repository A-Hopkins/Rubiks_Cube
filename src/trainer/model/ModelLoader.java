package trainer.model;

import com.javafx.experiments.importers.obj.ObjImporter;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads the models for the Rubik's cube using OpenJFX loads .obj files and associated .mtl files
 *
 * @author Alex
 */


public class ModelLoader {

    private static final Logger LOGGER = Logger.getLogger(ModelLoader.class.getName());
    private static final String OBJPATH = "/Cube.obj";
    private Map<String, MeshView> mapMeshes = new HashMap<>();
    private Set<String> meshes;

    public ModelLoader() {
        importObj();
    }

    private void importObj() {

		try {

            ObjImporter reader = new ObjImporter(getClass().getResource(OBJPATH).toExternalForm());
            meshes = reader.getMeshes();

            for (String s : meshes) {

                MeshView cubiePart = reader.buildMeshView(s);

                PhongMaterial material = (PhongMaterial) cubiePart.getMaterial();


                material.setSpecularPower(1);
                cubiePart.setMaterial(material);

                mapMeshes.put(s, cubiePart);
            }

            LOGGER.log(Level.INFO, "Loaded file: " + OBJPATH);

        } catch (IOException ex) {

            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
	}

    public Map<String, MeshView> getMapMeshes() {
        return mapMeshes;
    }
}
