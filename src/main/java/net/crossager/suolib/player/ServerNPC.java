package net.crossager.suolib.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.crossager.suolib.SuoLib;
import net.crossager.suolib.events.PlayerAttackInvalidEntityEvent;
import net.crossager.suolib.events.PlayerUseInvalidEntityEvent;
import net.crossager.suolib.util.NMSUtils;
import net.crossager.suolib.util.Utils;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
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

import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;

public class ServerNPC implements Listener {

    private Location loc;

    private final String name;

    private final EntityPlayer entity;

    private EntityArmorStand hologram;

    private String[] skin = {"", ""};

    private final GameProfile profile;

    private PlayerEntityInteraction click = (a, b, c) -> {};

    private boolean lookAtPlayer = true;

    private final boolean isHologram;

    public ServerNPC(String name, Location loc){
        this(name, loc, SuoLib.getProvider().getPlugin());
    }
    public ServerNPC(String name, Location loc, Plugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
        this.loc = loc;
        this.name = name;
        isHologram = name.length() > 16;
        if (isHologram){
            profile = new GameProfile(UUID.randomUUID(), "");
            hologram = new EntityArmorStand(EntityTypes.c, NMSUtils.getHandle(loc.getWorld()));
        } else {
            profile = new GameProfile(UUID.randomUUID(), name);
        }

        entity = new EntityPlayer(NMSUtils.getServer(Bukkit.getServer()), NMSUtils.getHandle(loc.getWorld()), profile);
        entity.b(loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
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
            JsonObject property = JsonParser.parseReader(new InputStreamReader(url2.openStream()))
                    .getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String value = property.get("value").getAsString();
            String name = property.get("signature").getAsString();
            skin = new String[]{value, name};
            setSkin(skin);
        } catch (Exception e) {
            Utils.log(Level.SEVERE, "Error whilst pulling npc skin, switched to default.");
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

    public void setOnClick(PlayerEntityInteraction click) {
        this.click = click;
    }

    public PlayerEntityInteraction getOnClick() {
        return click;
    }

    public void setLocation(Location loc) {
        this.loc = loc;
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

        PlayerConnection con = NMSUtils.getHandle(p).b;
        DataWatcher watcher = entity.ai();
        byte b = 0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40;
        watcher.b(DataWatcherRegistry.a.a(17), b);
        con.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, entity));
        con.a(new PacketPlayOutNamedEntitySpawn(entity));
        con.a(new PacketPlayOutEntityHeadRotation(entity, (byte) (loc.getYaw() * 256 / 360)));
        con.a(new PacketPlayOutEntityMetadata(entity.ae(), watcher, true));
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
            hologram.a(true);
            hologram.a(NMSUtils.toChatBaseComponent(name));
            hologram.n(true);
            hologram.persistentInvisibility = true;
            hologram.b(5, true);
            hologram.m(true);
            hologram.collidableExemptions.add(p.getUniqueId());
            DataWatcher amw = hologram.ai();
            con.a(new PacketPlayOutSpawnEntityLiving(hologram));
            con.a(new PacketPlayOutEntityMetadata(hologram.ae(), amw, true));

        }
        con.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, entity));
    }

    private void rotate(Player p){
        Location npcloc = loc;
        npcloc = npcloc.setDirection(p.getLocation().subtract(npcloc).toVector());
        PlayerConnection connection = NMSUtils.getHandle(p).b;
        float yaw = npcloc.getYaw();
        float pitch = npcloc.getPitch();
        connection.a(new PacketPlayOutEntity.PacketPlayOutEntityLook(entity.ae(), (byte) ((yaw%360.)*256/360), (byte) ((pitch%360.)*256/360), false));
        connection.a(new PacketPlayOutEntityHeadRotation(entity, (byte) ((yaw%360.)*256/360)));
    }

    private void destroy(Player p){
        PlayerConnection con = NMSUtils.getHandle(p).b;
        con.a(new PacketPlayOutEntityDestroy(entity.ae()));
        if (isHologram) con.a(new PacketPlayOutEntityDestroy(hologram.ae()));
    }

    private void removePlayer(Player p) {
        PlayerConnection con = NMSUtils.getHandle(p).b;
        con.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, entity));
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
    public void entity(PlayerUseInvalidEntityEvent e){
        if(e.getEntityId() == entity.ae() || (isHologram ? hologram.ae() == e.getEntityId(): false)) {
            if(EquipmentSlot.HAND.equals(e.getHand())) click.click(e.getPlayer(), e.getHand(), false);
        }
    }
    @EventHandler
    public void entity(PlayerAttackInvalidEntityEvent e){
        if(e.getEntityId() == entity.ae() || (isHologram ? hologram.ae() == e.getEntityId(): false)){
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