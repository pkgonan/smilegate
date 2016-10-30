package ranking.api.web.domain.model;

public class RankingData {
    String id;
    int score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public RankingData(String id, int score) {
        this.id = id;
        this.score = score;
    }
}
