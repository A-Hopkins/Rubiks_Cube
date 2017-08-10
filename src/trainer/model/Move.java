package trainer.model;

/**
 * Models what a 'move' is for the cube
 *
 * @authour alex
 */
public class Move {

    private String face;
    private long timestamp;

    public Move(String face, long timestamp) {
        this.face = face;
        this.timestamp = timestamp;
    }

    String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Move{" + "face=" + face + ", timestamp=" + timestamp + '}';
    }
}
