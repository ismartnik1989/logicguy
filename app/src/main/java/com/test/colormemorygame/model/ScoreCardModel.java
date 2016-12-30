package com.test.colormemorygame.model;

import java.io.Serializable;

/**
 * Created by Nikki on 12/4/2016.
 */

public class ScoreCardModel implements Serializable {
    private String rank;
    private String name;
    private String score;

    public ScoreCardModel(String rank, String name, String score) {
        this.rank = rank;
        this.name = name;
        this.score = score;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
