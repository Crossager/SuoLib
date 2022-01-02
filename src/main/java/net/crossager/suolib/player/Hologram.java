package net.crossager.suolib.player;

import net.crossager.suolib.SuoLib;
import net.crossager.suolib.events.PlayerAttackInvalidEntityEvent;
import net.crossager.suolib.events.PlayerUseInvalidEntityEvent;
import net.crossager.suolib.util.NMSUtils;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public class Hologram implements Listener {
    private String text;
    private Location loc;
    private Plugin plugin;
    private EntityArmorStand hologram;
    private PlayerEntityInteraction click = (a, b, c) -> {};

    public Hologram(String text, Location location){
        this(text, location, SuoLib.getProvider().getPlugin());
    }

    public Hologram(String text, Location location, Plugin plugin){
        this.text = text;
        this.loc = location;
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        hologram = new EntityArmorStand(EntityTypes.c, NMSUtils.getHandle(loc.getWorld()));
    }

    public void setLocation(Location loc) {
        this.loc = loc;
        for(Player p : Bukkit.getOnlinePlayers()){
            destroy(p);
            loaded.remove(p);
            check(p);
        }
    }

    public void setText(String text) {
        this.text = text;
        for(Player p : loaded){
            sendName(p);
        }
    }

    public Location getLocation() {
        return loc;
    }

    public String getText() {
        return text;
    }

    public void setClickEvent(PlayerEntityInteraction click) {
        this.click = click;
    }



    /**
     * Handling
     */
    private Set<Player> registered = new HashSet<>();

    private Set<Player> loaded = new HashSet<>();

    private boolean spawned = false;

    public void spawn(){
        for(Player p : Bukkit.getOnlinePlayers()){
            check(p);
        }
        spawned = true;
    }

    public void remove(){
        for (Player p : registered){
            destroy(p);
        }
        spawned = false;
        registered.clear();
        loaded.clear();
    }

    private void sendName(Player p){
        PlayerConnection con = NMSUtils.getHandle(p).b;
//        hologram.a(CraftChatMessage.fromStringOrNull(text))
        hologram.a(NMSUtils.toChatBaseComponent(text));
        DataWatcher amw = hologram.ai();
        con.a(new PacketPlayOutEntityMetadata(hologram.ae(), amw, true));
    }

    private void send(Player p){
        PlayerConnection con = NMSUtils.getHandle(p).b;
        hologram.a(loc.getX(), loc.getY() - 1.2, loc.getZ());
        hologram.a(true);
        hologram.a(NMSUtils.toChatBaseComponent(text));
        hologram.n(true);
        hologram.persistentInvisibility = true;
        hologram.b(5, true);
        hologram.m(true);
        hologram.collidableExemptions.add(p.getUniqueId());
        DataWatcher amw = hologram.ai();
        con.a(new PacketPlayOutSpawnEntityLiving(hologram));
        con.a(new PacketPlayOutEntityMetadata(hologram.ae(), amw, true));
    }

    private void destroy(Player p){
        PlayerConnection con = NMSUtils.getHandle(p).b;
        con.a(new PacketPlayOutEntityDestroy(hologram.ae()));
    }

    @EventHandler
    public void move(PlayerMoveEvent e){
        if(spawned)
        check(e.getPlayer());
    }

    @EventHandler
    public void world(PlayerChangedWorldEvent e){
        if(spawned)
            check(e.getPlayer());
    }

    @EventHandler
    public void join(PlayerJoinEvent e){
        if(spawned)
            check(e.getPlayer());
    }

    @EventHandler
    public void quit(PlayerQuitEvent e){
        if(spawned) {
            registered.remove(e.getPlayer());
            loaded.remove(e.getPlayer());
        }
    }
    private int clicked = 0;

    @EventHandler
    public void entity(PlayerUseInvalidEntityEvent e){
        if(e.getEntityId() == hologram.ae()){
            if(EquipmentSlot.HAND.equals(e.getHand())) click.click(e.getPlayer(), e.getHand(), false);
        }
    }
    @EventHandler
    public void entity(PlayerAttackInvalidEntityEvent e){
        if(e.getEntityId() == hologram.ae()){
            click.click(e.getPlayer(), EquipmentSlot.HAND, true);
        }
    }
    private void check(Player p){
        if(p.getWorld().equals(loc.getWorld())){
            if (p.getLocation().distance(loc) > 64 && loaded.contains(p)){
                destroy(p);
                loaded.remove(p);
            }
            if (p.getLocation().distance(loc) < 64 && !loaded.contains(p)){
                if (!registered.contains(p)) registered.add(p);
                send(p);
                loaded.add(p);
            }
        } else
            loaded.remove(p);
    }
}
