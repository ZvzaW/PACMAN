import java.io.Serializable;

public class Score implements Serializable {

    private int score;
    private String name;

    public Score(int score, String name) {
        this.score = score;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + ":   " + score + " points";
    }


    public int getScore() {
        return score;
    }
}
