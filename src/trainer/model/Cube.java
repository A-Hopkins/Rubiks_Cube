package trainer.model;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import trainer.math.Rotations;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Cube model and behavior
 *
 * @author Alex
 */
public class Cube {

    private final ModelDisplay content;
    private final Rotations rotate;
    private final ChangeListener<Number> rotMap;
    private final BooleanProperty onRotation = new SimpleBooleanProperty(false);
    private final DoubleProperty rotation = new SimpleDoubleProperty(0d);
    private List<Integer> order;

    private Map<String, MeshView> mapMeshes = new HashMap<>();
    private List<Integer> reorder, layer;
    private Point3D axis = new Point3D(0, 0, 0);


    /**
     * Cube constructor loads the models from {@link ModelLoader} then sets the scene for the cube
     */
    public Cube() {

        ModelLoader model = new ModelLoader();

        mapMeshes = model.getMapMeshes();

        Group cube = new Group();
        cube.getChildren().setAll(mapMeshes.values());

        double dimCube = cube.getBoundsInParent().getWidth();

        content = new ModelDisplay(800, 600, dimCube);
        content.setContent(cube);

        rotate = new Rotations();
        order = rotate.getCube();

        rotMap = (ov, angOld, angNew) -> mapMeshes.forEach((k, v) -> layer.stream().filter(l -> k.contains(l.toString()))
                .findFirst().ifPresent(l -> {
                    Affine a = new Affine(v.getTransforms().get(0));
                    a.prepend(new Rotate(angNew.doubleValue() - angOld.doubleValue(), axis));
                    v.getTransforms().setAll(a);
                    if (k.equals("Block46")) {
                        System.out.println("ang: " + (angNew.doubleValue() - angOld.doubleValue()));
                    }
                }));
    }

    /**
     * Method to rotate the face of the cube
     *
     * @param buttonRotate Name of button pressed
     */
    public void rotateFace(final String buttonRotate) {
        if (onRotation.get()) {
            return;
        }
        onRotation.set(true);

        boolean face = !(buttonRotate.startsWith("X") || buttonRotate.startsWith("Y") || buttonRotate.startsWith("Z"));

        rotate.turn(buttonRotate);

        reorder = rotate.getCube();

        if (!face) {
            layer = new ArrayList<>(reorder);
        } else {
            AtomicInteger index = new AtomicInteger();
            layer = order.stream()
                    .filter(o -> !Objects.equals(o, reorder.get(index.getAndIncrement())))
                    .collect(Collectors.toList());

            layer.add(0, reorder.get(Utilities.getCenter(buttonRotate)));
        }

        axis = Utilities.getAxis(buttonRotate);

        double angEnd = 90d * (buttonRotate.endsWith("i") ? 1d : -1d);

        rotation.set(0d);
        rotation.addListener(rotMap);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(600), e -> {
                    rotation.removeListener(rotMap);
                    onRotation.set(false);
                }, new KeyValue(rotation, angEnd)));
        timeline.playFromStart();

        order = new ArrayList<>(reorder);
    }

    public SubScene getSubScene() {
        return content.getSubScene();
    }

    public BooleanProperty isOnRotation() {
        return onRotation;
    }

}
