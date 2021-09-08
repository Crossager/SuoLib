package net.crossager.suolib.util;

import org.bukkit.plugin.java.JavaPlugin;

public class RunnableTimer {
    private int left;
    private int taskId;
    private long wait;
    private long delay;
    private Runnable runnable;
    private JavaPlugin plugin;
    public RunnableTimer(JavaPlugin plugin, long wait, long delay, Runnable runnable, int times){
        this.left = times;
        this.plugin = plugin;
        this.wait = wait;
        this.delay = delay;
        this.runnable = runnable;
    }
    public int getTaskId(){return taskId;}
    public void end(){
        plugin.getServer().getScheduler().cancelTask(taskId);
    }
    private void start(){
        taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, runnable, wait, delay);
    }
    public void setRunnable(Runnable runner) {runnable = runner;}
}
