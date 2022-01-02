package net.crossager.suolib.gui;

import net.crossager.suolib.SuoLib;
import net.crossager.suolib.gui.events.SignGuiClose;
import net.crossager.suolib.protocol.PacketContainer;
import net.crossager.common.reflection.MethodUtils;
import net.crossager.suolib.util.NMSUtils;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange;
import net.minecraft.network.protocol.game.PacketPlayOutOpenSignEditor;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SignGui implements Listener, Gui {

    private ChatColor color = ChatColor.BLACK;

    private boolean glowing = false;

    private String[] lines = new String[4];

    private SignGuiClose onGuiClose = (p, l) -> {};

    private Map<Player, List<SignGui>> openGuis;

    public SignGui(){
        this(SuoLib.getProvider().getPlugin());
    }

    public SignGui(Plugin plugin){
        openGuis = SuoLib.getData().getOpenSignGuis();
    }

    public SignGui(String... lines){
        this(SuoLib.getProvider().getPlugin(), lines);
    }

    public SignGui(Plugin plugin, String... lines){
        this(plugin);
        this.lines[0] = lines[0];
        this.lines[1] = lines[1];
        this.lines[2] = lines[2];
        this.lines[3] = lines[3];
    }

    public void setLines(String... lines){
        this.lines[0] = lines[0];
        this.lines[1] = lines[1];
        this.lines[2] = lines[2];
        this.lines[3] = lines[3];
    }

    public void setLine(int i, String line){
        lines[i] = line;
    }

    public String[] getLines() {
        return lines;
    }

    public String getLine(int i){
        return lines[i];
    }

    public SignGuiClose getOnClose() {
        return onGuiClose;
    }

    public void setOnGuiClose(SignGuiClose close) {
        this.onGuiClose = close;
    }

    public SignGuiClose getOnGuiClose() {
        return onGuiClose;
    }

    public void openGui(Player p){
        Location loc = p.getLocation().clone();
        loc.setY(255);
        BlockPosition position = new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        IBlockData data = null;
        try {
            data = (IBlockData) MethodUtils.getMethod(Material.OAK_SIGN.createBlockData(), "getState").invoke(loc.getBlock().getType().createBlockData());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        PacketPlayOutOpenSignEditor signPacket = new PacketPlayOutOpenSignEditor(position);
        PacketPlayOutBlockChange blockPacket = new PacketPlayOutBlockChange(position, data);
        NBTTagCompound signNBT = new NBTTagCompound();
        for(int i = 0; i < 4; i++){
            NBTTagString nbt = NBTTagString.a(ComponentSerializer.toString(TextComponent.fromLegacyText(lines[i])));
            signNBT.a("Text" + (i + 1), nbt);
        }
        signNBT.a("x", position.u());
        signNBT.a("y", position.v());
        signNBT.a("z", position.w());
        signNBT.a("id", "minecraft:oak_sign");
        signNBT.a("Color", color.name().toLowerCase());
        signNBT.a("GlowingText", glowing);
        PacketContainer<PacketPlayOutTileEntityData> tilePacket = new PacketContainer<>(null/*SuoLib.getProvider().getPacketManager().PLAY.OUT.TILE_ENTITY_DATA*/);
        tilePacket.write(position);
        tilePacket.write(TileEntityTypes.h);
        tilePacket.write(signNBT);
        NMSUtils.getHandle(p).b.a(blockPacket);
        tilePacket.send(p);
        NMSUtils.getHandle(p).b.a(signPacket);
        IBlockData data2 = null;
        try {
            data2 = (IBlockData) MethodUtils.getMethod(loc.getBlock().getType().createBlockData(), "getState").invoke(loc.getBlock().getType().createBlockData());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        PacketPlayOutBlockChange blockPacket2 = new PacketPlayOutBlockChange(position, data2);
        NMSUtils.getHandle(p).b.a(blockPacket2);
        if (openGuis.get(p) == null) {
            openGuis.put(p, new ArrayList<>());
        }
        openGuis.get(p).add(this);
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public boolean isGlowing() {
        return glowing;
    }

    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
    }
}
