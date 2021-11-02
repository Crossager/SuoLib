package net.crossager.suolib;

import net.crossager.suolib.server.ServerUtility;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

//C:\Users\thor\.m2\repository\net\Crossager\SuoLib\1.0\SuoLib-${project.version}.jar
//D:\Minecraft\Spigot 1.17.1 Server\plugins\SuoLib-${project.version}.jar
public final class SuoLib extends JavaPlugin implements Listener {
    private static SuoLib lib;
    public static SuoLib getPlugin(){return lib;}
    @Override
    public void onEnable() {
        lib = this;
        ServerUtility.init();
        Team team;
        if (Bukkit.getScoreboardManager().getMainScoreboard().getTeam("empty") == null)
            team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("empty");
        else team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("empty");
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        team.addEntry("");
    }



    @Override
    public void onDisable() {

    }
}
