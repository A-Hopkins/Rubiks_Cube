package trainer.model;

import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.shape.MeshView;

import java.util.HashMap;
import java.util.Map;

/**
 * Cube model
 * @author Alex
 */
public class Cube {

    private final double dimCube;
    private final ContentModel content;
    private Group cube = new Group();

    private Map<String, MeshView> mapMeshes = new HashMap<>();

    public Cube() {

        /**
         Import Rubik's Cube model and arrows
         */
        ModelLoader model = new ModelLoader();

        mapMeshes = model.getMapMeshes();

        cube.getChildren().setAll(mapMeshes.values());

        dimCube = cube.getBoundsInParent().getWidth();

        /*
        Create content subscene, add cube, set camera and lights
        */
        content = new ContentModel(800, 600, dimCube);
        content.setContent(cube);

    }

    public SubScene getSubScene() {
        return content.getSubScene();
    }
}
