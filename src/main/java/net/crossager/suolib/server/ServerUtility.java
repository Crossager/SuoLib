package net.crossager.suolib.server;

import net.crossager.suolib.SuoLib;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerUtility {
    public static double getTps(){return tps;}
    private static double tps = 0.0;
    public static void init(){
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SuoLib.getPlugin(), new Runnable()
        {
            private long mspt = 0;
            private long second = 0;

            @Override
            public void run()            {

                mspt = System.currentTimeMillis() - second;
                second = System.currentTimeMillis();
                if (mspt < 50) {
                    tps = 20.0D;
                } else {
                    tps = 1000 / mspt;
                }
            }
        }, 0, 1);
    }
}
