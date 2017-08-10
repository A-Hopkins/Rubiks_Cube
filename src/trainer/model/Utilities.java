package trainer.model;

import javafx.geometry.Point3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Holds the utilities of the Cube class
 *
 * @author alex
 */
class Utilities {

    private static final List<String> movements = Arrays.asList("F", "Fi", "F2", "R", "Ri", "R2",
            "B", "Bi", "B2", "L", "Li", "L2",
            "U", "Ui", "U2", "D", "Di", "D2");

    static List<String> getMovements() {
        return movements;
    }

    static Point3D getAxis(String face) {
        Point3D p = new Point3D(0, 0, 0);
        switch (face.substring(0, 1)) {
            case "L":
            case "M":
                p = new Point3D(-1, 0, 0);
                break;
            case "R":
                p = new Point3D(1, 0, 0);
                break;
            case "U":
                p = new Point3D(0, 1, 0);
                break;
            case "E":
            case "D":
                p = new Point3D(0, -1, 0);
                break;
            case "F":
            case "S":
                p = new Point3D(0, 0, 1);
                break;
            case "B":
                p = new Point3D(0, 0, -1);
                break;
            case "X":
                p = new Point3D(1, 0, 0);
                break;
            case "Y":
                p = new Point3D(0, 1, 0);
                break;
            case "Z":
                p = new Point3D(0, 0, 1);
                break;
        }
        return p;
    }

    static int getCenter(String face) {
        int c = 0;
        switch (face.substring(0, 1)) {
            case "L":
                c = 12;
                break;
            case "M":
                c = 13;
                break;
            case "R":
                c = 14;
                break;
            case "U":
                c = 10;
                break;
            case "E":
                c = 13;
                break;
            case "D":
                c = 16;
                break;
            case "F":
                c = 4;
                break;
            case "S":
                c = 13;
                break;
            case "B":
                c = 22;
                break;
        }
        return c;
    }

    static ArrayList<String> unifyNotation(String list) {
        List<String> asList = Arrays.asList(list.replaceAll("’", "i").replaceAll("'", "i").split(" "));

        ArrayList<String> sequence = new ArrayList<>();
        for (String s : asList) {
            if (s.contains("2")) {
                sequence.add(s.substring(0, 1));
                sequence.add(s.substring(0, 1));
            } else if (s.length() == 1 && s.matches("[a-z]")) {
                sequence.add(s.toUpperCase().concat("i"));
            } else {
                sequence.add(s);
            }
        }
        return sequence;
    }
}
