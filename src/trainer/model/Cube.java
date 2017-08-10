package trainer.model;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.util.Duration;
import trainer.math.Rotations;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private final BooleanProperty onScrambling = new SimpleBooleanProperty(false);

    private List<Integer> order;
    private final Map<String, Transform> mapTransformsScramble = new HashMap<>();
    private final IntegerProperty count = new SimpleIntegerProperty(-1);

    private Map<String, MeshView> mapMeshes = new HashMap<>();
    private List<Integer> reorder, layer;
    private Point3D axis = new Point3D(0, 0, 0);
    private List<Integer> orderScramble;
    private List<String> sequence = new ArrayList<>();
    private List<Move> moves = new ArrayList<>();
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());
    private String last = "V", get = "V";

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

    /**
     * Cube constructor loads the models from {@link ModelLoader} then sets the scene for the cube
     */
    public Cube() {

        ModelLoader model = new ModelLoader();

        mapMeshes = model.getMapMeshes();

        Group cube = new Group();
        cube.getChildren().setAll(mapMeshes.values());

        double dimCube = cube.getBoundsInParent().getWidth();

        content = new ModelDisplay(dimCube);
        content.setContent(cube);

        rotate = new Rotations();
        order = rotate.getCube();

        rotMap = (ov, angOld, angNew) -> mapMeshes.forEach((k, v) -> layer.stream().filter(l -> k.contains(l.toString()))
                .findFirst().ifPresent(l -> {
                    Affine a = new Affine(v.getTransforms().get(0));
                    a.prepend(new Rotate(angNew.doubleValue() - angOld.doubleValue(), axis));
                    v.getTransforms().setAll(a);
                }));
    }

    public void reset() {

    }

    public void scramble() {

        StringBuilder scrambleSequence = new StringBuilder();
        final List<String> movements = Utilities.getMovements();
        IntStream.range(0, 25).boxed().forEach(i -> {
            while (last.substring(0, 1).equals(get.substring(0, 1))) {

                get = movements.get((int) (Math.floor(Math.random() * movements.size())));
            }
            last = get;
            if (get.contains("2")) {
                get = get.substring(0, 1);
                scrambleSequence.append(get).append(" ");
            }
            scrambleSequence.append(get).append(" ");
        });

        System.out.println("Scramble sequence: " + scrambleSequence.toString());
        doSequence(scrambleSequence.toString().trim());
    }

    private void doSequence(String list) {
        onScrambling.set(true);
        sequence = Utilities.unifyNotation(list);

        IntegerProperty index = new SimpleIntegerProperty(1);

        ChangeListener<Boolean> lis = (ov, b, b1) -> {
            if (!b1) {
                if (index.get() < sequence.size()) {
                    rotateFace(sequence.get(index.get()));
                } else {

                    mapMeshes.forEach((k, v) -> mapTransformsScramble.put(k, v.getTransforms().get(0)));
                    orderScramble = new ArrayList<>(reorder);
                }
                index.set(index.get() + 1);
            }
        };
        index.addListener((ov, v, v1) -> {
            if (v1.intValue() == sequence.size() + 1) {
                onScrambling.set(false);
                onRotation.removeListener(lis);
                count.set(-1);
            }
        });
        onRotation.addListener(lis);
        rotateFace(sequence.get(0));
    }

    public int getNumMoves() {
        return moves.size();
    }

    public void addMove(Move m) {
        moves.add(m);
    }

    public List<Move> getMoves() {
        return moves;
    }

    public Move getMove(int index) {
        if (index > -1 && index < moves.size()) {
            return moves.get(index);
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Moves:\n");
        sb.append("Number of moves: ").append(moves.size()).append("\n");
        AtomicInteger ind = new AtomicInteger();
        moves.forEach(m -> sb.append("Move ").append(ind.getAndIncrement()).append(": ")
                .append(m.getFace()).append(" at ")
                .append(LocalTime.ofNanoOfDay(m.getTimestamp()).format(fmt)).append("\n"));
        return sb.toString();
    }
}
