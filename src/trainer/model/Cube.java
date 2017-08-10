package trainer.model;

import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.shape.MeshView;

import java.util.HashMap;
import java.util.Map;

/**
 * Cube model and behavior
 *
 * @author Alex
 */
public class Cube {

    private final double dimCube;
    private final ModelDisplay content;
    private Group cube = new Group();

    private Map<String, MeshView> mapMeshes = new HashMap<>();

    /**
     * Cube constructor loads the models from {@link ModelLoader} then sets the scene for the cube
     */
    public Cube() {

        ModelLoader model = new ModelLoader();

        mapMeshes = model.getMapMeshes();

        cube.getChildren().setAll(mapMeshes.values());

        dimCube = cube.getBoundsInParent().getWidth();

        content = new ModelDisplay(800, 600, dimCube);
        content.setContent(cube);

    }

    public SubScene getSubScene() {
        return content.getSubScene();
    }
}
