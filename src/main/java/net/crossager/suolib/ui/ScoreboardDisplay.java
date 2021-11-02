package net.crossager.suolib.ui;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardDisplay {
    private List<Score> scores = new ArrayList<>();
    private List<String> scoresString = new ArrayList<>();
    private Objective obj;
    public ScoreboardDisplay(String id, String name){
        if (Bukkit.getScoreboardManager().getMainScoreboard().getObjective(id) != null) obj = Bukkit.getScoreboardManager().getMainScoreboard().getObjective(id);
        else obj = Bukkit.getScoreboardManager().getMainScoreboard().registerNewObjective(ScoreboardUtil.getNewId(), "dummy", name);

    }
    public ScoreboardDisplay(String id){
        if (Bukkit.getScoreboardManager().getMainScoreboard().getObjective(id) != null) obj = Bukkit.getScoreboardManager().getMainScoreboard().getObjective(id);
        else obj = Bukkit.getScoreboardManager().getMainScoreboard().registerNewObjective(ScoreboardUtil.getNewId(), "dummy", "");
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
    public void setObjective(Objective objective){
        obj = objective;
    }



}
