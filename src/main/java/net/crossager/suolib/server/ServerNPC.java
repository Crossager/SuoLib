package net.crossager.suolib.server;

import com.destroystokyo.paper.event.player.PlayerUseUnknownEntityEvent;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.crossager.suolib.SuoLib;
import net.crossager.suolib.player.PlayerUtility;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.scores.ScoreboardTeam;
import net.minecraft.world.scores.ScoreboardTeamBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.scoreboard.CraftScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;

public class ServerNPC implements Listener {

    private Location loc;

    private String name;

    private int id;

    private EntityPlayer entity;

    private EntityArmorStand hologram;

    private String[] skin = {"", ""};

    private GameProfile profile;

    private boolean lookAtPlayer = true;

    private boolean isHologram;

    public ServerNPC(Location loc, String name){
        Bukkit.getServer().getPluginManager().registerEvents(this, SuoLib.getPlugin());
        this.loc = loc;
        this.name = name;
        isHologram = name.length() > 16;
        if (isHologram){
            profile = new GameProfile(UUID.randomUUID(), "");
            hologram = new EntityArmorStand(EntityTypes.c, ((CraftWorld)loc.getWorld()).getHandle());
        } else {
            profile = new GameProfile(UUID.randomUUID(), name);
        }

        entity = new EntityPlayer(((CraftServer) Bukkit.getServer()).getServer(), ((CraftWorld)loc.getWorld()).getHandle(), profile);
        entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
    }

    public void click(ServerNPCClick click){

    }

    public void setSkin(String[] s){
        skin = s;
        profile.getProperties().put("textures", new Property("textures", s[0], s[1]));
    }

    public String[] setSkinByName(String s){
        return setSkinById(PlayerUtility.getOfflinePlayerId(s));
    }

    public String[] setSkinById(UUID s){
        String[] skin = {"", ""};
        try {
            URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + s.toString() + "?unsigned=false");
            JsonObject property = new JsonParser().parse(new InputStreamReader(url2.openStream()))
                    .getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String value = property.get("value").getAsString();
            String name = property.get("signature").getAsString();
            skin = new String[]{value, name};
            setSkin(skin);
        } catch (Exception e) {
            SuoLib.getPlugin().getLogger().log(Level.SEVERE, "Error whilst pulling npc skin, switched to default.");
            setSkin(skin);
        }
        return skin;
    }

    public String[] getSkin() {
        return skin;
    }

    public boolean lookAtPlayer() {
        return lookAtPlayer;
    }

    public void setLookAtPlayer(boolean lookAtPlayer) {
        this.lookAtPlayer = lookAtPlayer;
    }

    // handling

    private Set<Player> registered = new HashSet<>();

    private Set<Player> loaded = new HashSet<>();

    public void spawn(){
        for(Player p : Bukkit.getOnlinePlayers()){
            check(p);
        }
    }

    public void remove(){
        for (Player p : registered){
            removePlayer(p);
        }
        registered.clear();
        loaded.clear();
    }

    private void sendPlayer(Player p){
        PlayerConnection con = ((CraftPlayer)p).getHandle().b;
        DataWatcher watcher = entity.getDataWatcher();
        byte b = 0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40;
        watcher.set(DataWatcherRegistry.a.a(17), b);
        con.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, entity));
        con.sendPacket(new PacketPlayOutNamedEntitySpawn(entity));
        con.sendPacket(new PacketPlayOutEntityHeadRotation(entity, (byte) (loc.getYaw() * 256 / 360)));
        con.sendPacket(new PacketPlayOutEntityMetadata(entity.getId(), watcher, true));
        if (isHologram){
//            ScoreboardTeam team = new ScoreboardTeam(
//                ((CraftScoreboard)Bukkit.getScoreboardManager().getMainScoreboard()).getHandle(), "");
//            team.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.b);
//            PacketPlayOutScoreboardTeam score1 = PacketPlayOutScoreboardTeam.a(team);
//            PacketPlayOutScoreboardTeam score2 = PacketPlayOutScoreboardTeam.a(team, true);
//            PacketPlayOutScoreboardTeam score3 = PacketPlayOutScoreboardTeam.a(team, "", PacketPlayOutScoreboardTeam.a.a);
//            con.sendPacket(score1);
//            con.sendPacket(score2);
//            con.sendPacket(score3);
            hologram.a(loc.getX(), loc.getY() + 0.8, loc.getZ());
            hologram.setCustomNameVisible(true);
            hologram.setCustomName(IChatBaseComponent.a(name));
            hologram.setInvisible(true);
            hologram.setInvulnerable(true);
            hologram.setSmall(true);
            hologram.collidableExemptions.add(p.getUniqueId());
            DataWatcher amw = hologram.getDataWatcher();
            byte a = 0x01 | 0x10 | 0x20;
            watcher.set(DataWatcherRegistry.a.a(17), a);
            con.sendPacket(new PacketPlayOutSpawnEntityLiving(hologram));
            con.sendPacket(new PacketPlayOutEntityMetadata(hologram.getId(), amw, true));

        }
        con.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, entity));
    }

    private void rotate(Player p){
        Location npcloc = loc;
        npcloc = npcloc.setDirection(p.getLocation().subtract(npcloc).toVector());
        PlayerConnection connection = ((CraftPlayer) p).getHandle().b;
        float yaw = npcloc.getYaw();
        float pitch = npcloc.getPitch();
        connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(entity.getId(), (byte) ((yaw%360.)*256/360), (byte) ((pitch%360.)*256/360), false));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(entity, (byte) ((yaw%360.)*256/360)));
    }

    private void destroy(Player p){
        PlayerConnection con = ((CraftPlayer) p).getHandle().b;
        con.sendPacket(new PacketPlayOutEntityDestroy(entity.getId()));
        if (isHologram) con.sendPacket(new PacketPlayOutEntityDestroy(hologram.getId()));
    }

    private void removePlayer(Player p) {
        PlayerConnection con = ((CraftPlayer) p).getHandle().b;
        con.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, entity));
        destroy(p);
    }

    @EventHandler
    public void move(PlayerMoveEvent e){
        check(e.getPlayer());
    }

    @EventHandler
    public void world(PlayerChangedWorldEvent e){
        check(e.getPlayer());
    }

    @EventHandler
    public void join(PlayerJoinEvent e){
        check(e.getPlayer());
    }

    @EventHandler
    public void quit(PlayerQuitEvent e){
        registered.remove(e.getPlayer());
        loaded.remove(e.getPlayer());
    }

    private int clicked = 0;

    @EventHandler
    public void entity(PlayerUseUnknownEntityEvent e){
        if(e.getEntityId() == entity.getId() ||e.getEntityId() == hologram.getId()){
            if (!e.isAttack()){
                clicked++;
                if (clicked == 4){
                    clicked = 0;
                    click(new ServerNPCClick(e.getPlayer(), e.getHand(), false));
                }
            } else
                click(new ServerNPCClick(e.getPlayer(), e.getHand(), true));
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
                sendPlayer(p);
                loaded.add(p);
            }
            if (loaded.contains(p) && p.getLocation().distance(loc) < 64 && lookAtPlayer){
                rotate(p);
            }
        } else
            loaded.remove(p);
    }
}