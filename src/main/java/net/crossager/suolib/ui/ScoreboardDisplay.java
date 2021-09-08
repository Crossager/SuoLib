package net.crossager.suolib.ui;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardDisplay {
    private List<Score> scores = new ArrayList<>();
    private List<String> scoresString = new ArrayList<>();
    private Scoreboard scr = ScoreboardUtil.empty();
    private Objective obj;
    public ScoreboardDisplay(String name){
        obj = scr.registerNewObjective(ScoreboardUtil.getNewId(), "dummy", name);
    }
    private void fromStringList(){
        scores.clear();
        for (int i = 0; i < scoresString.size(); i++){
            Score score = obj.getScore(scoresString.get(i));
            score.setScore(i);
            scores.add(score);
        }
    }
    public void setDisplayName(String name){
        obj.setDisplayName(name);
    }
    public String get(int index){
        return scoresString.get(index);
    }
    public void set(int index, String s){
        scoresString.set(index, s);
        this.fromStringList();
    }
    public void add(String s){
        scoresString.add(s);
        this.fromStringList();
    }
    public List<String> getScores(){
        return scoresString;
    }
    public void setScores(List<String> newScores){
        scoresString = newScores;
        this.fromStringList();
    }
    public Scoreboard getScoreboard(){
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        return scr;
    }
    public void setObjective(Objective objective){
        obj = objective;
    }



}
