package net.crossager.suolib.player;

import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class PlayerUtility {
    public static void setPlayerNameTag(Player p, String prefix, String suffix, ChatColor color){
        Team team;
        if (Bukkit.getScoreboardManager().getMainScoreboard().getTeam(p.getName()) == null)
            team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(p.getName());
        else team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(p.getName());
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.setColor(color);
        team.addEntry(getOfflinePlayerName(p.getUniqueId()));
    }
    public static void setPlayerNameTag(String s, String prefix, String suffix, ChatColor color){
        Team team;
        if (Bukkit.getScoreboardManager().getMainScoreboard().getTeam(s) == null)
            team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(s);
        else team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(s);
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.setColor(color);
        team.addEntry(s);
    }
    public static String getOfflinePlayerName(UUID id) {
        try {
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + id.toString());
            return new JsonParser().parse(new InputStreamReader(url.openStream()))
                    .getAsJsonObject().get("name").getAsString();
        } catch (Exception e){
            LogManager.getLogManager().getLogger("CrossagerAPI").log(Level.WARNING, "Couldn't get offline player name with uuid: " + id.toString());
            return "";
        }
    }
    public static UUID getOfflinePlayerId(String name) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            return UUID.fromString(new JsonParser().parse(new InputStreamReader(url.openStream())).getAsJsonObject().get("id").getAsString());
        } catch (Exception e){
            LogManager.getLogManager().getLogger("CrossagerAPI").log(Level.WARNING, "Couldn't get offline player id with name: " + name);
            return UUID.fromString("");
        }
    }
}
