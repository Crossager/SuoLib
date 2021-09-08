package net.crossager.suolib.server;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerUtility {
    private double tps = 0;
    public ServerUtility(JavaPlugin plugin){

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
        {
            private long mspt = 0;
            private long second = 0;

            @Override
            public void run()
            {
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
    public double getTps(){
        return tps;
    }
}
