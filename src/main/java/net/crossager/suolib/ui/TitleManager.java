package net.crossager.suolib.ui;

import net.crossager.suolib.util.RunnableTimer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TitleManager {

    public static void sendActionBar(JavaPlugin plugin, Player p, int ticks, String message){
        RunnableTimer timer = new RunnableTimer(plugin, 0, 1, null, ticks);
        timer.setRunnable(new Runnable() {
            private int currentTicks = 0;

            @Override
            public void run() {

                if (currentTicks > ticks) {
                    timer.end();
                }
                currentTicks++;

                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            }
        });
    }
    public static void sendActionBar(JavaPlugin plugin, Player p, int ticks, BaseComponent message){

        RunnableTimer timer = new RunnableTimer(plugin, 0, 1, null, ticks);
                timer.setRunnable(new Runnable() {
            private int currentTicks = 0;

            @Override
            public void run() {

                if (currentTicks > ticks) {
                    timer.end();
                }
                currentTicks++;
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
            }
        });
    }
    public static void sendActionBar(JavaPlugin plugin, Player p, int ticks, TextComponent message){

        RunnableTimer timer = new RunnableTimer(plugin, 0, 1, null, ticks);
        timer.setRunnable(new Runnable() {
            private int currentTicks = 0;

            @Override
            public void run() {

                if (currentTicks > ticks) {
                    timer.end();
                }
                currentTicks++;
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
            }
        });
    }
}
