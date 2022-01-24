package net.crossager.suolib.client;

import net.crossager.suolib.events.PlayerAttackInvalidEntityEvent;
import net.crossager.suolib.events.PlayerUseInvalidEntityEvent;
import net.crossager.suolib.util.NMSUtils;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EntityLiving;
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
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ClientEntity implements Listener {

    private EntityLiving entity;
    private final Map<Player, EntityLiving> currentEntities = new HashMap<>();
    private final Set<Player> players = new HashSet<>();
    private final Set<Player> loadedPlayers = new HashSet<>();
    private boolean alive = false;
    private Location location = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
    private double renderDistance = 64;

    public ClientEntity(Plugin plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    protected abstract void onInteract(Player player, @NotNull EquipmentSlot hand, boolean b);

    protected abstract void applyData(EntityLiving entity);

    public void spawn(){
        for(Player p : Bukkit.getOnlinePlayers()){
            check(p);
        }
        alive = true;
    }
    public void destroy(){
        loadedPlayers.forEach(this::destroy);
        alive = false;
        loadedPlayers.clear();
    }

    public final void setEntity(EntityLiving entity){
        this.entity = entity;
    }

    public Set<Player> getPlayers(){
        return players;
    }

    @EventHandler
    public void event(PlayerMoveEvent e){
        if(alive)
            check(e.getPlayer());
    }

    @EventHandler
    public void event(PlayerChangedWorldEvent e){
        if(alive)
            check(e.getPlayer());
    }

    @EventHandler
    public void event(PlayerJoinEvent e){
        if(alive)
            check(e.getPlayer());
    }

    @EventHandler
    public void event(PlayerQuitEvent e){
        if(alive) {
            loadedPlayers.remove(e.getPlayer());
        }
    }

    @EventHandler
    public void event(PlayerUseInvalidEntityEvent e){
        if(e.getEntityId() == currentEntities.get(e.getPlayer()).ae()){
            if(EquipmentSlot.HAND.equals(e.getHand())) onInteract(e.getPlayer(), e.getHand(), false);
        }
    }

    @EventHandler
    public void event(PlayerAttackInvalidEntityEvent e){
        if(e.getEntityId() == currentEntities.get(e.getPlayer()).ae()){
            onInteract(e.getPlayer(), EquipmentSlot.HAND, true);
        }
    }

    private void check(Player p){
        if(p.getWorld().equals(location.getWorld())){
            if (p.getLocation().distance(location) > renderDistance && loadedPlayers.contains(p)){
                destroy(p);
                loadedPlayers.remove(p);
            }
            if (p.getLocation().distance(location) < renderDistance && !loadedPlayers.contains(p)){
                send(p);
                loadedPlayers.add(p);
            }
        } else
            loadedPlayers.remove(p);
    }

    private void destroy(Player p){
        PlayerConnection con = NMSUtils.getHandle(p).b;
        con.a(new PacketPlayOutEntityDestroy(currentEntities.get(p).ae()));
    }

    private void send(Player p){
        PlayerConnection con = NMSUtils.getHandle(p).b;
        entity.a(location.getX(), location.getY(), location.getZ());
        applyData(entity);
        DataWatcher amw = entity.ai();
        con.a(new PacketPlayOutSpawnEntityLiving(entity));
        con.a(new PacketPlayOutEntityMetadata(entity.ae(), amw, true));
        currentEntities.put(p, entity);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void update(Player p){
        destroy(p);
        loadedPlayers.remove(p);
        check(p);
    }

    public void setRenderDistance(double renderDistance) {
        this.renderDistance = renderDistance;
    }

    public double getRenderDistance() {
        return renderDistance;
    }
}
