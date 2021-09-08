package net.crossager.suolib.player.custom;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.crossager.suolib.player.PlayerUtility;
import net.crossager.suolib.util.Misc;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.scores.ScoreboardTeam;
import net.minecraft.world.scores.ScoreboardTeamBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.scoreboard.CraftScoreboard;
import org.bukkit.entity.Player;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class NPC {
    private boolean showInTab = true;
    private boolean showNameTag = true;
    private boolean includeMetaData = true;
    private String[] skin;
    private GameProfile gpf;
    private EntityPlayer eplr;
    private Location loc;
    private String name;
    public NPC(String name, Location loc){
        this.name = name;
        gpf = new GameProfile(UUID.randomUUID(), name);
        eplr = new EntityPlayer(((CraftServer) Bukkit.getServer()).getServer(), ((CraftWorld)loc.getWorld()).getHandle(), gpf);
        eplr.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
        this.loc = loc;
    }
    public void setSkin(String[] s){
        skin = s;
        gpf.getProperties().put("textures", new Property("textures", s[0], s[1]));
    }
    public void setSkinByName(String s){
        setSkinById(PlayerUtility.getOfflinePlayerId(s));
    }
    public void setSkinById(UUID s){
        try {
            URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + s.toString() + "?unsigned=false");
            JsonObject property = new JsonParser().parse(new InputStreamReader(url2.openStream()))
                    .getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String value = property.get("value").getAsString();
            String name = property.get("signature").getAsString();
            setSkin(new String[]{value, name});
        } catch (Exception e) {
            System.out.println("Error whilst pulling npc skin, switched to default.");
            setSkin(Misc.toArray("", ""));
        }
    }
    public Player getAsPlayer(){return eplr.getBukkitEntity().getPlayer();}
    public String[] getSkin(){return skin;}
    public void setLocation(Location loc) {
        this.loc = loc;
        eplr.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
    }
    public EntityPlayer getEntity(){
        return eplr;
    }
    public int getId(){return eplr.getId();}
    public boolean getShowInTab() {
        return showInTab;
    }
    public void setShowInTab(boolean showInTab) {
        this.showInTab = showInTab;
    }
    public Location getLocation(){
        return loc;
    }
    public void remove(Player p){
        PlayerConnection con = ((CraftPlayer)p).getHandle().b;
        con.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, eplr));
        con.sendPacket(new PacketPlayOutEntityDestroy(getId()));
    }
    public void send(Player p){
        PlayerConnection con = ((CraftPlayer)p).getHandle().b;
        DataWatcher watcher = eplr.getDataWatcher();
        byte b = 0x01 | 0x02 |0x04 | 0x08 | 0x10 | 0x20 | 0x40;
        watcher.set(DataWatcherRegistry.a.a(17), b);
        con.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, eplr));
        con.sendPacket(new PacketPlayOutNamedEntitySpawn(eplr));
        con.sendPacket(new PacketPlayOutEntityHeadRotation(eplr, (byte) (loc.getYaw() * 256 / 360)));
        if (includeMetaData)
        con.sendPacket(new PacketPlayOutEntityMetadata(getId(), watcher, true));

        if (!showNameTag){
            ScoreboardTeam team = new ScoreboardTeam(
                    ((CraftScoreboard)Bukkit.getScoreboardManager().getMainScoreboard()).getHandle(), name);
            team.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.b);
            PacketPlayOutScoreboardTeam score1 = PacketPlayOutScoreboardTeam.a(team);
            PacketPlayOutScoreboardTeam score2 = PacketPlayOutScoreboardTeam.a(team, true);
            PacketPlayOutScoreboardTeam score3 = PacketPlayOutScoreboardTeam.a(team, name, PacketPlayOutScoreboardTeam.a.a);
            con.sendPacket(score1);
            con.sendPacket(score2);
            con.sendPacket(score3);
        }
        if (!showInTab)
            con.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, eplr));
    }
    public void resendChar(Player p){
        PlayerConnection con = ((CraftPlayer)p).getHandle().b;
        con.sendPacket(new PacketPlayOutNamedEntitySpawn(eplr));
    }

    public boolean isNameTagVisible() {
        return showNameTag;
    }

    public void setNameTagVisible(boolean showNameTag) {
        this.showNameTag = showNameTag;
    }

    public boolean getIncludeMetaData() {
        return includeMetaData;
    }

    public void setIncludeMetaData(boolean includeMetaData) {
        this.includeMetaData = includeMetaData;
    }
}
    

