package net.crossager.suolib.ui;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

public class ScoreboardUtil {
    public static Scoreboard empty(){return Bukkit.getScoreboardManager().getNewScoreboard();}
    public static String getNewId(){
        String newId = UUID.randomUUID().toString().substring(0, 15);
        if (Bukkit.getScoreboardManager().getMainScoreboard().getEntries().contains(newId)) newId = getNewId();
        return newId;
    }
    public static void unregisterEntries(Scoreboard s){
        for (String str : s.getEntries()){
            s.getEntryTeam(str).unregister();
        }
    }
}
