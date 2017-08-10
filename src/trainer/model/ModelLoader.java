package trainer.model;

import com.javafx.experiments.importers.obj.ObjImporter;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

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


class ModelLoader {

    private static final Logger LOGGER = Logger.getLogger(ModelLoader.class.getName());
    private static final String OBJPATH = "/Cube.obj";
    private final Map<String, MeshView> mapMeshes = new HashMap<>();

    /**
     * Custom constructor calling the importObj method to import the .obj and .mtl files using OpenJFX
     */
    ModelLoader() {
        importObj();
    }

    /**
     * imports the .obj files and .mtl files then creates a hashmap with the mesh values of each cubie
     */
    private void importObj() {

        try {

            ObjImporter reader = new ObjImporter(getClass().getResource(OBJPATH).toExternalForm());
            Set<String> meshes = reader.getMeshes();

            Affine affineIni = new Affine();
            affineIni.prepend(new Rotate(-90, Rotate.X_AXIS));
            affineIni.prepend(new Rotate(90, Rotate.Z_AXIS));

            for (String s : meshes) {

                MeshView cubiePart = reader.buildMeshView(s);

                cubiePart.getTransforms().add(affineIni);

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

    Map<String, MeshView> getMapMeshes() {
        return mapMeshes;
    }
}
